package com.algaworks.algafood.api.v1.controller.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Links")
public class LinksDtoOpenApi {
	
	private LinkDto rel;


	@Getter
	@Setter
	@ApiModel("Link")
	private class LinkDto{
		
		private String href;
		private boolean templated;
	}
}
