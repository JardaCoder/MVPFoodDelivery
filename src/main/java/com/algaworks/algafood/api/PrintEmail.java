package com.algaworks.algafood.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.enums.Notificador;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@Service
public class PrintEmail implements Strategy{
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Override
	public String printStrategy() {
		return cadastroRestauranteService.buscarOuFalhar(1L).getNome();
		
	}
	
	@Override
	public Notificador getStrategyName() {
		return Notificador.EMAIL;
	}

}
