package br.com.ecommerce.dominio.usuario;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;

/**
 * Armazena as informações de seguraça do usuário.
 * @author Thiago Viana Dantas
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SecurityCard extends CadastroDB{

	/**
	 * Login do usuário no sistema
	 */
	private String login;
	
	/**
	 * Senha do usuário no sistema.
	 * Deve ser em md5
	 */
	private String senha;
	
	public SecurityCard(){
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login.trim();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}

