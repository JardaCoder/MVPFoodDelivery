package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.RestauranteFormaPagamentoOpenApi;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoControlller implements RestauranteFormaPagamentoOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
	@Autowired
	private JardaLinks jardaLinks;
	
	@Override
	@GetMapping
	public CollectionModel<FormaPagamentoDto> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		
		CollectionModel<FormaPagamentoDto> formasPagamento = formaPagamentoDtoAssembler
				.toCollectionModel(restaurante.getFormasPagamento())
				.removeLinks()
				.add(jardaLinks.linkToRestauranteFormasPagamento(restauranteId))
				.add(jardaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
		
		formasPagamento.getContent().forEach(formaPagamentoDto ->{
			formaPagamentoDto.add(
					jardaLinks.linkToRestauranteFormaPagamentoDesassociacao(
							restauranteId, formaPagamentoDto.getId(), "desassociar"));
		});
		
		return formasPagamento;
	}
	
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
}



