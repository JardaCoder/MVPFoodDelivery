package com.algaworks.algafood.api.controller.openapi.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.CozinhaDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestauranteBasicoDtoOpenApi {

	
	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Jarda Burguer")
	private String nome;

	@ApiModelProperty(example = "10.00")
	private BigDecimal	taxaFrete;

	private CozinhaDto cozinha;

}
