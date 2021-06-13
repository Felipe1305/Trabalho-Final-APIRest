package com.example.demo.DTO;

import com.example.demo.entities.ClientEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class EnderecoDTO {


	
	@JsonIgnore
	private ClientEntity client;
	
	@JsonIgnore
	private Integer codigoEndereco;
	
	private String rua;
	
	private String bairro;
	
	private String cidade;

	private String Estado;
	
	private String cep;

	private String numero;
	
	private String complemento;
	
	@JsonBackReference
	private Integer codigoCliente;


	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}



	public Integer getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Integer codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public String getRua() {
		return rua;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getEstado() {
		return Estado;
	}

	public Integer getCodigoEndereco() {
		return codigoEndereco;
	}

	public void setCodigoEndereco(Integer codigoEndereco) {
		this.codigoEndereco = codigoEndereco;
	}

	@Override
	public String toString() {
		return "EnderecoDTO [client=" + client + ", codigoEndereco=" + codigoEndereco + ", rua=" + rua + ", bairro="
				+ bairro + ", cidade=" + cidade + ", Estado=" + Estado + ", cep=" + cep + ", numero=" + numero
				+ ", complemento=" + complemento + ", codigoCliente=" + codigoCliente + "]";
	}
	
	
	
}
