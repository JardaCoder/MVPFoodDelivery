package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Integer quantidade;
	
	@Column
	private BigDecimal precoUnitario;
	
	@Column
	private BigDecimal precoTotal;
	
	@Column
	private String observacao;
	
	@ManyToOne
	@JoinColumn
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn
	private Produto produto;
	
	
	public void calcularValorTotal() {
		BigDecimal valorUnitario = this.precoUnitario;
		Integer quantidade = this.quantidade;
		
		if(quantidade == null) {
			quantidade = 0;
		}
		
		if(valorUnitario == null) {
			valorUnitario = BigDecimal.ZERO;
		}
		
		this.setPrecoTotal(valorUnitario.multiply(new BigDecimal(quantidade)));
		
	}
	
}
