package br.com.ecommerce.dominio.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.SimpleDB;

/**
 * Campo adicional de descrição a ser exibido para o consumidor.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class CampoExtraProduto extends SimpleDB{

	@ManyToOne
	@JoinColumn(name="id_tipo")
	private TipoCampoExtraProduto tipo;
	
	/**
	 * Conteúdo a ser exibido.
	 */
	@Column(length=4096)
	private String conteudo;
	
	/**
	 * Posição em que será mostrado.
	 */
	private int posicao;
	
	/**
	 * Produto associado a este novo campo.
	 */
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	public CampoExtraProduto(){
		
	}

	public void setTipo(TipoCampoExtraProduto tipo) {
		this.tipo = tipo;
	}

	public TipoCampoExtraProduto getTipo() {
		return tipo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
