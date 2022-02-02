package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafood.api.model.CozinhasRepresentation;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	
	@GetMapping
	public ResponseEntity<List<Cozinha>> listar(){
		return ResponseEntity.ok().body(cozinhaRepository.findAll());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasRepresentation listarXml(){
		return new CozinhasRepresentation(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable("cozinhaId") Long id){
		return cadastroCozinha.buscarOuFalhar(id);
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> criar(@RequestBody Cozinha cozinha){
		cozinha = cadastroCozinha.salvar(cozinha);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha editar(@PathVariable("cozinhaId") Long id, @RequestBody Cozinha cozinha){
		return cadastroCozinha.editar(cozinha, id);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cozinhaId") Long id){
		cadastroCozinha.excluir(id);
	}
	
//	@DeleteMapping("/{cozinhaId}")
//	public ResponseEntity<Void> remover(@PathVariable("cozinhaId") Long id){
//		try {
//			cadastroCozinha.excluir(id);
//			return ResponseEntity.noContent().build();
//				
//			}
////		catch (EntidadeNaoEncontradaException e) {
////				return ResponseEntity.notFound().build();
////				
////			}
//		catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
	
}
