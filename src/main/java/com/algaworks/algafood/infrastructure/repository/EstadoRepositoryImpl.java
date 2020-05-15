package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Estado> listar(){
		return manager.createQuery("from Cozinha", Estado.class).getResultList();
	}
	
	@Override
	@Transactional
	public Estado salvarOuAdicionar (Estado estado) {
		return manager.merge(estado);
		
	}
	
	@Override
	public Estado buscarPorId (Long id) {
		return manager.find(Estado.class, id);
	}
	
	@Override
	@Transactional
	public void remover (Estado estado) {
		estado = buscarPorId(estado.getId());
		manager.remove(estado);
	}
}
