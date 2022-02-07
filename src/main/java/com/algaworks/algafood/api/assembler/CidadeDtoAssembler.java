package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDtoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDto cidadeToCidadeDto(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDto.class);
	}
	
	public List<CidadeDto> cidadesToListCidadeDto(List<Cidade> cidades) {
		return cidades.stream().map(this::cidadeToCidadeDto).collect(Collectors.toList());
	}
	

}
