package com.algaworks.algafood.api.controller.openapi.controller;


import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioDto;
import com.algaworks.algafood.api.model.input.AlterarSenhaInputDto;
import com.algaworks.algafood.api.model.input.UsuarioInputComSenhaDto;
import com.algaworks.algafood.api.model.input.UsuarioInputDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	List<UsuarioDto> listar();

   @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	UsuarioDto buscar( @ApiParam(value = "ID do usuário", example = "1", required = true) Long id);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário cadastrado"), 
	})
	UsuarioDto criar(@ApiParam(name = "Corpo", value = "Representação de um novo usuário", required = true)UsuarioInputComSenhaDto usuarioInputDto);

    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário atualizado"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	UsuarioDto editar(
			@ApiParam(value = "ID do usuário", example = "1", required = true)Long id,
			@ApiParam(name = "Corpo", value = "Representação de um novo usuário", required = true)UsuarioInputDto usuarioInputDto);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	void alterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true)Long id, 
			 @ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true)AlterarSenhaInputDto alterarSenhaInput);

	@ApiOperation("Exclui um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário excluído"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID do usuário", example = "1", required = true)Long id);

}