package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
	
	
	@Autowired
	PermissaoRepository permissaoRepository;
	@Autowired
	CadastroGrupoService cadastroGrupo;
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;

	
	@GetMapping
	public List<GrupoDto> listar(@PathVariable Long usuarioId){
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		return grupoDtoAssembler.gruposToListGrupoDto(usuario.getGrupos());
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
		cadastroUsuario.desassociarGrupo(usuarioId, grupoId);
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
		cadastroUsuario.associarGrupo(usuarioId, grupoId);
	}
	
}
