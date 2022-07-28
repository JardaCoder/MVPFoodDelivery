package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.JardaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoControlller;
import com.algaworks.algafood.api.model.ProdutoDto;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDtoAssembler
		extends RepresentationModelAssemblerSupport<Produto, ProdutoDto>{
	
    public ProdutoDtoAssembler() {
        super(RestauranteProdutoControlller.class, ProdutoDto.class);
    }
    
    
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	public ProdutoDto toModel(Produto produto) {
		ProdutoDto produtoDto = createModelWithId(
		        produto.getId(), produto, produto.getRestaurante().getId());
		
		modelMapper.map(produto, produtoDto);
		
		produtoDto.add(jardaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
		
		produtoDto.add(jardaLinks.linkToFotoProduto(
	            produto.getRestaurante().getId(), produto.getId(), "foto"));
		
		return produtoDto;
	}

}
