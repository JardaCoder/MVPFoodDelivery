package com.algaworks.algafood.api.v1.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.PedidoResumoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosResumoDto")
@Getter
@Setter
public class PedidoResumoDtoOpenApi {
	
    private PedidosResumoEmbeddedDtoOpenApi _embedded;
    private Links _links;
    private PageDtoOpenApi page;
    
    @ApiModel("PedidosResumoEmbeddedDto")
    @Data
    public class PedidosResumoEmbeddedDtoOpenApi {
        
        private List<PedidoResumoDto> pedidos;
        
    } 
}
