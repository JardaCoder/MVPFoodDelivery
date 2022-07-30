package com.algaworks.algafood.api.v1.controller;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.FormaPagamentoInputDtoDisassembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDto;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInputDto;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {
	
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamento;
	@Autowired
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
	@Autowired
	private FormaPagamentoInputDtoDisassembler formaPagamentoInputDtoDisassembler;

	
	@Override
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDto>> listar(ServletWebRequest request){
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		var dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if(dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		var formasPagamentoDto = formaPagamentoDtoAssembler.toCollectionModel(formaPagamentoRepository.findAll());
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
				//exemplos
				//.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				//.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				//.cacheControl(CacheControl.noCache())
				//.cacheControl(CacheControl.noStore())
				.eTag(eTag)
				.body(formasPagamentoDto);
	}
	
	@Override
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDto> buscar(@PathVariable("formaPagamentoId") Long id, ServletWebRequest request){
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		var dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacaoById(id);
		
		if(dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		 var formaPagamento = formaPagamentoDtoAssembler
				 .toModel(cadastroFormaPagamento.buscarOuFalhar(id));
		 
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
				.eTag(eTag)
				.body(formaPagamento);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto criar(@RequestBody @Valid FormaPagamentoInputDto formaPagamentoInputDto){
		FormaPagamento formaPagamento = cadastroFormaPagamento.salvar(formaPagamentoInputDtoDisassembler.formaPagamentoInputDtoToFormaPagamento(formaPagamentoInputDto));
		return formaPagamentoDtoAssembler.toModel(formaPagamento);
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDto editar(@PathVariable("formaPagamentoId") Long id, @RequestBody @Valid FormaPagamentoInputDto formaPagamentoInputDto){
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(id);
		
		formaPagamentoInputDtoDisassembler.copyToDomainObject(formaPagamentoInputDto, formaPagamentoAtual);
		
		formaPagamentoAtual  = cadastroFormaPagamento.salvar(formaPagamentoAtual);
		
		return formaPagamentoDtoAssembler.toModel(formaPagamentoAtual);
	}
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("formaPagamentoId") Long id){
		cadastroFormaPagamento.excluir(id);
	}
}
