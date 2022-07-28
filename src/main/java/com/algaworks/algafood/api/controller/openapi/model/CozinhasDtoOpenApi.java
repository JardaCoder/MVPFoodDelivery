package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CozinhaDto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasDto")
@Getter
@Setter
public class CozinhasDtoOpenApi {
	
	private CozinhasEmbeddedDtoOpenApi _embedded;
	
	private Links _links;
	
	private PageDtoOpenApi page;
	
	@Getter
	@Setter
	@ApiModel("CidadesEmbeddedDto")
	public class CozinhasEmbeddedDtoOpenApi{
		private List<CozinhaDto> cozinhas;
	}
}
