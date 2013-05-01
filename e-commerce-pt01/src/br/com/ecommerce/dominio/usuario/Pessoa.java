package br.com.ecommerce.dominio.usuario;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * Pessoa geral na aplicação.
 * @author Thiago Viana Dantas
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Pessoa extends CadastroDB {

	/**
	 * Nome da pessoa geral.
	 */
	@Column(length = 512)
	private String nome;

	/**
	 * Endereço da pessoa
	 */
	@OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	@OrderBy("dataCadastro ASC")
	private Collection<Endereco> endereco;

	/**
	 * Data de nascimento da pessoa.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_nacimento")
	private Date dataNascimento;

	/** 
	 * E-mail da pessoa.
	 */
	@Column(length = 128)
	private String email;

	/** 
	 * CPF da pessoa.
	 */
	@Column(name = "cpf")
	private long CPF;

	/**
	 * RG da pessoa.
	 */
	@Column(name = "rg")
	private long RG;

	@OneToOne(mappedBy = "caracteristica", cascade = javax.persistence.CascadeType.ALL)
	private Demografico demografico;

	/**
	 * Cartão de crédito da pessoa.
	 */
	@OneToMany(mappedBy = "proprietario", fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	private Collection<CreditCard> creditCards;

	public Pessoa() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(Collection<Endereco> endereco) {
		this.endereco = endereco;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCPF() {
		return CPF;
	}

	public void setCPF(long cPF) {
		CPF = cPF;
	}

	public long getRG() {
		return RG;
	}

	public void setRG(long rG) {
		RG = rG;
	}

	public Demografico getDemografico() {
		return demografico;
	}

	public void setDemografico(Demografico demografico) {
		this.demografico = demografico;
	}

	public Collection<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}
