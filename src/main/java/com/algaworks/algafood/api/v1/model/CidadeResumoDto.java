package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoDto extends RepresentationModel<CidadeResumoDto> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Brusque")
	private String nome;
	
	@ApiModelProperty(example = "Santa Catarina")
	private String estado;
	

}
