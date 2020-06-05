package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscarPorId(Long id);
	Estado salvarOuAdicionar (Estado estado);
	void remover (Long id);
}
