package com.algaworks.algafood.di.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import com.algaworks.algafood.di.service.ClienteAtivadoEvent;

@Component
public class EmissaoNotaFiscalService {
	
	@TipoDoNotificador(NivelUrgencia.PRIORIDADEI)
	@Autowired
	private Notificador notificador;
	
	@EventListener
	public void clienteAtivadoListener (ClienteAtivadoEvent event) {
		System.out.println("Emitindo nota fiscal para: " + event.getCliente().getNome());
	
	}

}
