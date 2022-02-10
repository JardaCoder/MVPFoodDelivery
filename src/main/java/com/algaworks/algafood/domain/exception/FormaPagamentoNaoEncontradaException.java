package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	private static final String MSG_FORMA_DE_PAGAMENTO_NAO_EXISTE = "A forma de pagamento de código %d não existe.";

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);

	}

	public FormaPagamentoNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_FORMA_DE_PAGAMENTO_NAO_EXISTE, estadoId));

	}

}
