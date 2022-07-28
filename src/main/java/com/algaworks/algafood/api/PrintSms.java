package com.algaworks.algafood.api;

import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.enums.Notificador;

@Service
public class PrintSms implements Strategy{

	@Override
	public String printStrategy() {
		return "SMS strategy";
		
	}

	@Override
	public Notificador getStrategyName() {
		return Notificador.SMS;
	}

}
