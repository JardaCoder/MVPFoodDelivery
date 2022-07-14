package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDto extends RepresentationModel<ItemPedidoDto>{

	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Costelinha de porco ao molho barbecue")
	private String produtoNome;
	
	@ApiModelProperty(example = "2")
	private Integer quantidade;
	
	@ApiModelProperty(example = "100")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "200")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Com bastante barbecue")
	private String observacao;
}
