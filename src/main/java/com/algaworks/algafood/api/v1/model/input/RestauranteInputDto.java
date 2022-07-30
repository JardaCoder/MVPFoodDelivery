package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputDto {

	@ApiModelProperty(example = "Jarda burgues", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "10.00", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(required = true)
	@Valid
	@NotNull
	private CozinhaIdInputDto cozinha;
	
	@ApiModelProperty(required = true)
	@NotNull
	@Valid
	private EnderecoInputDto endereco;
}
