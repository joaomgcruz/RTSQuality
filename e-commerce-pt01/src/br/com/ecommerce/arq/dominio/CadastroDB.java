package br.com.ecommerce.arq.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe que deve ser herdada por todo objeto que possa ser cadastrado pelo usuário
 * no banco de dados.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@MappedSuperclass
public abstract class CadastroDB extends SimpleDB{

	/**
	 * Indica se o objeto esta ativo ou não.
	 * Um objeto inativo é como se tivesse sido deletado do banco.
	 */
	@Column(name="inativo")
	private boolean inativo;
	
	/**
	 * Data de Cadastro do objeto.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_cadastro")
	private Date dataCadastro;
	
	@ManyToOne
	@JoinColumn(name="id_registro_entrada_cadastro")
	private RegistroEntrada registroEntradaCadastro;
	
	/**
	 * Data em que o objeto foi inativado.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_inativacao")
	private Date dataInativacao;
	
	@ManyToOne
	@JoinColumn(name="id_registro_entrada_inativacao")
	private RegistroEntrada registroEntradaInativacao;
	
	public CadastroDB(){
		
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public RegistroEntrada getRegistroEntradaCadastro() {
		return registroEntradaCadastro;
	}

	public void setRegistroEntradaCadastro(RegistroEntrada registroEntradaCadastro) {
		this.registroEntradaCadastro = registroEntradaCadastro;
	}

	public RegistroEntrada getRegistroEntradaInativacao() {
		return registroEntradaInativacao;
	}

	public void setRegistroEntradaInativacao(
			RegistroEntrada registroEntradaInativacao) {
		this.registroEntradaInativacao = registroEntradaInativacao;
	}
}
