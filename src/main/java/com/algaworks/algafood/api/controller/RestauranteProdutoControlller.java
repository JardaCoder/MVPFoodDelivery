package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDtoAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoInputDtoDisassembler;
import com.algaworks.algafood.api.model.ProdutoDto;
import com.algaworks.algafood.api.model.input.ProdutoInputDto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoControlller {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private CadastroProdutoService cadastroProduto;
	@Autowired
	private ProdutoDtoAssembler produtoDtoAssembler;
	@Autowired
	private ProdutoInputDtoDisassembler produtoInputDtoDisassembler;
	
	@GetMapping
	public List<ProdutoDto> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		return produtoDtoAssembler.produtosToListProdutoDto(restaurante.getProdutos());
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		
		return produtoDtoAssembler.produtoToProdutoDto(produto);
	}
	
	@PostMapping
	public ProdutoDto criar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDto produtoInput){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDtoDisassembler.produtoInputDtoToProduto(produtoInput);
		produto.setRestaurante(restaurante);
		
		return produtoDtoAssembler.produtoToProdutoDto(cadastroProduto.salvar(produto));
	}
	
	
	@PutMapping("/{produtoId}")
	public ProdutoDto editar(@PathVariable Long produtoId, @PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDto produtoInput){
			Produto produtoAtual = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
			
			produtoInputDtoDisassembler.copyToDomainObject(produtoInput, produtoAtual);
			
			produtoAtual = cadastroProduto.salvar(produtoAtual);
			
			return produtoDtoAssembler.produtoToProdutoDto(produtoAtual);
	}
	
	
}



