package com.example.demo.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.ImageEntity;
import com.example.demo.entities.ProdutoEntity;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.ProdutoRepository;

@Service
public class ImageService {
	
	@Autowired
	ImageRepository ImageRepo;
	
	@Autowired
	ProdutoRepository ProdutoRepo;
	
	@Autowired
	ProdutoService ProdutoService;
	
	@Transactional
	public ImageEntity create( MultipartFile file, ProdutoEntity produtoEntity) throws IOException {
		ImageEntity image = new ImageEntity();
		image.setName("Imagem");
		image.setMimetype(file.getContentType());
		image.setData(file.getBytes());
		image.setProduto(produtoEntity);
		return ImageRepo.save(image);
	}
	
	@Transactional
	public ImageEntity getImage(Integer id) {
		ImageEntity produto = ImageRepo.findByProdutoId(id);
		System.out.println(produto.toString());
		return produto;
		
	}
	
//	@Transactional
//	public ImageEntity getImagem(String nome) throws Exception {
//		ProdutoEntity produto = ProdutoService.findBySearch(nome);
//		return ProdutoRepo.findByProduto(produto);
//	}
//	
	

}