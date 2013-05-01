package br.com.ecommerce.arq.sbeans;

import java.io.Serializable;

import br.com.ecommerce.arq.dao.DAOFactory;
import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.PersistDB;
import br.com.ecommerce.arq.erros.DAOException;

/**
 * Processador abstrato que deve ser herdado por todos os processadores do sistema.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public abstract class AbstractSBean implements Serializable{

	/**
	 * Retorna a instância do DAO
	 * @param daoClass Herda de GenericDAO
	 * @return A instância do DAO
	 * @throws DAOException
	 */
	public <T extends GenericDAOImpl> T getDAO(Class<T> daoClass)throws DAOException {
		return DAOFactory.getInstance().getDAO(daoClass, null);
	}
	
	public void atualizar(PersistDB obj) throws DAOException{
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.update(obj);
		}finally{
			dao.close();
		}
	}
}
