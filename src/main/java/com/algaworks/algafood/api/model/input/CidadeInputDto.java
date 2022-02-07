package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDto {

	@NotBlank
	private String nome;
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	@NotNull
	@Valid
	private EstadoIdInputDto estado;
}
