package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDto {
	
	
	@ApiModelProperty(example = "1")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;

	@ApiModelProperty(example = "Jarda Burguer")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;

	@ApiModelProperty(example = "10.00")
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal	taxaFrete;

	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDto cozinha;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
	@ApiModelProperty(example = "true")
	private Boolean aberto;
	
	private EnderecoDto endereco;
}
