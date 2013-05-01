package br.com.ecommerce.dominio.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * SubTipo de produtos
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SubTipoProduto extends CadastroDB{

	/**
	 * Denominação do sub-tipo de produto.
	 */
	@Column(length=512)
	private String denominacao;
	
	/**
	 * Representa uma descrição do sub-tipo.
	 */
	@Column(length=1024)
	private String descricao;
	
	/**
	 * Tipo do produto.
	 */
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private TipoProduto tipo;
	
	public SubTipoProduto(){
		
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

	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}

	public TipoProduto getTipo() {
		return tipo;
	}
}
