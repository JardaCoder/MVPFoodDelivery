package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDtoDisassembler;
import com.algaworks.algafood.api.model.PedidoDto;
import com.algaworks.algafood.api.model.PedidoResumoDto;
import com.algaworks.algafood.api.model.input.PedidoInputDto;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	EmissaoPedidoService cadastroPedido;
	@Autowired
	private PedidoDtoAssembler pedidoDtoAssembler;
	@Autowired
	private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;
	@Autowired
	private PedidoInputDtoDisassembler pedidoInputDtoDisassembler;
	
	
	@GetMapping
	public List<PedidoResumoDto> listar(){
		return pedidoResumoDtoAssembler.pedidoResumosToListPedidoResumoDto(pedidoRepository.findAll());
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDto buscar(@PathVariable("codigoPedido") String codigoPedido){
		return pedidoDtoAssembler.pedidoToPedidoDto(cadastroPedido.buscarOuFalhar(codigoPedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDto criar(@RequestBody @Valid PedidoInputDto pedidoInputDto){
		
		try {
			var cliente = new Usuario();
			
			cliente.setId(1L);
			
			Pedido pedido = pedidoInputDtoDisassembler.pedidoInputDtoToPedido(pedidoInputDto);
			
			pedido.setCliente(cliente);
			
			pedido = cadastroPedido.processarPedido(pedido);
			
			return pedidoDtoAssembler.pedidoToPedidoDto(pedido);
			
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
