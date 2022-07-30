package com.algaworks.algafood.api.v2.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaDtoV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputDtoV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

	@ApiOperation("Lista as cidades")
	PagedModel<CozinhaDtoV2> listar(Pageable pageable);

	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDtoV2 buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

	
	@ApiOperation("Cria uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha criada"),
	})
	CozinhaDtoV2 criar(@ApiParam(name = "Corpo", value = "Representação de uma niva cozinha", required = true)CozinhaInputDtoV2 cozinhaInputDto);
	

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha Atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDtoV2 editar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)Long id,
			@ApiParam(name = "Corpo", value = "Representação de uma cozinha com os novos dados", required = true)CozinhaInputDtoV2 cozinhaInputDto);
	

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)Long id);

}