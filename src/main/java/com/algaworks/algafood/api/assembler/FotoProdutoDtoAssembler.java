package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FotoProdutoDto;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDtoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDto fotoProdutoToFotoProdutoDto(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoDto.class);
	}
	
	public List<FotoProdutoDto> fotoProdutosToListFotoProdutoDto(Collection<FotoProduto> fotoProdutos) {
		return fotoProdutos.stream().map(this::fotoProdutoToFotoProdutoDto).collect(Collectors.toList());
	}
	

}
