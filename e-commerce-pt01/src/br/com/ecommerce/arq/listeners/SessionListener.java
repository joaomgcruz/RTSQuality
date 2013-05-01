package br.com.ecommerce.arq.listeners;

import java.util.ArrayList;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.ecommerce.arq.dominio.Mensagem;

/**
 * Listener para a sessão do usuário.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class SessionListener implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent se) {
		//Mensagens que poderão vir a ser exibidas para o usuário.
		se.getSession().setAttribute("mensagens", new ArrayList<Mensagem>());
	}

	/**
	 * Efetua as operações necessárias para o fechamento da sessão com o usuário.
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {

	}

}
