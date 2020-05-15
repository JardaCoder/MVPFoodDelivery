package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<FormaPagamento> listar(){
		return manager.createQuery("from Cozinha", FormaPagamento.class).getResultList();
	}
	
	@Override
	@Transactional
	public FormaPagamento salvarOuAdicionar (FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
		
	}
	
	@Override
	public FormaPagamento buscarPorId (Long id) {
		return manager.find(FormaPagamento.class, id);
	}
	
	@Override
	@Transactional
	public void remover (FormaPagamento formaPagamento) {
		formaPagamento = buscarPorId(formaPagamento.getId());
		manager.remove(formaPagamento);
	}
}
