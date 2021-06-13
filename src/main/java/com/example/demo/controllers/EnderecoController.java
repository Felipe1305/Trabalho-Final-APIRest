package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.EnderecoDTO;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.services.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired 
	EnderecoService service;
	
	@GetMapping
	public ResponseEntity<List<EnderecoDTO>> findAll () {
		return ResponseEntity.ok().header("Method: ", "Find All").body(service.findAll());
	}
	
	@PostMapping("/create")
	public ResponseEntity<EnderecoDTO> create (@RequestBody EnderecoDTO catObj) throws HttpClientErrorException{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Method: ", "Create");
		EnderecoDTO body = service.create(catObj.getCep(),catObj);
		
		return new ResponseEntity<EnderecoDTO>(body,headers,HttpStatus.CREATED);
	}
	//return new ResponseEntity<TodoEntity>(body, headers, HttpStatus.OK);
	
	@GetMapping("/{id}")
	public EnderecoDTO getById(@PathVariable Integer id) throws IdNotFoundException{
	 
	
	return	service.buscarId(id);
	}
	
//	@PutMapping("/{id}")
//	public ResponseEntity<EnderecoDTO> update(@PathVariable Integer id, @RequestBody EnderecoDTO catEnt) {
//		return ResponseEntity.ok().header("Method: ", "Update").body(service.update(id, catEnt));
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) throws IdNotFoundException {
		service.delete(id);
		return ResponseEntity.ok("Deletado com sucesso!");
	}
	
}
