package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoControlller;
import com.algaworks.algafood.api.v1.model.ProdutoDto;
import com.algaworks.algafood.core.security.SecurityUtils;
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
	
	@Autowired
	private SecurityUtils securityUtils;
	
	public ProdutoDto toModel(Produto produto) {
		ProdutoDto produtoDto = createModelWithId(
		        produto.getId(), produto, produto.getRestaurante().getId());
		
		modelMapper.map(produto, produtoDto);
		
		if(securityUtils.podeConsultarRestaurantes()) {
			produtoDto.add(jardaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
			
			produtoDto.add(jardaLinks.linkToFotoProduto(
					produto.getRestaurante().getId(), produto.getId(), "foto"));
		}
		
		return produtoDto;
	}

}
