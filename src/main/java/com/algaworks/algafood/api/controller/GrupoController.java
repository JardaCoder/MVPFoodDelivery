package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.disassembler.GrupoInputDtoDisassembler;
import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.api.model.input.GrupoInputDto;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {
	
	
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	CadastroGrupoService cadastroGrupo;
	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;
	@Autowired
	private GrupoInputDtoDisassembler grupoInputDtoDisassembler;

	
	@GetMapping
	public List<GrupoDto> listar(){
		return grupoDtoAssembler.gruposToListGrupoDto(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoDto buscar(@PathVariable("grupoId") Long id){
		return grupoDtoAssembler.grupoToGrupoDto(cadastroGrupo.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDto criar(@RequestBody @Valid GrupoInputDto grupoInputDto){
		Grupo grupo = cadastroGrupo.salvar(grupoInputDtoDisassembler.grupoInputDtoToGrupo(grupoInputDto));
		return grupoDtoAssembler.grupoToGrupoDto(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoDto editar(@PathVariable("grupoId") Long id, @RequestBody @Valid GrupoInputDto grupoInputDto){
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);
		
		grupoInputDtoDisassembler.copyToDomainObject(grupoInputDto, grupoAtual);
		
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		return grupoDtoAssembler.grupoToGrupoDto(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("grupoId") Long id){
		cadastroGrupo.excluir(id);
	}
}
