package br.com.ecommerce.dominio.usuario;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.ItemDB;

/**
 * Representa uma cidade.
 * @author Thiago Viana Dantas
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Cidade extends ItemDB{

	/**
	 * Representa o estado onde a cidade está localizada.
	 */
	@ManyToOne(fetch=FetchType.EAGER, cascade = javax.persistence.CascadeType.ALL)
	private Estado estado;
	
	public Cidade(){
		
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
