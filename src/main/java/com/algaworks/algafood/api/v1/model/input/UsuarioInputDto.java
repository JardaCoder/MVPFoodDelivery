package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputDto {

	@ApiModelProperty(example = "Jardel", required = true)
	@NotBlank
	private String nome;
	
	
	@ApiModelProperty(example = "jardel@example.com", required = true)
	@Email
	@NotBlank
	private String email;
}
