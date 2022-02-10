package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	private static final String MSG_PERMISSAO_NAO_EXISTE = "A permissão de código %d não existe.";

	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);

	}

	public PermissaoNaoEncontradaException(Long estadoId) {
		this(String.format(MSG_PERMISSAO_NAO_EXISTE, estadoId));

	}

}
