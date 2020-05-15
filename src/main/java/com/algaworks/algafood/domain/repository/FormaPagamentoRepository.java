package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> listar();
	FormaPagamento buscarPorId(Long id);
	FormaPagamento salvarOuAdicionar (FormaPagamento formaPagamento);
	void remover (FormaPagamento formaPagamento);
}
