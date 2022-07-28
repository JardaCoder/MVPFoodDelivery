package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.JardaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoControlller;
import com.algaworks.algafood.api.model.FotoProdutoDto;
import com.algaworks.algafood.domain.model.FotoProduto;


@Component
public class FotoProdutoDtoAssembler  
		extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDto>{
	
	public FotoProdutoDtoAssembler() {
	   super(RestauranteProdutoFotoControlller.class, FotoProdutoDto.class);
	}
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	public FotoProdutoDto toModel(FotoProduto fotoProduto) {
		FotoProdutoDto fotoProdutoDto = modelMapper.map(fotoProduto, FotoProdutoDto.class);
        
		fotoProdutoDto.add(jardaLinks.linkToFotoProduto(
				fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));
        
		fotoProdutoDto.add(jardaLinks.linkToProduto(
				fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
        
        return fotoProdutoDto;
	}
	
	public List<FotoProdutoDto> fotoProdutosToListFotoProdutoDto(Collection<FotoProduto> fotoProdutos) {
		return fotoProdutos.stream().map(this::toModel).collect(Collectors.toList());
	}

}
