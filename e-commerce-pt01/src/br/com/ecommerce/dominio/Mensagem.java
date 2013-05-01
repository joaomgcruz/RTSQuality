package br.com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * Representa uma mensagem cadastrada.
 * @author Thiago Viana Dantas
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Mensagem extends CadastroDB{
	
	/** Mensagem ativada no momento 
	 * 
	*/
	private boolean ativado;
	
	/**
	 * Denominação do tipo de produto.
	 */
	@Column(length=512)
	private String denominacao;
	
	public Mensagem(){
		
	}

	public boolean isAtivado() {
		return ativado;
	}

	public void setAtivado(boolean ativado) {
		this.ativado = ativado;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}
}
