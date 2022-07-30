package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.CozinhaDto;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDtoAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDto> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	
	public CozinhaDtoAssembler() {
		super(CozinhaController.class, CozinhaDto.class);
	}
	
	public CozinhaDto toModel(Cozinha cozinha) { 
		
		CozinhaDto cozinhaDto = createModelWithId(cozinha.getId(), cozinha);
		
		modelMapper.map(cozinha, cozinhaDto);
		
		cozinhaDto.add(jardaLinks.linkToCozinhas());
		
		return cozinhaDto;
	}
	
	@Override
	public CollectionModel<CozinhaDto> toCollectionModel(Iterable<? extends Cozinha> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToCozinhas());
	}
}
