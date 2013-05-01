package br.com.ecommerce.arq.jsf;

import br.com.ecommerce.arq.dominio.SimpleDB;

/**
 * Controler para facilitar o cadastro de CadastroDBs
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class CadastroAbstractController<T extends SimpleDB> extends AbstractController{

	/**
	 * Objeto que será cadastrado.
	 */
	protected T obj;
	
	public CadastroAbstractController(){

	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
}
