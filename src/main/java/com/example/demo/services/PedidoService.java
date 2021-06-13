package com.example.demo.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.PedidoDTO;
import com.example.demo.DTO.ProdutosPedidosDTO;
import com.example.demo.entities.PedidoEntity;
import com.example.demo.entities.ProdutoEntity;
import com.example.demo.entities.ProdutosPedidos;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.exceptions.NullPointerExceptionClient;
import com.example.demo.exceptions.NullPointerExceptionPedido;
import com.example.demo.exceptions.QuantindadeInsuficienteException;
import com.example.demo.mapper.PedidoMapper;
import com.example.demo.mapper.ProdutoMapper;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.ProdutoRepository;
import com.example.demo.repositories.ProdutosPedidosRepository;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repo;

	@Autowired
	ProdutosPedidosRepository prodPedRepository;

	@Autowired
	ClientRepository clientRepo;

	@Autowired
	ProdutoRepository produtoRepo;

	@Autowired
	ProdutoService serviceProduto;

	@Autowired
	ProdutoMapper mapperProd;

	@Autowired
	PedidoMapper mapper;

	@Autowired
	ProdutoMapper produtoMapper;

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired ClientService serviceClient;

//	@Autowired
//	MailConfig send;

	public List<PedidoDTO> findAll() {
		List<PedidoEntity> list = new ArrayList<>();
		list.addAll(repo.findAll());
		List<PedidoDTO> listDTO = new ArrayList<>();
		for (PedidoEntity pedidoEntity : list) {
			listDTO.add(mapper.toDTO(pedidoEntity));
		}
		return listDTO;
	}

	public PedidoDTO createPedido(PedidoDTO pedDTO, Integer id) throws IdNotFoundException, QuantindadeInsuficienteException, NullPointerExceptionClient {
		try {
			serviceClient.buscarId(id);
			} catch (NullPointerException e) {
				throw new NullPointerExceptionClient ("Cliente não encontrado para esse pedido!");
			}
		
		Double valorTotal = 0.0;
		pedDTO.setCliente(clientRepo.findById(id).get());

		PedidoEntity pedidoSaved = repo.save(mapper.toEntity(pedDTO));
		for (ProdutosPedidosDTO produto : pedDTO.getListaProdutos()) {

			ProdutoEntity produtoFind = serviceProduto.getByIdInterno(produto.getId());
			produtoFind.setId(produto.getId());
			ProdutosPedidos prodPed = new ProdutosPedidos();
			prodPed.setPedido(pedidoSaved);
			prodPed.setPreco((double) serviceProduto.getByIdInterno(produto.getId()).getPreco());
			prodPed.setProduto(produtoFind);
			prodPed.setQuantidade(produto.getQuantidade());
//			System.out.println(produtoFind);
			prodPedRepository.save(prodPed);
			valorTotal += serviceProduto.getByIdInterno(produto.getId()).getPreco() * produto.getQuantidade();
			
			if(produto.getQuantidade() > produtoFind.getQuantidadeEmEstoque()) {
				throw new QuantindadeInsuficienteException ("Quantidade em estoque indisponível!");
			}
			produtoFind.setQuantidadeEmEstoque(produtoFind.getQuantidadeEmEstoque() - (produto.getQuantidade()));
			produtoRepo.save(produtoFind);

		}

		pedidoSaved.setStatus("Pendente");
		pedidoSaved.setDataDoPedido(LocalDate.now());
		pedidoSaved.setValorTotalDoPedido(valorTotal);
		repo.save(pedidoSaved);

//		send.sendMail("falmeida.1305@gmail.com", "Trabalho", "Pedido cadastrado com sucesso!");

		return pedDTO;
	}

	public void inserirItem(PedidoDTO pedDTO, Long pedidoId, Integer clientId) throws IdNotFoundException,
			NullPointerExceptionClient, NullPointerExceptionPedido, QuantindadeInsuficienteException {
		try {
		serviceClient.buscarId(clientId);
		} catch (NullPointerException e) {
			throw new NullPointerExceptionClient ("Cliente não encontrado para esse pedido!");
		}
		try {
		PedidoEntity pedido = repo.findById(pedidoId).get();
		} catch (NoSuchElementException e) {
			throw new NullPointerExceptionPedido ("Pedido não encontrado!");
		}
		PedidoEntity pedido = repo.findById(pedidoId).get();
		Double valorTotal = 0.0;
		for (ProdutosPedidosDTO produto : pedDTO.getListaProdutos()) {
			ProdutoEntity produtoFind = serviceProduto.getByIdInterno(produto.getId());
			produtoFind.setId(produto.getId());
			ProdutosPedidos prodPed = new ProdutosPedidos();
			prodPed.setPedido(pedido);
			prodPed.setPreco((double) serviceProduto.getByIdInterno(produto.getId()).getPreco());
			prodPed.setProduto(produtoFind);
			prodPed.setQuantidade(produto.getQuantidade());
			prodPedRepository.save(prodPed);
			valorTotal += serviceProduto.getByIdInterno(produto.getId()).getPreco() * produto.getQuantidade();
			if(produto.getQuantidade() > produtoFind.getQuantidadeEmEstoque()) {
				throw new QuantindadeInsuficienteException ("Quantidade em estoque indisponível!");
			}
			produtoFind.setQuantidadeEmEstoque(produtoFind.getQuantidadeEmEstoque() - (produto.getQuantidade()));
			produtoRepo.save(produtoFind);
		}
		pedido.setValorTotalDoPedido(pedido.getValorTotalDoPedido() + valorTotal);
		repo.save(pedido);
	}

	public void finalizarPedido(Long pedidoId, PedidoDTO pedDTO) throws IdNotFoundException {
		System.out.println(pedDTO.getStatus());
		if (pedDTO.getStatus().contains("finalizado")) {
			PedidoEntity pedido = repo.findById(pedidoId).get();
			pedido.setStatus("Finalizado");
			
			pedido.setDataDaEntrega(LocalDate.now().plusDays(10));
			PedidoDTO dto = mapper.toDTO(repo.save(pedido));
			sendMail(dto);
		}
	
	}


	public String sendMail(PedidoDTO pedido) throws IdNotFoundException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Pedido finalizado com sucesso! \n\n" + pedido);
		message.setTo("falmeida.1305@gmail.com");
		message.setSubject("Pedido finalizado!");
		message.setFrom("trabalhoapiserratec@gmail.com");

		javaMailSender.send(message);
		return "Email enviado com sucesso!";
	}

	public PedidoDTO getById(Long id) throws IdNotFoundException {

		if (repo.findById(id).isEmpty()) {
			throw new IdNotFoundException("Id não encontrado!");
		}
		PedidoDTO pedReturn = mapper.toDTO(repo.findById(id).get());

		return pedReturn;
	}

//	public PedidoDTO update(Integer id, PedidoDTO pedEnt) throws IdNotFoundException {
//		PedidoDTO  pedNew = pedEnt;
//		
//		PedidoEntity ped = mapper.toEntity(getById(id));
//		return mapper.toDTO(repo.save(prod));
//	}

	public void delete(Long id) throws IdNotFoundException {
		getById(id);
		repo.deleteById(id);
	}

	public List<PedidoDTO> findAllById(Integer id) {
		List<PedidoEntity> list = repo.findByClienteId(id);
		List<PedidoDTO> listDTO = new ArrayList<>();
		for (PedidoEntity pedidoEntity : list) {
			listDTO.add(mapper.toDTO(pedidoEntity));
		}

		return listDTO;

	}

}
