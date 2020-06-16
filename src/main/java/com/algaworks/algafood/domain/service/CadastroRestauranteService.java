package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() ->  new EntidadeNaoEncontradaException(
				String.format("Cozinha de código %d não existe", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	public void excluir (Long cozinhaId) {
		try {
			restauranteRepository.deleteById(cozinhaId);
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"O restaurante de código %d não existe.", cozinhaId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Restaurante de código %d não pode ser removida por que está em uso.", cozinhaId));
		}
	}
	
	public Restaurante editar (Restaurante restaurante, Long restauranteId) {
		Optional <Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if (restauranteAtual.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante de código %d não exite", restauranteId));
		}
		
		BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
		
		Restaurante restauranteSalvo = salvar(restauranteAtual.get());
		return restauranteSalvo;
		
	}

	public Restaurante editarParcialmente(Restaurante restaurante, Long id) {
		
		return null;
	}
	
}
