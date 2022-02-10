package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FormaPagamentoDto;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDtoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDto formaPagamentoToFormaPagamentoDto(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDto.class);
	}
	
	public List<FormaPagamentoDto>	formasPagamentoToListFormaPagamentoDto(Collection<FormaPagamento> formasPagamento) {
		return formasPagamento.stream().map(this::formaPagamentoToFormaPagamentoDto).collect(Collectors.toList());
	}
	

}
