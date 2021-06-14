package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CategoriaDTO;
import com.example.demo.entities.CategoriaEntity;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.mapper.CategoriaMapper;
import com.example.demo.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repo;
	
	@Autowired
	CategoriaMapper mapper;
	
	public List<CategoriaDTO> findAll(){
		List<CategoriaEntity> listEnt = repo.findAll();
		List<CategoriaDTO> listDTO = new ArrayList<>();
		
		for (CategoriaEntity categoriaEntity : listEnt) {
			listDTO.add( mapper.toDTO(categoriaEntity));
		}
		return listDTO;
	}
	
	public CategoriaDTO create(CategoriaDTO catObj) throws IdNotFoundException  {
		
		CategoriaEntity catEnt = (mapper.toEntity(catObj));
	
		
		return mapper.toDTO(repo.save(catEnt));
	}

	public CategoriaDTO buscarId(Integer id) throws IdNotFoundException {
		if(repo.findById(id).isEmpty()) {
			throw new IdNotFoundException ("Id não encontrado!");
		}
		CategoriaDTO catReturn =mapper.toDTO(repo.findById(id).get());
	
		return catReturn;
	}

	public CategoriaDTO update(Integer id, CategoriaDTO catDTO) throws IdNotFoundException  {
		
		buscarId(id);
		CategoriaDTO  catNew = catDTO;
		CategoriaEntity cat =repo.getById(id);
		
		if(catNew.getNome() != null) {
			cat.setNome(catNew.getNome());
		}
		
		if(catNew.getDescricao()!= null) {
			cat.setDescricao(catNew.getDescricao());
		}
		
		return mapper.toDTO(repo.save(cat));
	}

	public void delete(Integer id) throws IdNotFoundException, DataIntegrityViolationException  {
		buscarId(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException ("Não é possível excluir uma categoria que possui produtos!");
		}
	}

	public Page<CategoriaEntity> search(String nome, Integer page, Integer linhasPorPagina, String orderBy,
			String direction) {
	PageRequest pageRequest = PageRequest.of(page, linhasPorPagina,Direction.valueOf(direction), orderBy);
//	List<CategoriaEntity> categorias = repoCategoria.findAllById(ids);
	return repo.search(nome,pageRequest);
	
	}
	
}
