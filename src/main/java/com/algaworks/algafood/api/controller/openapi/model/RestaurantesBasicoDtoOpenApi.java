package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.RestauranteBasicoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantesBasicoDto")
@Data
public class RestaurantesBasicoDtoOpenApi {
	
	private RestaurantesEmbeddedDtoOpenApi _embedded;
	
    private Links _links;
    
    @ApiModel("RestaurantesEmbeddedDto")
    @Data
    public class RestaurantesEmbeddedDtoOpenApi {
        
        private List<RestauranteBasicoDto> restaurantes;
        
    }
}
