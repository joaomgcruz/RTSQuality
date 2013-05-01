package br.com.ecommerce.arq.erros;

/**
 * Exception Relativa a erros de arquitetura
 */
public class ArqException extends Exception {
	public ArqException(String msg) {
        super(msg);
        printStackTrace();
    }
	
	public ArqException(Exception e) {
        super(e);
    }
	
	public ArqException(String mensagem, Exception e) {
    	super(mensagem, e);
    }
}
