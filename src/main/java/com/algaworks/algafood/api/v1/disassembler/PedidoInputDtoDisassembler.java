package com.algaworks.algafood.api.v1.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.PedidoInputDto;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Pedido pedidoInputDtoToPedido(PedidoInputDto pedidoInputDto) {
		return modelMapper.map(pedidoInputDto, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoInputDto pedidoInputDto, Pedido pedido) {
		modelMapper.map(pedidoInputDto, pedido);
	}
}
