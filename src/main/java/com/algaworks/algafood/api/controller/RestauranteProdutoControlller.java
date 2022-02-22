package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDtoAssembler;
import com.algaworks.algafood.api.controller.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.api.disassembler.ProdutoInputDtoDisassembler;
import com.algaworks.algafood.api.model.ProdutoDto;
import com.algaworks.algafood.api.model.input.ProdutoInputDto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoControlller implements RestauranteProdutoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private CadastroProdutoService cadastroProduto;
	@Autowired
	private ProdutoDtoAssembler produtoDtoAssembler;
	@Autowired
	private ProdutoInputDtoDisassembler produtoInputDtoDisassembler;
	@Autowired 
	private ProdutoRepository produtoRepository;
	
	@Override
	@GetMapping
	public List<ProdutoDto> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		List<Produto> produtos = null;
		
		if(incluirInativos) {
			produtos = restaurante.getProdutos();
		}else {			
			produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		
		return produtoDtoAssembler.produtosToListProdutoDto(produtos);
	}
	
	@Override
	@GetMapping("/{produtoId}")
	public ProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		
		return produtoDtoAssembler.produtoToProdutoDto(produto);
	}
	
	@Override
	@PostMapping
	public ProdutoDto criar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDto produtoInput){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDtoDisassembler.produtoInputDtoToProduto(produtoInput);
		produto.setRestaurante(restaurante);
		
		return produtoDtoAssembler.produtoToProdutoDto(cadastroProduto.salvar(produto));
	}
	
	
	@Override
	@PutMapping("/{produtoId}")
	public ProdutoDto editar(@PathVariable Long produtoId, @PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDto produtoInput){
			Produto produtoAtual = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
			
			produtoInputDtoDisassembler.copyToDomainObject(produtoInput, produtoAtual);
			
			produtoAtual = cadastroProduto.salvar(produtoAtual);
			
			return produtoDtoAssembler.produtoToProdutoDto(produtoAtual);
	}
	
	
}



