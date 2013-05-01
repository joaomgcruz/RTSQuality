package br.com.ecommerce.arq.aspects;

import javax.persistence.Column;

public aspect DemograficoIdade {
	@Column(name="idade",columnDefinition="idade")
	private int br.com.ecommerce.dominio.usuario.Demografico.idade;
	
	public int br.com.ecommerce.dominio.usuario.Demografico.getIdade() {
		return idade;
	}
	
	public void br.com.ecommerce.dominio.usuario.Demografico.setIdade(int idade) {
		this.idade = idade;
	}
}
