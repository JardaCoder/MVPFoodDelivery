package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PagedDtoOpenApi<T> {
	
	
	@ApiModelProperty(value= "Página de elementos")
	private List<T> content;
	
	@ApiModelProperty(example="10", value="Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example="50", value="Total de elementos com a soma de todas as páginas.")
	private int totalElements;
	
	@ApiModelProperty(example="10",value="Total de páginas")
	private int totalPages;
	
	@ApiModelProperty(example="0", value="Página atual (começa em 0)")
	private int number;
	
	@ApiModelProperty(example="false", value="Se a página atual é a ultima")
	private boolean lastPage;
	
	@ApiModelProperty(example="true", value="Se a página atual é a primeira")
	private boolean firstPage;

}
