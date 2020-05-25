package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository  {
	
	List<Restaurante> listar();
	Restaurante buscarPorId(Long id);
	Restaurante salvarOuAdicionar (Restaurante restaurante);
	void remover (Long id);
	

}
