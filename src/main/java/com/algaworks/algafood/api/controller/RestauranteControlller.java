package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.JardaFoodException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "restaurantes")
public class RestauranteControlller {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar(){
		return ResponseEntity.ok().body(restauranteRepository.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id){
		Optional <Restaurante> restaurante = restauranteRepository.findById(id);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
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
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Void> remover(@PathVariable("cozinhaId") Long id){
		try {
			cadastroRestaurante.excluir(id);
			return ResponseEntity.noContent().build();
				
			}catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.notFound().build();
				
			}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
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
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> editarParcialmente(@PathVariable("restauranteId") Long id, @RequestBody Map<String, Object> campos ){
		try {
			Optional <Restaurante> restauranteAtual = restauranteRepository.findById(id);
			
			if(restauranteAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			merge(campos, restauranteAtual.get());
			cadastroRestaurante.editar(restauranteAtual.get(), id);
			
			return ResponseEntity.ok().body(restauranteAtual.get());
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem =  objectMapper.convertValue(camposOrigem, Restaurante.class);
		System.out.println(restauranteOrigem);
		
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field  = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor =  ReflectionUtils.getField(field, restauranteOrigem);
			System.out.println(nomePropriedade);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
