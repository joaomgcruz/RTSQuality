package br.com.ecommerce.dominio.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * Tipos de Produtos disponíveis
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class TipoProduto extends CadastroDB{
	
	/**
	 * Denominação do tipo de produto.
	 */
	@Column(length=512)
	private String denominacao;
	
	/**
	 * Representa uma descrição do tipo.
	 */
	@Column(length=1024)
	private String descricao;
	
	public TipoProduto(){
		
	}

	public void setDenominacao(String denominacao) {
		this.denominacao = denominacao;
	}

	public String getDenominacao() {
		return denominacao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
