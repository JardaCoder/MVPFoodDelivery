package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.GrupoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GruposDto")
@Data
public class GruposDtoOpenApi {

	private GruposEmbeddedDtoOpenApi _embedded;
	private Links _links;

	@ApiModel("GruposEmbeddedDto")
	@Data
	public class GruposEmbeddedDtoOpenApi {

		private List<GrupoDto> grupos;

	}

}
