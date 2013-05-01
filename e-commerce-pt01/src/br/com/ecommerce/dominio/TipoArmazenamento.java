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
public class TipoArmazenamento extends SimpleDB{

	@Column(length=512)
	private String descricao;
	
	public TipoArmazenamento(){
		
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

		
}
