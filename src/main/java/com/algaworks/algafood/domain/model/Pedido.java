package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafood.domain.enums.StatusPedido;
import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "pedido")
public class Pedido extends AbstractAggregateRoot<Pedido> {

	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String codigo;
	
	@Column
	private BigDecimal subtotal;
	
	@Column
	private BigDecimal taxaFrete = BigDecimal.ZERO;
	
	@Column
	private BigDecimal valorTotal;
	
	@Column
	@CreationTimestamp
	private OffsetDateTime dataCriacao;
	
	@Column
	private OffsetDateTime dataConfirmacao;
	
	@Column
	private OffsetDateTime dataCancelamento;
	
	@Column
	private OffsetDateTime dataEntrega;

	@Enumerated(EnumType.STRING)
	@Column
	private StatusPedido status = StatusPedido.CRIADO;
	
	@JsonIgnore
	@Embedded
	private Endereco enderecoEntrega;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "cliente_id", nullable = false)
	private Usuario cliente;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurante restaurante;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private FormaPagamento formaPagamento;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();
	
	
	public void calcularValorTotal() {
		this.itens.forEach(ItemPedido::calcularValorTotal);
		
		BigDecimal subTotal = getItens().stream()
		        .map(item -> item.getPrecoTotal())
		        .reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.setSubtotal(subTotal);
		
		this.setValorTotal(subTotal.add(getTaxaFrete()));
	}
	
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		
		registerEvent(new PedidoConfirmadoEvent(this));
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	public void confirmarEntrega() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}
	
	private void setStatus(StatusPedido novoStatus) {
		if(getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(String
					.format("Status do pedido %s não pode ser alterado de %s para %s",
							getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
		}
		
		this.status = novoStatus;
	}
	
	public boolean podeSerConfirmado() {
		return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
	}
	
	public boolean podeSerEntregue() {
		return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
	}
	
	public boolean podeSerCancelado() {
		return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
	}
	
	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}
	
}
