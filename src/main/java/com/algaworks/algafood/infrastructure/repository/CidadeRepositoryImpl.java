package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> listar(){
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}
	
	@Override
	@Transactional
	public Cidade salvarOuAdicionar (Cidade cidade) {
		return manager.merge(cidade);
		
	}
	
	@Override
	public Cidade buscarPorId (Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@Override
	@Transactional
	public void remover (Long id) {
		Cidade cidade = buscarPorId(id);
		
		if(cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cidade);
	}
}
