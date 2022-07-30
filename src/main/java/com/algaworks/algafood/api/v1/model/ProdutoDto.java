package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoDto extends RepresentationModel<ProdutoDto>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Costela ao molho barbecue")
	private String nome;
	
	@ApiModelProperty(example = "Costelinha desmanchando")
	private String descricao;
	
	@ApiModelProperty(example = "100.00")
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;

}
