package com.algaworks.algafood.api.controller.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.controller.openapi.model.RestauranteBasicoDtoOpenApi;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.api.model.input.RestauranteInputDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	
	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoDtoOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de restaurantes ", allowableValues = "apenas-nome",
				name="projecao", paramType = "query", type = "string")
	})
	List<RestauranteDto> listar();

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	List<RestauranteDto> listarApenasNome();

	@ApiOperation(value = "Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDto buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long id);

	
	@ApiOperation("Cria um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante criado"),
	})
	RestauranteDto criar(
			@ApiParam(name = "Corpo", value = "Representação de um novo restaurante", required = true) RestauranteInputDto restauranteInputDto);
	
	@ApiOperation("Exclui um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante excluído"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long id);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante Atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDto editar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(name = "Corpo", value = "Representação restaurante com os novos dados", required = true) RestauranteInputDto restauranteInputDto);

	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante ativado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId);

	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante inativado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId);

	
	@ApiOperation("Ativa vários restaurantes por IDs")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes ativados"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void ativarMultiplos(@ApiParam(name="Corpo", value = "Lista de IDs de restaurantes", example = "[1, 2, 3]", required = true)List<Long> restauranteIds);

	
	@ApiOperation("Inativa vários restaurantes por IDs")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes inativados"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void inativarMultiplos(@ApiParam(name="Corpo", value = "Lista de IDs de restaurantes", example = "[1, 2, 3]", required = true)List<Long> restauranteIds);

	
	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante fechado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId);

	
	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante aberto"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId);

}