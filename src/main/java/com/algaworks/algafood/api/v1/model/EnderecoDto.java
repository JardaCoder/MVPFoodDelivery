package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDto {


	@ApiModelProperty(example = "88360-000")
	private String cep;
	
	@ApiModelProperty(example = "Rua andré mourão")
	private String logradouro;
	
	@ApiModelProperty(example = "176")
	private String numero;
	
	@ApiModelProperty(example = "Terceira casa do lado esquerdo")
	private String complemento;
	
	@ApiModelProperty(example = "São Pedro")
	private String bairro;
	
	private CidadeResumoDto cidade;
}
