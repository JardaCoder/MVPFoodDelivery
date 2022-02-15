package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.flywaydb.core.internal.util.FileCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService{
	
	@Autowired
	StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		
		Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		
		try {
			FileCopyUtils.copy(novaFoto.getInputStream(), 
					Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possivel armazenar o aquivo", e);
		}
	}
	
	@Override
	public void remover(String nomeArquivo) {
		Path arquivoPath = getArquivoPath(nomeArquivo);		
		try {
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel remover o aquivo", e);
		}
	}
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);		
			
			return  FotoRecuperada.builder()
					.inputStream(Files.newInputStream(arquivoPath))
					.build();
			
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o arquivo", e);
		}
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties
				.getLocal()
				.getDiretorio()
				.resolve(Path.of(nomeArquivo));
	}
	
}