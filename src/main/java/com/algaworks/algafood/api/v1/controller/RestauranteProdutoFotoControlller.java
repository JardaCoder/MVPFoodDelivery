package com.algaworks.algafood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.util.MediaTypeUtils;
import com.algaworks.algafood.api.v1.assembler.FotoProdutoDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.api.v1.model.FotoProdutoDto;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInputDto;
import com.algaworks.algafood.core.security.annotations.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;


@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoControlller implements RestauranteProdutoFotoControllerOpenApi {

	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	@Autowired
	private CadastroProdutoService cadasProdutoService;
	@Autowired
	private FotoProdutoDtoAssembler fotoProdutoDtoAssembler;
	@Autowired
	private FotoStorageService fotoStorage;

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDto atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInputDto fotoProduto, @RequestPart(required = true) MultipartFile arquivo
			) throws IOException {
		
		 arquivo = fotoProduto.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		
		foto.setProduto(cadasProdutoService.buscarOuFalhar(produtoId, restauranteId));
		foto.setDescricao(fotoProduto.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
		
		return fotoProdutoDtoAssembler.toModel(fotoSalva);
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDto buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		
		var foto = catalogoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId);
		
		return fotoProdutoDtoAssembler.toModel(foto);
	}
	
	@Override
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws IOException, HttpMediaTypeNotAcceptableException {
		
		try {
			var foto = catalogoFotoProdutoService.buscarOuFalhar(produtoId, restauranteId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(foto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

			MediaTypeUtils.verificarCompatibilidadesMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(foto.getNomeArquivo());
			
			if(fotoRecuperada.haveUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			}else {
				return ResponseEntity
						.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
			
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}	
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		catalogoFotoProdutoService.excluir(produtoId, restauranteId);
	}
}