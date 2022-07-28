package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.PermissaoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("PermissoesDto")
@Data
public class PermissoesDtoOpenApi {

	private PermissoesEmbeddedDtoOpenApi _embedded;
	private Links _links;

	@ApiModel("PermissoesEmbeddedDto")
	@Data
	public class PermissoesEmbeddedDtoOpenApi {

		private List<PermissaoDto> permissoes;

	}
}
