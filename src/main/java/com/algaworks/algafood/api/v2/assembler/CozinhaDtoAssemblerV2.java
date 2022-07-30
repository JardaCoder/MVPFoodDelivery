package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.JardaLinksV2;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerV2;
import com.algaworks.algafood.api.v2.model.CozinhaDtoV2;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDtoV2> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinksV2 jardaLinks;
	
	
	public CozinhaDtoAssemblerV2() {
		super(CozinhaControllerV2.class, CozinhaDtoV2.class);
	}
	
	public CozinhaDtoV2 toModel(Cozinha cozinha) { 
		
		CozinhaDtoV2 cozinhaDto = createModelWithId(cozinha.getId(), cozinha);
		
		modelMapper.map(cozinha, cozinhaDto);
		
		cozinhaDto.add(jardaLinks.linkToCozinhas());
		
		return cozinhaDto;
	}
	
	@Override
	public CollectionModel<CozinhaDtoV2> toCollectionModel(Iterable<? extends Cozinha> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToCozinhas());
	}
}
