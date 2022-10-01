package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PermissaoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.api.v1.model.PermissaoDto;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoDtoAssembler permissaoDtoAssembler;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<PermissaoDto> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoDtoAssembler.toCollectionModel(todasPermissoes);
	}
}
