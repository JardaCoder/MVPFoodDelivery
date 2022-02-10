package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	private static final String MSG_PRODUTO_NAO_EXISTE = "O produto de código %d não existe.";

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);

	}

	public ProdutoNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_PRODUTO_NAO_EXISTE, estadoId));

	}

}
