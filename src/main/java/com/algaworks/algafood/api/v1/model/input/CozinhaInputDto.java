package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaInputDto {

	@ApiModelProperty(example = "Grega", required = true)
	@NotBlank
	private String nome;
}
