package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoDto extends RepresentationModel<GrupoDto>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerente")
	private String nome;

}
