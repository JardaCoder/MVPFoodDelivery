package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteBasicoDto extends RepresentationModel<RestauranteBasicoDto> {
	
	
	@ApiModelProperty(example = "1")
	//@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;

	@ApiModelProperty(example = "Jarda Burguer")
	//@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;

	@ApiModelProperty(example = "10.00")
	//@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;

	//RestauranteResumoDtoRestauranteResumoDto@JsonView(RestauranteView.Resumo.class)
	private CozinhaDto cozinha;
	
}
