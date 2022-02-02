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

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	CadastroCidadeService cadastroCidade;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable("cidadeId") Long id) {
		return cadastroCidade.buscarOuFalhar(id);
	}

	@PostMapping
	public ResponseEntity<Cidade> criar(@RequestBody Cidade cidade) {
		
		try {
			cidade = cadastroCidade.salvar(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
	}

	@PutMapping("/{cidadeId}")
	public Cidade editar(@PathVariable("cidadeId") Long id, @RequestBody Cidade cidade) {
		return cadastroCidade.editar(cidade, id);
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("cidadeId") Long id) {
		cadastroCidade.excluir(id);

	}
}
