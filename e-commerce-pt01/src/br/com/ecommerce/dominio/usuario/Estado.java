package br.com.ecommerce.dominio.usuario;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.ItemDB;

/**
 * Representa um estado.
 * @author Thiago Viana Dantas
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Estado extends ItemDB{

	public Estado(){
		
	}
}
