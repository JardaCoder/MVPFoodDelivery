package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CidadeDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("CidadesDto")
public class CidadesDtoOpenApi {

	private CidadeEmbeddedDtoOpenApi _embedded;
	
	private Links _links;
	
	@Getter
	@Setter
	@ApiModel("CidadesEmbeddedDto")
	public class CidadeEmbeddedDtoOpenApi{
		private List<CidadeDto> cidades;
	}
}
