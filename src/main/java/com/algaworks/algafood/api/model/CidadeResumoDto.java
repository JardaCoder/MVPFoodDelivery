package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoDto {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Brusque")
	private String nome;
	
	@ApiModelProperty(example = "Santa Catarina")
	private String estado;
	

}
