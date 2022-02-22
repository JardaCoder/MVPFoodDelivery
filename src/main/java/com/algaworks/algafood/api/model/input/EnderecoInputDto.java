package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInputDto {


	@ApiModelProperty(example = "88360-000", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua andré mourão", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "176", required = true)
	@NotBlank
	private String numero;

	@ApiModelProperty(example = "Terceira casa do lado esquerdo")
	private String complemento;

	@ApiModelProperty(example = "São pedro", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	@ApiModelProperty(required = true)
	private CidadeIdInputDto cidade;
	
}
