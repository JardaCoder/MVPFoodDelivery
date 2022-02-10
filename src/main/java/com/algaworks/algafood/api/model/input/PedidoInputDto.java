package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInputDto {
	
	@NotNull
	@Valid
	private RestauranteIdInputDto restaurante;
	
	@NotNull
	@Valid
	private FormaPagamentoIdInputDto formaPagamento;
	
	@NotNull
	@Valid
	private EnderecoInputDto enderecoEntrega;
	
	@NotNull
	@Size(min = 1)
	@Valid
	private List<ItemPedidoInputDto> itens;
	
	
	
}
