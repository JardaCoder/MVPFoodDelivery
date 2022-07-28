package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.enums.Notificador;

public interface Strategy {

	public String printStrategy();
	
	Notificador getStrategyName();
}
