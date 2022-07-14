package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioDto extends RepresentationModel<UsuarioDto>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Jardel")
	private String nome;
	
	@ApiModelProperty(example = "jardel@example.com")
	private String email;
}
