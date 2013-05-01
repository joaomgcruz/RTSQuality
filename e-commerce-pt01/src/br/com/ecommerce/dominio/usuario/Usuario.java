package br.com.ecommerce.dominio.usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * Entidade que representa os usuarios disponíveis.
 * @author Thiago Viana Dantas
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Usuario extends CadastroDB {

	/**
	 * Pessoa que representa este usuário.
	 */
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="id_pessoa", unique=true)
	private Pessoa pessoa;
	
	/**
	 * Cartão de identificação do usuário
	 */
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="id_security_card", unique=true, nullable=false)
	private SecurityCard securityCard;
	
	public Usuario (){
		
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public SecurityCard getSecurityCard() {
		return securityCard;
	}

	public void setSecurityCard(SecurityCard securityCard) {
		this.securityCard = securityCard;
	}

}
