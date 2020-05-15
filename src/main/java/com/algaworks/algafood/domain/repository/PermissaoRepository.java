package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar();
	Permissao buscarPorId(Long id);
	Permissao salvarOuAdicionar (Permissao permissao);
	void remover (Permissao permissao);
}
