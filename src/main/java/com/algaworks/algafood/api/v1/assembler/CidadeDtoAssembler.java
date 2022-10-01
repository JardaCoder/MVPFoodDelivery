package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.model.CidadeDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDto> {
	
	public CidadeDtoAssembler() {
		super(CidadeController.class, CidadeDto.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JardaLinks jardaLinks;
	@Autowired
	private SecurityUtils securityUtils;
	
	@Override
	public CidadeDto toModel(Cidade cidade) {
		
		CidadeDto cidadeDto = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeDto);
		
	    if (securityUtils.podeConsultarCidades()) {
			cidadeDto.add(
					jardaLinks.linkToCidades("cidades"));
	    }
	    
	    if (securityUtils.podeConsultarEstados()) {
			cidadeDto.getEstado().add(
					jardaLinks.linkToEstado(cidade.getEstado().getId()));
	    }
		
		return cidadeDto;
	}
	
	@Override
	public CollectionModel<CidadeDto> toCollectionModel(Iterable<? extends Cidade> entities) {
	    CollectionModel<CidadeDto> collectionModel = super.toCollectionModel(entities);
	    
	    if (securityUtils.podeConsultarCidades()) {
	        collectionModel.add(jardaLinks.linkToCidades());
	    }
	    
	    return collectionModel;
	}
	
}
