package com.example.demo.controllers;


import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.ProdutoDTO;
import com.example.demo.controllers.utils.URL;
import com.example.demo.entities.ImageEntity;
import com.example.demo.entities.ProdutoEntity;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.mapper.ProdutoMapper;
import com.example.demo.services.ImageService;
import com.example.demo.services.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
		
		@Autowired 
		ProdutoService service;
		
		@Autowired
		ProdutoMapper mapperProd;
		
		@Autowired
		ImageService imageService;
		
		@GetMapping
		public ResponseEntity<List<ProdutoDTO>> findAll () {
			return ResponseEntity.ok().header("Method: ", "Find All").body(service.findAll());
		}
		
		@PostMapping
		public ResponseEntity<ProdutoDTO> create (@RequestBody ProdutoDTO prodObj){
			HttpHeaders headers = new HttpHeaders();
			headers.add("Method: ", "Create");
			ProdutoDTO body = service.create(prodObj);
			
			return new ResponseEntity<ProdutoDTO>(body,headers,HttpStatus.CREATED);
		}

		@GetMapping("/{id}")
		public ResponseEntity<ProdutoDTO> findById(@PathVariable Integer id) throws IdNotFoundException{
			return ResponseEntity.ok().header("Method: ", "Get By Id").body(service.getById(id));
		}
		
		@PutMapping("/{id}")
		public ResponseEntity<ProdutoDTO> update(@PathVariable Integer id, @RequestBody ProdutoDTO prodDTO) throws IdNotFoundException {
			return ResponseEntity.ok().header("Method: ", "Update").body(service.update(id, prodDTO));
		}
		
		@DeleteMapping("/{id}")
		public ResponseEntity<String> delete(@PathVariable Integer id) throws IdNotFoundException {
			service.delete(id);
			return ResponseEntity.ok("Deletado com sucesso!");
		}
		
		@RequestMapping(value= "/search",method = RequestMethod.GET)
		public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
				@RequestParam(value = "nome", defaultValue = "") String nome,
				@RequestParam(value = "categorias", defaultValue = "") String categorias,
				
				@RequestParam(value = "linhasPorPagina", defaultValue = "24") Integer linhasPorPagina,
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
				@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

//			List<Integer> ids = URL.decodeIntList(categorias);
			String nomeDecoded = URL.decodeParam(nome);
			
			Page<ProdutoEntity> list = service.search(nomeDecoded,page, linhasPorPagina, orderBy, direction);
			Page<ProdutoDTO> listDTO = list.map(obj -> mapperProd.toDTO(obj));
			return ResponseEntity.ok().body(listDTO);

		}
		
		@PostMapping("/image")
		public ProdutoDTO create (@RequestParam MultipartFile file, @RequestPart ProdutoEntity produtoEntity) throws IOException {
		
			return service.createImage(produtoEntity, file);
		}
		
		@GetMapping(path = "/{id}/image")
		public ResponseEntity<byte[]> getImagem(@PathVariable Integer id) throws Exception{
			ImageEntity imagem = imageService.getImage(id);
			HttpHeaders header = new HttpHeaders();
			header.add("content-length", String.valueOf(imagem.getData().length));
			header.add("content-type", imagem.getMimetype());
			return new ResponseEntity<>(imagem.getData(),header,HttpStatus.OK);
		}
		
		

}
