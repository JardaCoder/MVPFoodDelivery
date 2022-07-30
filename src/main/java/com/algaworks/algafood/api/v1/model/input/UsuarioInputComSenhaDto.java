package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputComSenhaDto extends UsuarioInputDto {

	@ApiModelProperty(example = "123456", required = true)
	@NotBlank
	private String senha;

}
