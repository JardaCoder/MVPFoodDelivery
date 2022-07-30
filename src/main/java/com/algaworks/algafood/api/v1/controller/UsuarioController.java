package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.UsuarioInputDtoDisassembler;
import com.algaworks.algafood.api.v1.model.UsuarioDto;
import com.algaworks.algafood.api.v1.model.input.AlterarSenhaInputDto;
import com.algaworks.algafood.api.v1.model.input.UsuarioInputComSenhaDto;
import com.algaworks.algafood.api.v1.model.input.UsuarioInputDto;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/v1/usuarios",  produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {
	
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	@Autowired
	private UsuarioDtoAssembler usuarioDtoAssembler;
	@Autowired
	private UsuarioInputDtoDisassembler usuarioInputDtoDisassembler;

	
	@Override
	@GetMapping
	public CollectionModel<UsuarioDto> listar(){
		return usuarioDtoAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@Override
	@GetMapping("/{usuarioId}")
	public UsuarioDto buscar(@PathVariable("usuarioId") Long id){
		return usuarioDtoAssembler.toModel(cadastroUsuario.buscarOuFalhar(id));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto criar(@RequestBody @Valid UsuarioInputComSenhaDto usuarioInputDto){
		Usuario usuario = cadastroUsuario.salvar(usuarioInputDtoDisassembler.usuarioInputDtoToUsuario(usuarioInputDto));
		return usuarioDtoAssembler.toModel(usuario);
	}
	
	@Override
	@PutMapping("/{usuarioId}")
	public UsuarioDto editar(@PathVariable("usuarioId") Long id, @RequestBody @Valid UsuarioInputDto usuarioInputDto){
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(id);
		
		usuarioInputDtoDisassembler.copyToDomainObject(usuarioInputDto, usuarioAtual);
		
		usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
		
		return usuarioDtoAssembler.toModel(usuarioAtual);
	}
	
	@Override
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable("usuarioId") Long id, @RequestBody @Valid AlterarSenhaInputDto alterarSenhaInput){
		cadastroUsuario.alterarSenha(id, alterarSenhaInput.getNovaSenha(), alterarSenhaInput.getSenhaAtual());
	}
	
	
	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("usuarioId") Long id){
		cadastroUsuario.excluir(id);
	}
}
