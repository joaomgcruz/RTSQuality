package br.com.ecommerce.arq.dominio;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Tipos de mensagens a serem exibidas para o usuário
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table(schema="arq")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class TipoMensagem extends ItemDB{

	/**
	 * Mensagem de erro.
	 */
	public static TipoMensagem ERRO = new TipoMensagem(1);
	
	/**
	 * Mensagem de alerta.
	 */
	public static TipoMensagem WARNING = new TipoMensagem(2);
	
	/**
	 * Mensagem de informação.
	 */
	public static TipoMensagem INFORMATION = new TipoMensagem(3);
	
	
	public TipoMensagem(){
		
	}

	public TipoMensagem(int id){
		setId(id);
	}
}
