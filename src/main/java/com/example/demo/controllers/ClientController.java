package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ClientDTO;
import com.example.demo.DTO.PedidoDTO;
import com.example.demo.exceptions.CPFException;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.mapper.ProdutoMapper;
import com.example.demo.repositories.ProdutoRepository;
import com.example.demo.services.ClientService;
import com.example.demo.services.PedidoService;
import com.example.demo.services.ProdutoService;

@RestController
@RequestMapping("/cliente")
public class ClientController {

	@Autowired
	ClientService service;
	
	@Autowired
	PedidoService servicePedido;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ProdutoMapper mapperProd;	
	
	@Autowired
	ProdutoRepository repoProd;
	


	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		return ResponseEntity.ok().header("Method: ", "Find All").body(service.findAll());
	}

	@PostMapping(value="/cadastro")
	public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO catObj) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Method: ", "Create");
		
		ClientDTO body = service.create(catObj);

		return new ResponseEntity<ClientDTO>(body, headers, HttpStatus.CREATED);
	}
	// return new ResponseEntity<TodoEntity>(body, headers, HttpStatus.OK);

	@GetMapping("/{id}")
	public ClientDTO getById(@PathVariable Integer id) throws IdNotFoundException {

		return service.buscarId(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientDTO catDTO)
			throws IdNotFoundException, CPFException {

		return ResponseEntity.ok().header("Method: ", "Update").body(service.update(id, catDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) throws IdNotFoundException {
		service.delete(id);
		return ResponseEntity.ok("Deletado com sucesso!");
	}
	

	@GetMapping("/{id}/pedidos")
	public ResponseEntity<List<PedidoDTO>> getAllPedidos(@PathVariable  Integer id){
	  List<PedidoDTO> list = servicePedido.findAllById(id);
		return ResponseEntity.ok().body(list);
	}

}
