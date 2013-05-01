package br.com.ecommerce.dominio.produto;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.SimpleDB;

/**
 * Armazena as informações de disponibilidade de um determinado produto.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class DisponibilidadeProduto extends SimpleDB{

	/**
	 * Produto associado a esta disponibilidade.
	 */
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	/**
	 * Preço do produto X 100.
	 */
	private BigInteger preco;
	
	/**
	 * Quantidade do produto em estoque.
	 */
	private long quantidade;
	
	public DisponibilidadeProduto(){

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigInteger getPreco() {
		return preco;
	}

	public void setPreco(BigInteger preco) {
		this.preco = preco;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}
	
}
