package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable("estadoId") Long id){
		return cadastroEstado.buscarOuFalhar(id);
	}
	
	@PostMapping
	public ResponseEntity<Estado> criar(@RequestBody Estado estado){
		estado = cadastroEstado.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estado);
	}
	
	@PutMapping("/{estadoId}")
	public Estado editar(@PathVariable("estadoId") Long id, @RequestBody Estado estado){
		return estado = cadastroEstado.editar(estado, id);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("estadoId") Long id){
		cadastroEstado.excluir(id);
	}
}
