package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.ProdutoDto;
import com.algaworks.algafood.api.model.input.ProdutoInputDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	@ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	CollectionModel<ProdutoDto> listar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId,
			@ApiParam(value = "Se deve incluir inativos na listagem", example = "false", defaultValue = "false", required = true) Boolean incluirInativos);
	

	@ApiOperation("Busca o produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do produto ou do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Produto do restaurante não encontrado", response = Problem.class)
	})
	ProdutoDto buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId, 
			@ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId);

	@ApiOperation("Cria um produto para um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Produto criado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ProdutoDto criar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId,
			@ApiParam(name = "Corpo", value = "Representação de um novo produto", required = true)ProdutoInputDto produtoInput);

	@ApiOperation("Atualiza o produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Produto atualizado"),
		@ApiResponse(code = 404, message = "Produto do restaurante não encontrado", response = Problem.class)
	})
	ProdutoDto editar(@ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId, 
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId,
			@ApiParam(name = "Corpo", value = "Representação de um produto com os novos dados", required = true)ProdutoInputDto produtoInput);

}