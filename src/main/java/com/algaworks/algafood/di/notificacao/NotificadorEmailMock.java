package com.algaworks.algafood.di.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

//@Profile("dev")
@TipoDoNotificador(NivelUrgencia.PRIORIDADEII)
@Component
public class NotificadorEmailMock implements Notificador {
	
//	public NotificadorEmailMock() {
//		System.out.printf("Notificador fake");
//	}
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("MOCK: Notificacao seria enviada para %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
