package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoResumoDto;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDtoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoResumoDto pedidoToPedidoResumoDto(Pedido pedidoResumo) {
		return modelMapper.map(pedidoResumo, PedidoResumoDto.class);
	}
	
	public List<PedidoResumoDto> pedidoResumosToListPedidoResumoDto(List<Pedido> pedidoResumos) {
		return pedidoResumos.stream().map(this::pedidoToPedidoResumoDto).collect(Collectors.toList());
	}
	

}
