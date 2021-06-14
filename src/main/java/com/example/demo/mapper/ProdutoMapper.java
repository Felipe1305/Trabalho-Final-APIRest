package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.entities.ProdutoEntity;

@Component
public class ProdutoMapper {
	
	
	public ProdutoDTO toDTO(ProdutoEntity prodEnt) {
		ProdutoDTO dto = new ProdutoDTO();
		
		dto.setNome(prodEnt.getNome());
		dto.setDescricao(prodEnt.getDescricao());
		dto.setDataCadastro(prodEnt.getDataCadastro());
		dto.setImagem(prodEnt.getImagem());
		dto.setPreco(prodEnt.getPreco());
		dto.setQuantidadeEmEstoque(prodEnt.getQuantidadeEmEstoque());
		dto.setCategoria(prodEnt.getCategoria());
		dto.setUrl(prodEnt.getURL());
		return dto;
	}
	
	public ProdutoEntity toEntity (ProdutoDTO dto) {
		ProdutoEntity entity = new ProdutoEntity();
		
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		entity.setDataCadastro(dto.getDataCadastro());
		entity.setImagem(dto.getImagem());
		entity.setPreco(dto.getPreco());
		entity.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
		entity.setCategoria(dto.getCategoria());
		entity.setURL(dto.getUrl());
		return entity;
	}
}
