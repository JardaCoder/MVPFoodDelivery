package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_CIDADE_NAO_EXISTE = "A cidade de código %d não existe.";

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);

	}

	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format(MSG_CIDADE_NAO_EXISTE, cidadeId));

	}

}
