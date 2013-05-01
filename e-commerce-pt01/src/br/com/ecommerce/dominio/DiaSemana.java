package br.com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.SimpleDB;

/**
 * Dia da Semana
 * @author Mario
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class DiaSemana extends SimpleDB{

	@Column(length=512)
	private String denominacao;
	
	public DiaSemana(){
		
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
	
}
