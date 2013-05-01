package br.com.ecommerce.dominio;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Relativo a estocagem de um produto.
 * @author Rodrigo Dutra de Oliveira
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Estoque extends CadastroDB{

	/**
	 * Produto relacionado a esta estocagem.
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	/**
	 * Quantidade de produtos.
	 */
	private int quantidade;
	
	/**
	 * Quantidade de itens restantes.
	 */
	private int restantes;
	
	public Estoque(){
		
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setRestantes(int restantes) {
		this.restantes = restantes;
	}

	public int getRestantes() {
		return restantes;
	}
}
