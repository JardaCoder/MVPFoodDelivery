package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDto {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Costela ao molho barbecue")
	private String nome;
	
	@ApiModelProperty(example = "Costelinha desmanchando")
	private String descricao;
	
	@ApiModelProperty(example = "100.00")
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;

}
