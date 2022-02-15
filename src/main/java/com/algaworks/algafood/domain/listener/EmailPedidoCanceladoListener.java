package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;;

@Component
public class EmailPedidoCanceladoListener {
	
	
	@Autowired
	private EnvioEmailService envioEmail;


	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		
		var pedido = event.getPedido();
		
		
		var mensagem = Mensagem.builder()
			.assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
			.corpo("pedido-cancelado.html")
			.destinatario(pedido.getCliente().getEmail())
			.variavel("pedido", pedido)
			.build();
		
		envioEmail.enviar(mensagem);
	}
}
