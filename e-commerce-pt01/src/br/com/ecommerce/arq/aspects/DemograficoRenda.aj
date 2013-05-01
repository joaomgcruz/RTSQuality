package br.com.ecommerce.arq.aspects;

import javax.persistence.Column;

public aspect DemograficoRenda {
	@Column(name="renda",columnDefinition="renda")
	private double br.com.ecommerce.dominio.usuario.Demografico.renda;
	
	public double br.com.ecommerce.dominio.usuario.Demografico.getRenda() {
		return renda;
	}
	
	public void br.com.ecommerce.dominio.usuario.Demografico.setRenda(double renda) {
		this.renda = renda;
	}
}
