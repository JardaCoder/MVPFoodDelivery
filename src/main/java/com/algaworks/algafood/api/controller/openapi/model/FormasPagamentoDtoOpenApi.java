package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.FormaPagamentoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("FormasPagamentoDto")
@Data
public class FormasPagamentoDtoOpenApi {

    private FormasPagamentoEmbeddedDtoOpenApi _embedded;
    
    private Links _links;
    
    @ApiModel("FormasPagamentoEmbeddedDto")
    @Data
    public class FormasPagamentoEmbeddedDtoOpenApi {
        
        private List<FormaPagamentoDto> formasPagamento;
        
    } 
}
