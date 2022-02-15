package com.algaworks.algafood.domain.model.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendaDiariaReportDto {
	
	private List<VendaDiaria> vendasDiarias = new ArrayList<VendaDiaria>();
	
	private BigDecimal total =  new BigDecimal(1000);

	public List<VendaDiaria> getVendasDiarias() {
		return vendasDiarias;
	}

	public void setVendasDiarias(List<VendaDiaria> vendasDiarias) {
		this.vendasDiarias = vendasDiarias;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	
	
}
