package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.v1.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.api.v1.disassembler.EstadoInputDtoDisassembler;
import com.algaworks.algafood.api.v1.model.EstadoDto;
import com.algaworks.algafood.api.v1.model.input.EstadoInputDto;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/v1/estados")
public class EstadoController implements EstadoControllerOpenApi {
	
	
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	CadastroEstadoService cadastroEstado;
	@Autowired
	private EstadoDtoAssembler estadoDtoAssembler;
	@Autowired
	private EstadoInputDtoDisassembler estadoInputDtoDisassembler;

	
	@CheckSecurity.Estados.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<EstadoDto> listar(){
		return estadoDtoAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@CheckSecurity.Estados.PodeConsultar
	@Override
	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable("estadoId") Long id){
		return estadoDtoAssembler.toModel(cadastroEstado.buscarOuFalhar(id));
	}
	
	@CheckSecurity.Estados.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto criar(@RequestBody @Valid EstadoInputDto estadoInputDto){
		Estado estado = cadastroEstado.salvar(estadoInputDtoDisassembler.estadoInputDtoToEstado(estadoInputDto));
		return estadoDtoAssembler.toModel(estado);
	}
	
	@CheckSecurity.Estados.PodeEditar
	@Override
	@PutMapping("/{estadoId}")
	public EstadoDto editar(@PathVariable("estadoId") Long id, @RequestBody @Valid EstadoInputDto estadoInputDto){
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
		
		estadoInputDtoDisassembler.copyToDomainObject(estadoInputDto, estadoAtual);
		
		estadoAtual = cadastroEstado.salvar(estadoAtual);
		
		return estadoDtoAssembler.toModel(estadoAtual);
	}
	
	@CheckSecurity.Estados.PodeEditar
	@Override
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("estadoId") Long id){
		cadastroEstado.excluir(id);
	}
}
