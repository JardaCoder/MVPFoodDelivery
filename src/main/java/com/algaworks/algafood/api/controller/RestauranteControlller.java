package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.JardaFoodException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "restaurantes")
public class RestauranteControlller {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar(){
		return ResponseEntity.ok().body(restauranteRepository.listar());
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id){
		Restaurante restaurante = restauranteRepository.buscarPorId(id);
		
		if (restaurante !=  null) {
			return ResponseEntity.ok(restaurante);
		}
		return ResponseEntity.notFound().build();
		
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//302
//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8090/cozinhas");
//		return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody Restaurante restaurante){
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> editar(@PathVariable("restauranteId") Long id, @RequestBody Restaurante restaurante){
		try {
			restaurante = cadastroRestaurante.editar(restaurante, id);
			return ResponseEntity.ok().body(restaurante);
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch (JardaFoodException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
