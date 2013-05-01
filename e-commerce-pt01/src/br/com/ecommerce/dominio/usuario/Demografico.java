package br.com.ecommerce.dominio.usuario;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.SimpleDB;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Demografico extends SimpleDB{

	@OneToOne
	@JoinColumn(name = "id_pessoa",unique = true)
	private Pessoa caracteristica;
	
	public Demografico(){
		
	}
	
	public Pessoa getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Pessoa caracteristica) {
		this.caracteristica = caracteristica;
	}

}
