package com.algaworks.algafood.api.v2.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.CidadeDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("CidadesDto")
public class CidadesDtoOpenApiV2 {

	private CidadeEmbeddedDtoOpenApiV2 _embedded;
	
	private Links _links;
	
	@Getter
	@Setter
	@ApiModel("CidadesEmbeddedDto")
	public class CidadeEmbeddedDtoOpenApiV2{
		private List<CidadeDto> cidades;
	}
}
