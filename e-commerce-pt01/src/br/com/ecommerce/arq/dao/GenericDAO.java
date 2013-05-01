package br.com.ecommerce.arq.dao;

import java.util.Collection;

import org.hibernate.HibernateException;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.dominio.PersistDB;
import br.com.ecommerce.arq.erros.DAOException;

public interface GenericDAO {

	/**
	 * Desassocia o objeto com a sessão.
	 */
	public void detach(PersistDB p) throws DAOException;

	/**
	 * Busca um PersistDB pela sua chave primária.
	 * @param <T> é o tipo do PersistDB.
	 * @param primaryKey é a chamve primária.
	 * @param classe é a classe do PersistDB.
	 * 
	 * @return o PersistDB com a chave primária informada.
	 * @throws DAOException
	 */
	public <T extends PersistDB> T findByPrimaryKey(int primaryKey, Class<T> classe) throws DAOException;

	/**
	 * Busca todos os PersistDBs associados à classe passada.
	 * @param <T> é o tipo do PersistDB
	 * @param classe é a classe que representa o tipo do PersistDB
	 * 
	 * @return Coleção com os PersistDBs encontrados
	 * @throws DAOException
	 */
	public <T extends PersistDB> Collection<T> findAll(Class<T> classe) throws DAOException;
	
	/**
	 * Busca todos os PersistDBs associados à classe passada.
	 * @param <T> é o tipo do PersistDB
	 * @param classe é a classe que representa o tipo do PersistDB
	 * 
	 * @return Coleção com os PersistDBs encontrados
	 * @throws DAOException
	 */
	public <T extends PersistDB> Collection<T> findAll(Class<T> classe, String order) throws DAOException;
	
	/**
	 * Busca todos os CadastroDBs ativos associados à classe passada.
	 * @param <T> é o tipo do PersistDB
	 * @param classe é a classe que representa o tipo do PersistDB
	 * 
	 * @return Coleção com os CadastroDBs encontrados
	 * @throws DAOException
	 */
	public <T extends CadastroDB> Collection<T> findAllAtivos(Class<T> classe) throws DAOException;

	/**
	 * Método para efetuar a inserção de um objeto mapedo no banco
	 * @param obj
	 * @throws DAOException
	 */
	public void save(PersistDB obj) throws DAOException;
	
	/**
	 * Método para efetuar a inserção de um log de anotação
	 * @param obj
	 * @throws DAOException
	 */
	public void saveAnnotation(PersistDB obj) throws DAOException;

	/**
	 * Método para efetuar a atualizão de um objeto mapedo no banco.
	 * @param obj
	 * @throws DAOException
	 */
	public void update(PersistDB obj) throws DAOException;

	/**
	 * Método para efetuar a remoção de um objeto mapedo no banco
	 * @param obj
	 * @throws DAOException
	 */
	public void delete(PersistDB obj) throws DAOException;

	/**
	 * Método para se fechar a sessão.
	 * @throws HibernateException
	 * @throws DAOException
	 */
	public void close() throws HibernateException, DAOException;
	
	/**
	 * Busca todos os registros de uma classe EXATAMENTE com o valor do campo
	 * passado por parametro
	 *
	 * @param classe
	 * @param field
	 * @param value
	 * @return lista de registros
	 * @throws DAOException
	 */
	public <T> Collection<T> findByExactField(Class<T> classe, String field, Object value) throws DAOException;
	

}
