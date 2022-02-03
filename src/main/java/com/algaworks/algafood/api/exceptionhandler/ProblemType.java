package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreesível", "/mensagem-incompreensivel"),
	PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
	DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos"),
	ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio"),
	ERRO_DE_SISTEMA("Erro interno de sistema.", "/erro-interno");
	
	
	private String title;
	
	private String uri;

	private ProblemType(String title, String path) {
		this.title = title;
		this.uri = "https://algafood.com.br" + path;
	}
	
	
}
