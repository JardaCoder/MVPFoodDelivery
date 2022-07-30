package com.algaworks.algafood.api.v1.controller;

import java.util.List;

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

import com.algaworks.algafood.api.v1.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.GrupoInputDtoDisassembler;
import com.algaworks.algafood.api.v1.model.GrupoDto;
import com.algaworks.algafood.api.v1.model.input.GrupoInputDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
	
	
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	CadastroGrupoService cadastroGrupo;
	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;
	@Autowired
	private GrupoInputDtoDisassembler grupoInputDtoDisassembler;

	
	@Override
	@GetMapping
	public CollectionModel<GrupoDto> listar(){
	    List<Grupo> todosGrupos = grupoRepository.findAll();
	    
	    return grupoDtoAssembler.toCollectionModel(todosGrupos);
	}
	
	@Override
	@GetMapping("/{grupoId}")
	public GrupoDto buscar(@PathVariable("grupoId") Long id){
		return grupoDtoAssembler.toModel(cadastroGrupo.buscarOuFalhar(id));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDto criar(@RequestBody @Valid GrupoInputDto grupoInputDto){
		Grupo grupo = cadastroGrupo.salvar(grupoInputDtoDisassembler.grupoInputDtoToGrupo(grupoInputDto));
		return grupoDtoAssembler.toModel(grupo);
	}
	
	@Override
	@PutMapping("/{grupoId}")
	public GrupoDto editar(@PathVariable("grupoId") Long id, @RequestBody @Valid GrupoInputDto grupoInputDto){
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);
		
		grupoInputDtoDisassembler.copyToDomainObject(grupoInputDto, grupoAtual);
		
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		return grupoDtoAssembler.toModel(grupoAtual);
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("grupoId") Long id){
		cadastroGrupo.excluir(id);
	}
}
