package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido {

	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private BigDecimal subtotal;
	
	@Column
	private BigDecimal taxaFrete;
	
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
	private StatusPedido statusPedido;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "cliente_id", nullable = false)
	private Usuario cliente;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurante restaurante;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private FormaPagamento formaPagamento;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();
	
}
