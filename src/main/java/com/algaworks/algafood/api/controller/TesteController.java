package com.algaworks.algafood.api.controller;


import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping(value = "/testes")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("cozinhas/por-nome")
	public ResponseEntity<List<Cozinha>> listar(@RequestParam("nome") String nome){
		return ResponseEntity.ok().body(cozinhaRepository.findCozinhasByNomeContaining(nome));
	}
	
	@GetMapping("cozinhas/por-nome-exist")
	public ResponseEntity<Boolean> cozinhaExiste(@RequestParam("nome") String nome){
		return ResponseEntity.ok().body(cozinhaRepository.existsByNome(nome));
	}
	
	@GetMapping("restaurantes/primeiro-por-nome")
	public ResponseEntity<List<Restaurante>> listarPrimeiro(@RequestParam("nome") String nome){
		return ResponseEntity.ok().body(restauranteRepository.findTop2RestauranteByNomeContaining(nome));
	}
	
	@GetMapping("cozinhas/unica-por-nome")
	public ResponseEntity<Optional<Cozinha>> buscarUma(@RequestParam("nome") String nome){
		return ResponseEntity.ok().body(cozinhaRepository.findByNome(nome));
	}
	
	@GetMapping("restaurantes/por-taxa-frete")
	public ResponseEntity<List<Restaurante>> listarRestaurantes(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return ResponseEntity.ok().body(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}
	
	@GetMapping("restaurantes/por-taxa-frete-nome")
	public ResponseEntity<List<Restaurante>> listarRestaurantes(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return ResponseEntity.ok().body(restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}
	
	@GetMapping("restaurantes/por-nome-e-cozinha")
	public ResponseEntity<List<Restaurante>> listarRestaurantesPorNomeECozinha(String nome, Long cozinhaId){
		return ResponseEntity.ok().body(restauranteRepository.buscarPorNome(nome, cozinhaId));
	}
	
	@GetMapping("restaurantes/count-por-cozinha")
	public ResponseEntity<Integer> countByCozinha(Long cozinhaId){
		return ResponseEntity.ok().body(restauranteRepository.countByCozinhaId(cozinhaId));
	}
	
	
	@GetMapping("restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome){
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("restaurantes/primeiro")
	public Optional<Restaurante> buscarPrimeiroRestaurante(String nome){
		return restauranteRepository.buscarPrimeiro();
	}
	

	@GetMapping("cozinhas/primeira")
	public Optional<Cozinha> buscarPrimeiraCozinha(String nome){
		return cozinhaRepository.buscarPrimeiro();
	}

}
