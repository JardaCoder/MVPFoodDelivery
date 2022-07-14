package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteControlller;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoResumoDto;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDto>{
	
	@Autowired
	private ModelMapper modelMapper;
	
    public PedidoResumoDtoAssembler() {
        super(PedidoController.class, PedidoResumoDto.class);
    }
	
    @Override
	public PedidoResumoDto toModel(Pedido pedido) {
		PedidoResumoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);
        
        pedidoDto.add(linkTo(PedidoController.class).withRel("pedidos"));
        
        pedidoDto.getRestaurante().add(linkTo(methodOn(RestauranteControlller.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());
        
        pedidoDto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());
        
        return pedidoDto;
	}
	
	public List<PedidoResumoDto> pedidoResumosToListPedidoResumoDto(List<Pedido> pedidoResumos) {
		return pedidoResumos.stream().map(this::toModel).collect(Collectors.toList());
	}
	

}
