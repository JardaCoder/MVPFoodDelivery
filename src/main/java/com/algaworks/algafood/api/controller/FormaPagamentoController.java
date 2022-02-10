package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoInputDtoDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDto;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/formas-pagamento")
public class FormaPagamentoController {
	
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamento;
	@Autowired
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
	@Autowired
	private FormaPagamentoInputDtoDisassembler formaPagamentoInputDtoDisassembler;

	
	@GetMapping
	public List<FormaPagamentoDto> listar(){
		return formaPagamentoDtoAssembler.formasPagamentoToListFormaPagamentoDto(formaPagamentoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public FormaPagamentoDto buscar(@PathVariable("estadoId") Long id){
		return formaPagamentoDtoAssembler.formaPagamentoToFormaPagamentoDto(cadastroFormaPagamento.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto criar(@RequestBody @Valid FormaPagamentoInputDto formaPagamentoInputDto){
		FormaPagamento formaPagamento = cadastroFormaPagamento.salvar(formaPagamentoInputDtoDisassembler.formaPagamentoInputDtoToFormaPagamento(formaPagamentoInputDto));
		return formaPagamentoDtoAssembler.formaPagamentoToFormaPagamentoDto(formaPagamento);
	}
	
	@PutMapping("/{estadoId}")
	public FormaPagamentoDto editar(@PathVariable("estadoId") Long id, @RequestBody @Valid FormaPagamentoInputDto formaPagamentoInputDto){
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(id);
		
		formaPagamentoInputDtoDisassembler.copyToDomainObject(formaPagamentoInputDto, formaPagamentoAtual);
		
		formaPagamentoAtual  = cadastroFormaPagamento.salvar(formaPagamentoAtual);
		
		return formaPagamentoDtoAssembler.formaPagamentoToFormaPagamentoDto(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("estadoId") Long id){
		cadastroFormaPagamento.excluir(id);
	}
}
