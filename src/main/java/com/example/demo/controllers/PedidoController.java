package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.PedidoDTO;
import com.example.demo.entities.PedidoEntity;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.services.PedidoService;

@RestController
@RequestMapping("/produto")
public class PedidoController {
		
		@Autowired 
		PedidoService service;
		
		@GetMapping
		public ResponseEntity<List<PedidoDTO>> findAll () {
			return ResponseEntity.ok().header("Method: ", "Find All").body(service.findAll());
		}
		
		@PostMapping
		public ResponseEntity<PedidoDTO> create (@RequestBody PedidoDTO prodObj){
			HttpHeaders headers = new HttpHeaders();
			headers.add("Method: ", "Create");
			PedidoDTO body = service.create(prodObj);
			
			return new ResponseEntity<PedidoDTO>(body,headers,HttpStatus.CREATED);
		}

		@GetMapping("/{id}")
		public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) throws IdNotFoundException{
			return ResponseEntity.ok().header("Method: ", "Get By Id").body(service.getById(id));
		}
		
//		@PutMapping("/{id}")
//		public ResponseEntity<PedidoDTO> update(@PathVariable Integer id, @RequestBody PedidoDTO prodEnt) throws IdNotFoundException {
//			return ResponseEntity.ok().header("Method: ", "Update").body(service.update(id, prodEnt));
//		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<String> delete(@PathVariable Long id) throws IdNotFoundException {
			service.delete(id);
			return ResponseEntity.ok("Deletado com sucesso!");
		}

}