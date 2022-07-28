package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.controller.openapi.model.FormasPagamentoDtoOpenApi;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoDto;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "FormasPagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value ="Lista as formas de pagamento", response = FormasPagamentoDtoOpenApi.class)
	ResponseEntity<CollectionModel<FormaPagamentoDto>> listar(ServletWebRequest request);
	

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da forma de pagamento  inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento  não encontrada", response = Problem.class)
	})
	ResponseEntity<FormaPagamentoDto> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id,
			ServletWebRequest request);

	
	@ApiOperation("Cria uma forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Forma de pagamento criada"),
	})
	FormaPagamentoDto criar(
			@ApiParam(name = "Corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoInputDto formaPagamentoInputDto);

	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Forma de pagamento Atualizada"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	FormaPagamentoDto editar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)Long id,
			@ApiParam(name = "Corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoInputDto formaPagamentoInputDto);

	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)Long id);

}