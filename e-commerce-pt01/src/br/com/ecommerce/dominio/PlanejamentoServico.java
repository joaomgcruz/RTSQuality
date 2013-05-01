package br.com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Agendamento de Serviços
 * @author Mario
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class PlanejamentoServico extends CadastroDB{
	
	@Column(length=1024)
	private String equipe;
	
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	public PlanejamentoServico(){
		
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	}
