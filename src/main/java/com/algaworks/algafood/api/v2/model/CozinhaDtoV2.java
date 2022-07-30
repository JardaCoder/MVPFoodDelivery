package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhaDto")
@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDtoV2 extends RepresentationModel<CozinhaDtoV2> {
	
	@ApiModelProperty(example = "1")
	//@JsonView(RestauranteView.Resumo.class)
	private Long idCozinha;
	
	@ApiModelProperty(example = "Tailandesa")
	//@JsonView(RestauranteView.Resumo.class)
	private String nomeCozinha;
}
