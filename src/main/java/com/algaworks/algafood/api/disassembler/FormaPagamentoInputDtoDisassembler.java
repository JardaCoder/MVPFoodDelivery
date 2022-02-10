package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoInputDto;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamento formaPagamentoInputDtoToFormaPagamento(FormaPagamentoInputDto formaPagamentoInputDto) {
		return modelMapper.map(formaPagamentoInputDto, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInputDto formaPagamentoInputDto, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInputDto, formaPagamento);
	}
}
