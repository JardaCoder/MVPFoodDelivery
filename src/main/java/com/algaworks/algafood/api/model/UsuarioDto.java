package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Jardel")
	private String nome;
	
	@ApiModelProperty(example = "jardel@example.com")
	private String email;
}
