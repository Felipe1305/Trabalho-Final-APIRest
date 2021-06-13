package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.PedidoDTO;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.exceptions.NullPointerExceptionClient;
import com.example.demo.exceptions.NullPointerExceptionPedido;
import com.example.demo.exceptions.QuantindadeInsuficienteException;
import com.example.demo.services.PedidoService;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	@Autowired
	PedidoService service;



	@GetMapping(value="/buscar-todos")
	public ResponseEntity<List<PedidoDTO>> findAll() {
		return ResponseEntity.ok().header("Method: ", "Find All").body(service.findAll());
	}

//		@PostMapping
//		public ResponseEntity<PedidoDTO> create (@RequestBody PedidoDTO prodObj){
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Method: ", "Create");
//			PedidoDTO body = service.create(prodObj);
//			
//			return new ResponseEntity<PedidoDTO>(body,headers,HttpStatus.CREATED);
//		}

	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> findById(@PathVariable Long id) throws IdNotFoundException {
		return ResponseEntity.ok().header("Method: ", "Get By Id").body(service.getById(id));
	}

//		@PutMapping("/{id}")
//		public ResponseEntity<PedidoDTO> update(@PathVariable Integer id, @RequestBody PedidoDTO prodEnt) throws IdNotFoundException {
//			return ResponseEntity.ok().header("Method: ", "Update").body(service.update(id, prodEnt));
//		}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws IdNotFoundException {
		service.delete(id);
		return ResponseEntity.ok("Deletado com sucesso!");
	}

	@PostMapping("/create/{clientId}")
	public ResponseEntity<String> inserirPedido(@PathVariable Integer clientId, @RequestBody PedidoDTO pedDTO)
			throws IdNotFoundException, QuantindadeInsuficienteException, NullPointerExceptionClient {

		service.createPedido(pedDTO, clientId);
		return ResponseEntity.ok("Pedido Inserido com sucesso!");
	}

	@PostMapping("/inserir-item/{pedidoId}/{clientId}")
	public ResponseEntity<String> inserirItem(@PathVariable Long pedidoId, @PathVariable Integer clientId,
			@RequestBody PedidoDTO pedDTO) throws IdNotFoundException, NullPointerExceptionClient,
			NullPointerExceptionPedido, QuantindadeInsuficienteException {
		service.inserirItem(pedDTO, pedidoId, clientId);
		return ResponseEntity.ok("Item inserido com sucesso!");
	}

	@PostMapping("/finalizar-pedido/{pedidoId}")
	public ResponseEntity<String> finalizarPedido(@PathVariable Long pedidoId, @RequestBody PedidoDTO pedDTO)
			throws IdNotFoundException {
		service.finalizarPedido(pedidoId, pedDTO);
		return ResponseEntity.ok(
				"Pedido finalizado com sucesso! \nTe enviamos um e-mail com as informações do pedido e dados da entrega!");
	}

//	@RequestMapping(value = "/emailsend", method = RequestMethod.POST)


}
