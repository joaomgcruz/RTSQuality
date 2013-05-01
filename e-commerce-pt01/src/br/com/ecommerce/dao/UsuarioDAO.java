package br.com.ecommerce.dao;

import org.hibernate.Query;

import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.erros.DAOException;

public class UsuarioDAO extends GenericDAOImpl{

	public UsuarioDAO(){
		daoName = this.getClass().getName();
	}
	
	public Long findQuantUsuariosByLogin(String login) throws DAOException{
		String hql =  "select count(*) from SecurityCard as s where s.login = :login";
		Query query = getSession().createQuery(hql);
		query.setString("login", login);
		return (Long) query.uniqueResult();
	}
}
