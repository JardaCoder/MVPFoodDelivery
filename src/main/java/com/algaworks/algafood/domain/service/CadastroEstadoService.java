package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir (Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(
					"A estado de código %d não existe.", estadoId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Estado de código %d não pode ser removida por que está em uso.", estadoId));
		}
		
	}
	
	public Estado editar (Estado estado, Long estadoId) {
		Optional <Estado> estadoAtual = estadoRepository.findById(estadoId);
		
		if (estadoAtual.isEmpty()) {
			throw new EntidadeNaoEncontradaException(
					String.format("Estado de código %d não exite", estadoId));
		}
		
		BeanUtils.copyProperties(estado, estadoAtual.get(), "id");
		Estado estadoSalvo = salvar(estadoAtual.get());
		
		return estadoSalvo;
	}
}
