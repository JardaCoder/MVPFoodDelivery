package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation("Confirma o pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> confirmar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

	@ApiOperation("Cancela o pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido cancelado"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> cancelar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

	@ApiOperation("Confirma entrega do pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido com entrega confirmada"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> confirmarEntrega(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

}