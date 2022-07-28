package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "fotos")
@Getter
@Setter
public class FotoProdutoDto extends RepresentationModel<FotoProdutoDto>{
	
	
	@ApiModelProperty(example = "prime.png")
	private String nomeArquivo;
	
	@ApiModelProperty(example = "prime rib suculento")
	private String descricao;
	
	@ApiModelProperty(example = "image/jpeg")
	private String contentType;
	
	@ApiModelProperty(example = "389590")
	private String tamanho;

}
