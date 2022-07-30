package com.algaworks.algafood.api.v1.model.input;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInputDto {
	
	@ApiModelProperty(required = true)
	@NotNull
	@Valid
	private RestauranteIdInputDto restaurante;
	
	@ApiModelProperty(required = true)
	@NotNull
	@Valid
	private FormaPagamentoIdInputDto formaPagamento;
	
	@ApiModelProperty(required = true)
	@NotNull
	@Valid
	private EnderecoInputDto enderecoEntrega;
	
	@ApiModelProperty(required = true)
	@NotNull
	@Size(min = 1)
	@Valid
	private List<ItemPedidoInputDto> itens = new ArrayList<ItemPedidoInputDto>();
	
	
	
}
