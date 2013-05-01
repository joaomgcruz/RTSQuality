package br.com.ecommerce.arq.helper;

import org.hibernate.HibernateException;

import br.com.ecommerce.arq.dao.DAOFactory;
import br.com.ecommerce.arq.dao.ParametroDAO;
import br.com.ecommerce.arq.erros.DAOException;

/**
 * Classe helper para tratar com parâmetros no sistema.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class ParametroHelper {
	
	/**
	 * Singleton desta classe.
	 */
	private static ParametroHelper singleton;

	private ParametroHelper(){
		
	}
	
	/**
	 * @return a instância desta classe.
	 */
	public static ParametroHelper getInstance(){
		if(singleton == null)
			singleton = new ParametroHelper();
		return singleton;
	}
	
	/**
	 * Método usado para se obter um parametro passando seu código.
	 * 
	 * @return o valor do parâmetro.
	 * @throws DAOException 
	 * @throws HibernateException 
	 */
	public String getParamametro(String codigo) throws DAOException{
		return DAOFactory.getInstance().getDAO(ParametroDAO.class, null).getParametro(codigo);
	}
	
	/**
	 * Método usado para se obter um valor inteiro de um parâmetro.
	 *  
	 * @param codigo
	 * @return
	 * @throws NumberFormatException
	 * @throws HibernateException
	 * @throws DAOException
	 */
	public int getIntParametro(String codigo) throws NumberFormatException, DAOException{
		return Integer.valueOf(getParamametro(codigo)); 
	}

	/**
	 * Busca por um parâmetro booleano.
	 * 
	 * @param codigo
	 * @return
	 * @throws DAOException
	 */
	public boolean getBooleanParametro(String codigo) throws DAOException{
		return Boolean.valueOf(getParamametro(codigo));
	}
}
