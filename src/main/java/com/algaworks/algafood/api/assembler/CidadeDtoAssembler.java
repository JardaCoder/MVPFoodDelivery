package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.CidadeDto;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDto> {
	
	public CidadeDtoAssembler() {
		super(CidadeController.class, CidadeDto.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CidadeDto toModel(Cidade cidade) {
		
		CidadeDto cidadeDto = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeDto);
		
		cidadeDto.add(linkTo(methodOn(CidadeController.class)
				.listar())
				.withRel("cidades"));
		
		cidadeDto.getEstado().add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeDto.getEstado().getId()))
				.withSelfRel());
		
		
		return cidadeDto;
	}
	
	@Override
	public CollectionModel<CidadeDto> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(CidadeController.class).withSelfRel());
	}
	
}
