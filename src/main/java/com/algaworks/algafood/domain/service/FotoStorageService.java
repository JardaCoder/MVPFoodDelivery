package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeArquivo);
	
	FotoRecuperada recuperar(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if(nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String gerarNomeArquivo(String nomeArquivo) {
		return UUID.randomUUID().toString() + "_" + nomeArquivo;
	}
	
	@Getter
	@Builder
	class NovaFoto {
		private String nomeArquivo;
		
		private InputStream inputStream;
		
		private String contentType;
	}
	
	@Getter
	@Builder
	class FotoRecuperada{
		
		private InputStream inputStream;
		
		private String url;
		
		public boolean haveUrl() {
			return url !=  null;
		}
		
		
		public boolean haveInputStream() {
			return inputStream !=  null;
		}
	}
}
