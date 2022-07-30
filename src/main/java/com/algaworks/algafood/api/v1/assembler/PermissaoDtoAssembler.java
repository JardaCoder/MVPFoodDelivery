package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.model.PermissaoDto;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDtoAssembler implements RepresentationModelAssembler<Permissao, PermissaoDto>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	@Override
	public PermissaoDto toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoDto.class);
	}
	
  @Override
    public CollectionModel<PermissaoDto> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(jardaLinks.linkToPermissoes());
    } 
}
