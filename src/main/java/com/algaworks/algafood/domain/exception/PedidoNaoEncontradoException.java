package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	
	private static final String MSG_PEDIDO_NAO_EXISTE = "O pedido de código %s não existe.";

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format(MSG_PEDIDO_NAO_EXISTE, codigoPedido));

	}
}
