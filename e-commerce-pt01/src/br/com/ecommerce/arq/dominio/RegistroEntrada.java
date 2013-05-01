package br.com.ecommerce.arq.dominio;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Registro de entrada do usuário.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class RegistroEntrada extends SimpleDB{

	public RegistroEntrada(){
		
	}
}
