package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.CategoriaDTO;
import com.example.demo.controllers.utils.URL;
import com.example.demo.entities.CategoriaEntity;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.exceptions.NullPointerExceptionCategoria;
import com.example.demo.mapper.CategoriaMapper;
import com.example.demo.services.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	CategoriaService service;

	@Autowired
	CategoriaMapper mapperCat;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		return ResponseEntity.ok().header("Method: ", "Find All").body(service.findAll());
	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO catObj) throws IdNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Method: ", "Create");
		CategoriaDTO body = service.create(catObj);

		return new ResponseEntity<CategoriaDTO>(body, headers, HttpStatus.CREATED);
	}
	// return new ResponseEntity<TodoEntity>(body, headers, HttpStatus.OK);

	@GetMapping("/{id}")
	public CategoriaDTO getById(@PathVariable Integer id) throws IdNotFoundException {

		return service.buscarId(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> update(@PathVariable Integer id, @RequestBody CategoriaDTO catEnt)
			throws IdNotFoundException, NullPointerExceptionCategoria {
		return ResponseEntity.ok().header("Method: ", "Update").body(service.update(id, catEnt));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) throws IdNotFoundException {
		service.delete(id);
		return ResponseEntity.ok("Deletado com sucesso!");
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "nome", defaultValue = "") String nome,

			@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

//			List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);

		Page<CategoriaEntity> list = service.search(nomeDecoded, page, linhasPorPagina, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> mapperCat.toDTO(obj));
		return ResponseEntity.ok().body(listDTO);

	}

}
