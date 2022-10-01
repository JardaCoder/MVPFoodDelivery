package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.api.v1.model.GrupoDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
	
	
	@Autowired
	PermissaoRepository permissaoRepository;
	@Autowired
	CadastroGrupoService cadastroGrupo;
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;
	@Autowired
	private JardaLinks jardaLinks;    
	@Autowired
	private SecurityUtils securityUtils;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<GrupoDto> listar(@PathVariable Long usuarioId){
	    Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	    CollectionModel<GrupoDto> grupos = grupoDtoAssembler.toCollectionModel(usuario.getGrupos())
	            .removeLinks();
	    
	    if (securityUtils.podeEditarUsuariosGruposPermissoes()) {
	    	grupos.add(jardaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
	        
		    grupos.getContent().forEach(grupoModel -> {
		        grupoModel.add(jardaLinks.linkToUsuarioGrupoDesassociacao(
		                usuarioId, grupoModel.getId(), "desassociar"));
		    });
	    }

	    return grupos;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
	
}
