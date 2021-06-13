package com.example.demo.DTO;

import java.time.LocalDate;

import com.example.demo.entities.CategoriaEntity;
import com.example.demo.entities.ImageEntity;
import com.example.demo.entities.ProdutoEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProdutoDTO {
	
	@JsonIgnore
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private Double preco;
	
	private Integer quantidadeEmEstoque;
	
	private LocalDate dataCadastro;
	
	@JsonIgnore
	private ImageEntity imagem;

	private String url;
	
	@JsonBackReference
	private Integer codigoCategoria;
	
//	@GeneratedValue(strategy=GenerationType.TABLE)

	
	
	@JsonIgnore
	private CategoriaEntity categoria;
	
public ProdutoDTO() {
	super();
}

//	public ProdutoDTO(ProdutoEntity obj) {
//		this.id = obj.getId();
//		this.nome = obj.getNome();
//		this.preco = obj.getPreco();
//	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public ImageEntity getImagem() {
		return imagem;
	}

	public void setImagem(ImageEntity imagem) {
		this.imagem = imagem;
	}
	public Integer getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(Integer codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public CategoriaEntity getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEntity categoria) {
		this.categoria = categoria;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "\nProduto  Nome: " + nome + "\nPreco: " + preco + "\n\n";
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	
	
}
