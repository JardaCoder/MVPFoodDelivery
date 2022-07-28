package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários de um restaurante")
	@ApiResponses({
        @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
	CollectionModel<UsuarioDto> listar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId);

	@ApiOperation("Desassocia um usuario a um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuario desassociado ao restaurante"),
		@ApiResponse(code = 404, message = "Usuário ou restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId,
			@ApiParam(value = "ID de um usuário", example = "1", required = true)Long usuarioId);

	@ApiOperation("Associa um usuario a um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário associado ao restaurante"),
		@ApiResponse(code = 404, message = "Usuário ou restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> associar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)Long restauranteId,
			@ApiParam(value = "ID de um usuário", example = "1", required = true)Long usuarioId);

}