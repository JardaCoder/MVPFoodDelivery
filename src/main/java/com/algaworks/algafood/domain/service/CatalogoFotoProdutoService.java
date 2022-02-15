package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {
	
	private static final String MSG_FOTO_EM_USO = "A foto do produto de código %d não pode ser excluida por que está em uso";
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private FotoStorageService fotoStorageService;

	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream inputStream) {
		
		var restauranteId = foto.getRestauranteId();
		var produtoId = foto.getProduto().getId();
		var nomeArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = 
				produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			produtoRepository.delete(fotoExistente.get());
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
		}

		foto.setNomeArquivo(nomeArquivo);
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		var novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(inputStream)
				.contentType(foto.getContentType())
				.build();
		
		
		fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
		
		
		return foto;
	}
	
	public FotoProduto buscarOuFalhar(Long produtoId, Long restauranteId) {
		return produtoRepository.findFotoById(produtoId, restauranteId)
				.orElseThrow(() -> new FotoNaoEncontradaException(produtoId, restauranteId));
	}

	public void excluir(Long produtoId, Long restauranteId) {
		try {
			
			var foto = buscarOuFalhar(produtoId, restauranteId);
			
			produtoRepository.delete(foto);
			produtoRepository.flush();
			
			fotoStorageService.remover(foto.getNomeArquivo());
			
		}catch (EmptyResultDataAccessException e) {
			throw new FotoNaoEncontradaException(produtoId, restauranteId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					MSG_FOTO_EM_USO, produtoId));
		}
		
	}
}
