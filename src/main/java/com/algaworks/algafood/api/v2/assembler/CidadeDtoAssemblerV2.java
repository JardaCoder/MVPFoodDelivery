package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.JardaLinksV2;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.model.CidadeDtoV2;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDtoV2> {
	
	public CidadeDtoAssemblerV2() {
		super(CidadeControllerV2.class, CidadeDtoV2.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinksV2 jardaLinks;
	
	@Override
	public CidadeDtoV2 toModel(Cidade cidade) {
		
		CidadeDtoV2 cidadeDto = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeDto);
		
		cidadeDto.add(
				jardaLinks.linkToCidades("cidades"));
		
		return cidadeDto;
	}
	
	@Override
	public CollectionModel<CidadeDtoV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToCidades());
	}
	
}
