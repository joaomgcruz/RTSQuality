package br.com.ecommerce.arq.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Representa os parâmetros que serão usados para informar como o sistema deve
 * se comportar.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Parametro extends SimpleDB{

	/**
	 * Código unico que representa cada parâmetro.
	 */
	@Column(unique=true)
	private String codigo;
	
	/**
	 * Nome do parâmetro
	 */
	private String nome;
	
	/**
	 * Descrição informando onde ele é usado.
	 */
	private String descricao;
	
	/**
	 * Valor propriamente dito do parâmetro.
	 */
	private String valor;
	
	public Parametro(){
		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
