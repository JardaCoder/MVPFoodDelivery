package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDtoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDto> {
	
	 public FormaPagamentoDtoAssembler() {
		 super(FormaPagamentoController.class, FormaPagamentoDto.class);
	 }

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JardaLinks jardaLinks;
	@Autowired
	private SecurityUtils securityUtils;
	
	public FormaPagamentoDto toModel(FormaPagamento formaPagamento) {
		
		FormaPagamentoDto formaPagamentoDto = 
				createModelWithId(formaPagamento.getId(), formaPagamento);
		 
		modelMapper.map(formaPagamento, formaPagamentoDto);
		
		if(securityUtils.podeConsultarFormasPagamento()) {
			formaPagamentoDto.add(jardaLinks.linkToFormasPagamento("formasPagamento")); 
		}
		
		return formaPagamentoDto;
	}
	
	@Override
	public CollectionModel<FormaPagamentoDto> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
	    CollectionModel<FormaPagamentoDto> collectionModel = super.toCollectionModel(entities);
	    
	    if (securityUtils.podeConsultarFormasPagamento()) {
	        collectionModel.add(jardaLinks.linkToFormasPagamento());
	    }
	        
	    return collectionModel;
	}
	

}
