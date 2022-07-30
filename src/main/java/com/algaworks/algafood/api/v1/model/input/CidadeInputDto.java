package com.algaworks.algafood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDto {

	@ApiModelProperty(example = "Santa Catarina", required = true)
	@NotBlank
	private String nome;
	
	
	@ApiModelProperty(required = true)
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	@NotNull
	@Valid
	private EstadoIdInputDto estado;
}
