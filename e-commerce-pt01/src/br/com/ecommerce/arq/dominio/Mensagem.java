package br.com.ecommerce.arq.dominio;

import java.io.Serializable;

/**
 * Mensagem que será exibida ao usuário
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class Mensagem implements Serializable{

	/**
	 * Mensagem em si que será exibida.
	 */
	private String mensagem;
	
	/**
	 * Tipo da mensagem a ser exibida.
	 */
	private TipoMensagem tipoMensagem;
	
	public Mensagem(){
		
	}
	
	public Mensagem(String mensagem){
		this.mensagem = mensagem;
	}
	
	public Mensagem(String mensagem, TipoMensagem tipoMensagem){
		this.mensagem = mensagem;
		this.tipoMensagem = tipoMensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public TipoMensagem getTipoMensagem() {
		return tipoMensagem;
	}

	public void setTipoMensagem(TipoMensagem tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}
	
	@Override
	public String toString(){
		return mensagem == null ? super.toString() : mensagem;
	}
}
