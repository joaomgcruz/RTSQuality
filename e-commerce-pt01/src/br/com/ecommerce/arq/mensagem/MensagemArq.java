package br.com.ecommerce.arq.mensagem;

import java.util.Formatter;

import br.com.ecommerce.arq.dominio.TipoMensagem;

/**
 * Representa uma mensagem da arquitetura.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class MensagemArq {

	/**
	 * Mensagem será exibida ao usuário.
	 */
	private String mensagem;
	
	/**
	 * Tipo de mansagem, se é de erro, information...
	 */
	private TipoMensagem tipoMensagem;
	
	public MensagemArq(){
		
	}

	public MensagemArq(String mensagem, TipoMensagem tipoMensagem){
		this.mensagem = mensagem;
		this.tipoMensagem = tipoMensagem;
	}
	
	/**
	 * Retorna a mensagem formatada
	 * @param argumentos
	 * @return
	 */
	public String getMensagem(Object... argumentos) {
		StringBuilder mensagem = new StringBuilder();

		Formatter fm = new Formatter(mensagem);
	    fm.format(this.mensagem, argumentos);

	    return mensagem.toString();
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
}
