package br.com.ecommerce.dominio.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * Armazena as informações do cartão de crédito do usuário.
 * @author Thiago Viana Dantas
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class CreditCard extends CadastroDB{

		/**
		 * Nome do titular do cartão de crédito.
		 */
		@Column(length=512, name="nome_titular")
		private String nomeTitular;
		
		/**
		 * Número do cartão de crédito
		 */
		@Column(name="numero_cartao")
		private long numeroCartao;
		
		/**
		 * Data de vencimento da cartão de crédito.
		 */
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="data_vencimento")
		private Date dataVencimento;
		
		/**
		 * Proprietário do cartão.
		 */
		@ManyToOne(fetch=FetchType.EAGER)
		@JoinColumn(name="id_pessoa")
		private Pessoa proprietario;
		
		public CreditCard(){
			
		}

		public String getNomeTitular() {
			return nomeTitular;
		}

		public void setNomeTitular(String nomeTitular) {
			this.nomeTitular = nomeTitular;
		}

		public long getNumeroCartao() {
			return numeroCartao;
		}

		public void setNumeroCartao(long numeroCartao) {
			this.numeroCartao = numeroCartao;
		}

		public Date getDataVencimento() {
			return dataVencimento;
		}

		public void setDataVencimento(Date dataVencimento) {
			this.dataVencimento = dataVencimento;
		}

		public void setProprietario(Pessoa proprietario) {
			this.proprietario = proprietario;
		}

		public Pessoa getProprietario() {
			return proprietario;
		}		
		
}
