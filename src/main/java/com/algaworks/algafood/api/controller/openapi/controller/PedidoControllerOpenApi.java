package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoDto;
import com.algaworks.algafood.api.model.PedidoResumoDto;
import com.algaworks.algafood.api.model.input.PedidoInputDto;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation("Lista os pedidos")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome das propriedades para filtrar na resposta, "
				+ "separados por virgula", name="campos", paramType = "query", type = "string")
	})
	Page<PedidoResumoDto> pesquisar(PedidoFilter pedidoFilter, Pageable pageable);

	
	
	@ApiOperation("Busca um pedido pelo código")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome das propriedades para filtrar na resposta, "
				+ "separados por virgula", name="campos", paramType = "query", type = "string")
	})
	@ApiResponses({
		@ApiResponse(code = 400, message = "Código do pedido inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	PedidoDto buscar(@ApiParam(value = "Código de um pedido",
	example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigoPedido);

	
	@ApiOperation("Cria um pedido")
	PedidoDto criar(@ApiParam(name = "Corpo", value = "Representação de um novo pedido") PedidoInputDto pedidoInputDto);

}