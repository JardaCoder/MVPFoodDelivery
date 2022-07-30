package com.algaworks.algafood.api.v2.controller.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PageModel")
@Getter
@Setter
public class PageDtoOpenApiV2 {
	
	@ApiModelProperty(example="10", value="Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example="50", value="Total de elementos com a soma de todas as páginas.")
	private int totalElements;
	
	@ApiModelProperty(example="10",value="Total de páginas")
	private int totalPages;
	
	@ApiModelProperty(example="0", value="Página atual (começa em 0)")
	private int number;
	

}
