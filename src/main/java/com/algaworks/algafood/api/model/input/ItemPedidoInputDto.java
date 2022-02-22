package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInputDto {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "2", required = true)
	@NotNull
	@Min(value = 1)
	private Integer quantidade;
	
	@ApiModelProperty(example = "Carne mal passada")
	private String observacao;
	
}
