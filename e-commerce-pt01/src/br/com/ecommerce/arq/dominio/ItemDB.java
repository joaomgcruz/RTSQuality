package br.com.ecommerce.arq.dominio;

import javax.persistence.MappedSuperclass;

/**
 * Representa um item qualquer a ser persistido.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@MappedSuperclass
public class ItemDB extends CadastroDB{

	private String denominacao;
	
	public ItemDB(){
		
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public String getDenominacao() {
		return denominacao;
	}
	
	public String toString(){
		return denominacao;
	}
}
