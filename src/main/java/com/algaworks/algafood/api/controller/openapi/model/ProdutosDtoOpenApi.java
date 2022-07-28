package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.ProdutoDto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProdutosDto")
@Data
public class ProdutosDtoOpenApi {

    private ProdutosEmbeddedDtoOpenApi _embedded;
    private Links _links;
    
    @ApiModel("ProdutosEmbeddedDto")
    @Data
    public class ProdutosEmbeddedDtoOpenApi {
        
        private List<ProdutoDto> produtos;
        
    } 
}
