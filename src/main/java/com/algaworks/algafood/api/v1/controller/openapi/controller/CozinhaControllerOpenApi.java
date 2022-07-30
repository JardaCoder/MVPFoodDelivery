package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CozinhaDto;
import com.algaworks.algafood.api.v1.model.input.CozinhaInputDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cidades")
	PagedModel<CozinhaDto> listar(Pageable pageable);

	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDto buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

	
	@ApiOperation("Cria uma cozinha")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha criada"),
	})
	CozinhaDto criar(@ApiParam(name = "Corpo", value = "Representação de uma niva cozinha", required = true)CozinhaInputDto cozinhaInputDto);
	

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cozinha Atualizada"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDto editar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)Long id,
			@ApiParam(name = "Corpo", value = "Representação de uma cozinha com os novos dados", required = true)CozinhaInputDto cozinhaInputDto);
	

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída"),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true)Long id);

}