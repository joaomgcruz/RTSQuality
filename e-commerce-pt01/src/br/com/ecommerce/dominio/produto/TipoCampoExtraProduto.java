package br.com.ecommerce.dominio.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.SimpleDB;

/**
 * Tipo de campo extra.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class TipoCampoExtraProduto extends SimpleDB{

	/**
	 * Denominação do novo campo a ser exibido para o consumidor.
	 */
	@Column(length=1024)
	private String denominacao;
	
	/**
	 * Representa uma descrição do tipo.
	 */
	@Column(length=1024)
	private String descricao;
	
	public TipoCampoExtraProduto(){
		
	}
	
	public String getDenominacao() {
		return denominacao;
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
