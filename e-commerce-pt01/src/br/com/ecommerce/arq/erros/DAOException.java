package br.com.ecommerce.arq.erros;

/**
 * Exception Relativa a erros nos DAO's
 */
public class DAOException extends Exception {
	
	public DAOException(String msg) {
        super(msg);
        printStackTrace();
    }
	
	public DAOException(Exception e) {
        super(e);
    }
	
	public DAOException(String mensagem, Exception e) {
    	super(mensagem, e);
    }

}
