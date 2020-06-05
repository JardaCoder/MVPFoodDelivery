package com.algaworks.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cidade salvar(Cidade cidade) {
		return cidadeRepository.salvarOuAdicionar(cidade);
	}
	
	public void excluir (Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"A cidade de código %d não existe.", cidadeId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Cidade de código %d não pode ser removida por que está em uso.", cidadeId));
		}
		
	}
	
	public Cidade editar (Cidade cidade, Long cidadeId) {
		Cidade cidadeAtual = cidadeRepository.buscarPorId(cidadeId);
		
		if (cidadeAtual == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade de código %d não exite", cidadeId));
		}
		
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		cidadeAtual = salvar(cidadeAtual);
		
		return cidadeAtual;
	}
}
