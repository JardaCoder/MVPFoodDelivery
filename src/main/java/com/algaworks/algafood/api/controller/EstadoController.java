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

import com.algaworks.algafood.api.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.disassembler.EstadoInputDtoDisassembler;
import com.algaworks.algafood.api.model.EstadoDto;
import com.algaworks.algafood.api.model.input.EstadoInputDto;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
	
	
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	CadastroEstadoService cadastroEstado;
	@Autowired
	private EstadoDtoAssembler estadoDtoAssembler;
	@Autowired
	private EstadoInputDtoDisassembler estadoInputDtoDisassembler;

	
	@GetMapping
	public List<EstadoDto> listar(){
		return estadoDtoAssembler.estadosToListEstadoDto(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable("estadoId") Long id){
		return estadoDtoAssembler.estadoToEstadoDto(cadastroEstado.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto criar(@RequestBody @Valid EstadoInputDto estadoInputDto){
		Estado estado = cadastroEstado.salvar(estadoInputDtoDisassembler.estadoInputDtoToEstado(estadoInputDto));
		return estadoDtoAssembler.estadoToEstadoDto(estado);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDto editar(@PathVariable("estadoId") Long id, @RequestBody @Valid EstadoInputDto estadoInputDto){
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
		
		estadoInputDtoDisassembler.copyToDomainObject(estadoInputDto, estadoAtual);
		
		estadoAtual = cadastroEstado.salvar(estadoAtual);
		
		return estadoDtoAssembler.estadoToEstadoDto(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("estadoId") Long id){
		cadastroEstado.excluir(id);
	}
}
