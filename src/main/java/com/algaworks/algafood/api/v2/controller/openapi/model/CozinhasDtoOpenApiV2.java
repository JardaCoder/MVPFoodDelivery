package com.algaworks.algafood.api.v2.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.CozinhaDto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasDto")
@Getter
@Setter
public class CozinhasDtoOpenApiV2 {
	
	private CozinhasEmbeddedDtoOpenApiV2 _embedded;
	
	private Links _links;
	
	private PageDtoOpenApiV2 page;
	
	@Getter
	@Setter
	@ApiModel("CidadesEmbeddedDto")
	public class CozinhasEmbeddedDtoOpenApiV2{
		private List<CozinhaDto> cozinhas;
	}
}
