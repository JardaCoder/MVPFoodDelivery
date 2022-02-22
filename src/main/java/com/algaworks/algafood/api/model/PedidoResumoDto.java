package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDto {

	@ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
	private String codigo;
	
	@ApiModelProperty(example = "200.00")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "210.00")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CONFIRMADO")
	private String status;
	
	@ApiModelProperty(example = "2022-02-11T14:13:25Z")
	private OffsetDateTime dataCriacao;
	
	private RestauranteResumoDto restaurante;
	
	private UsuarioDto cliente;
	
}
