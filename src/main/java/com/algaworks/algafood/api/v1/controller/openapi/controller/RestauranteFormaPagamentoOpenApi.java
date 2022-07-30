package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoOpenApi {

	@ApiOperation("Lista as formas de pagamento pelo ID do restaurante")
	@ApiResponses({
	    @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	CollectionModel<FormaPagamentoDto> listar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Desassocia uma forma de pagamento a um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento desassociada ao restaurante"),
		@ApiResponse(code = 404, message = "Forma de pagamento ou restaurante não encontrada(o)", response = Problem.class)
	})
	ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)  Long restauranteId,
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@ApiOperation("Associa uma forma de pagamento a um restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento associada ao restaurante"),
		@ApiResponse(code = 404, message = "Forma de pagamento ou restaurante não encontrada(o)", response = Problem.class)
	})
	ResponseEntity<Void> associar(@ApiParam(value = "ID de um restaurante", example = "1", required = true)  Long restauranteId,
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}