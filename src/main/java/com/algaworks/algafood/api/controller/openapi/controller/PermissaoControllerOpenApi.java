package com.algaworks.algafood.api.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.model.PermissaoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoDto> listar();
}
