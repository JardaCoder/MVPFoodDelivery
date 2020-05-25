package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhasRepresentation;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
		return ResponseEntity.ok().body(cozinhaRepository.listar());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasRepresentation listarXml(){
		return new CozinhasRepresentation(cozinhaRepository.listar());
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id){
		Cozinha cozinha = cozinhaRepository.buscarPorId(id);
		
		if (cozinha !=  null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
		
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//302
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8090/cozinhas");
//		return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> criar(@RequestBody Cozinha cozinha){
		cozinha = cadastroCozinha.salvar(cozinha);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> editar(@PathVariable("cozinhaId") Long id, @RequestBody Cozinha cozinha){
		Cozinha cozinhaAtual = cozinhaRepository.buscarPorId(id);
		if(cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			cozinhaAtual = cozinhaRepository.salvarOuAdicionar(cozinhaAtual);
			return ResponseEntity.ok().body(cozinhaAtual);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Void> remover(@PathVariable("cozinhaId") Long id){
		try {
			cadastroCozinha.excluir(id);
			return ResponseEntity.noContent().build();
				
			}catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.notFound().build();
				
			}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
