package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Permissao> listar(){
		return manager.createQuery("from Cozinha", Permissao.class).getResultList();
	}
	
	@Override
	@Transactional
	public Permissao salvarOuAdicionar (Permissao permissao) {
		return manager.merge(permissao);
		
	}
	
	@Override
	public Permissao buscarPorId (Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Override
	@Transactional
	public void remover (Permissao permissao) {
		permissao = buscarPorId(permissao.getId());
		manager.remove(permissao);
	}
}
