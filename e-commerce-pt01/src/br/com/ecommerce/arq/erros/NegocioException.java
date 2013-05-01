package br.com.ecommerce.arq.erros;

/**
 * Exception Relativa a erros de negócio
 */
public class NegocioException extends Exception {
	public NegocioException(String msg) {
        super(msg);
        printStackTrace();
    }
	
	public NegocioException(Exception e) {
        super(e);
    }
	
	public NegocioException(String mensagem, Exception e) {
    	super(mensagem, e);
    }
}
