package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.EstadoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@ApiModel("EstadosDto")
public class EstadosDtoOpenApi {

	private EstadoEmbeddedDtoOpenApi _embedded;
	
	private Links _links;
	
	@Getter
	@Setter
	@ApiModel("EstadosEmbeddedDto")
	public class EstadoEmbeddedDtoOpenApi{
		private List<EstadoDto> estados;
	}
}
