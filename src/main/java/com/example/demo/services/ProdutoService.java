package com.example.demo.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.entities.CategoriaEntity;
import com.example.demo.entities.ProdutoEntity;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.mapper.ProdutoMapper;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository repo;
	
	@Autowired
	CategoriaRepository repoCategoria;
	
	@Autowired
	ProdutoMapper mapper;
	
	@Autowired ImageService imageService;
	
	
	public List<ProdutoDTO> findAll(){
		List<ProdutoEntity> list = new ArrayList<>();
		list.addAll(repo.findAll());
		List<ProdutoDTO> listDTO = new ArrayList<>();
		for (ProdutoEntity produtoEntity : list) {
			listDTO.add(mapper.toDTO(produtoEntity));
		}
		return listDTO;
	}
	
	public ProdutoDTO create(ProdutoDTO prodDTO) {
		prodDTO.setCategoria(repoCategoria.getById(prodDTO.getCodigoCategoria()));
		repo.save(mapper.toEntity(prodDTO));
		return prodDTO;
	}

	public ProdutoDTO getById(Integer id) throws IdNotFoundException {
		System.out.println(id);
		if(repo.findById(id).isEmpty()) {
			throw new IdNotFoundException("Id não encontrado!");
		}
		ProdutoDTO prodReturn = mapper.toDTO(repo.findById(id).get());
		
		return prodReturn;
	}

	public ProdutoDTO update(Integer id, ProdutoDTO prodEnt) throws IdNotFoundException {
		getById(id);
		ProdutoDTO  prodNew = prodEnt;
		
		ProdutoEntity prod = repo.findById(id).get();
		
		if(prodNew.getNome() != null) {
			prod.setNome(prodNew.getNome());
		}
		
		if(prodNew.getDescricao()!= null) {
			prod.setDescricao(prodNew.getDescricao());
		}
		return mapper.toDTO(repo.save(prod));
	}

	public void delete(Integer id) throws IdNotFoundException {
		getById(id);
		repo.deleteById(id);
	}

	public Page<ProdutoEntity> search(String nome, Integer page, Integer linhasPorPagina,
			String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linhasPorPagina,Direction.valueOf(direction), orderBy);
//		List<CategoriaEntity> categorias = repoCategoria.findAllById(ids);
		return repo.search(nome,pageRequest);
	}
	
	public ProdutoEntity getByIdInterno(Integer id) throws IdNotFoundException {
		
		if(repo.findById(id).isEmpty()) {
			throw new IdNotFoundException("Id não encontrado!");
		}
		ProdutoEntity prodReturn = repo.findById(id).get();
		
		return prodReturn;
	}
	
	private ProdutoDTO getImage(ProdutoEntity entity) {
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("produto/{id}/image")
				.buildAndExpand(entity.getId()).toUri();
		ProdutoDTO produto = new ProdutoDTO();
		produto.setUrl(uri.toString());
		produto.setCategoria(entity.getCategoria());
		produto.setNome(entity.getNome());
		produto.setDescricao(entity.getDescricao());
		produto.setPreco(entity.getPreco());
		produto.setQuantidadeEmEstoque(entity.getQuantidadeEmEstoque());
		produto.setQuantidadeEmEstoque(entity.getQuantidadeEmEstoque());
		produto.setDataCadastro(entity.getDataCadastro());
		return produto;
	}
	
	
	
	public ProdutoDTO createImage(ProdutoEntity produtoEntity, MultipartFile file) throws IOException {
		
	ProdutoEntity entitySaved = repo.save(produtoEntity);
	imageService.create(file,produtoEntity);
	return getImage(entitySaved);
	
	}
}
