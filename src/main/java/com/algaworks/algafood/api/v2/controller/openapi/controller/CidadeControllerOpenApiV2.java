package com.algaworks.algafood.api.v2.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeDto;
import com.algaworks.algafood.api.v1.model.input.CidadeInputDto;
import com.algaworks.algafood.api.v2.model.CidadeDtoV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputDtoV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {

	

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeDtoV2> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeDtoV2 buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

	@ApiOperation("Cria uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada"),
	})
	CidadeDtoV2 criar(@ApiParam(name = "Corpo", value = "Representação de uma nova cidade", required = true) CidadeInputDtoV2 cidadeInputDtp);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade Atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeDtoV2 editar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id,
			@ApiParam(name = "Corpo", value = "Representação de uma cidade com os novos dados", required = true) CidadeInputDtoV2 cidadeInputDto);

	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

}