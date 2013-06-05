package br.com.ecommerce.arq.dao;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.dominio.PersistDB;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.util.StringUtils;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;

/**
 * Implementa��o do GenericDAO para banco.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class GenericDAOImpl implements GenericDAO{
	/**
	 * Constantes para as operaes no banco.
	 */
	protected static final int INSERIR = 1;
	protected static final int ATUALIZAR = 2;
	protected static final int REMOVER = 3;

	/**
	 * Transa��o com o banco.
	 */
	private Transaction tx;

	/**
	 * Sess�o de comunica��o com o banco.
	 */
	protected Session session;

	/**
	 * Nome do dao.
	 */
	protected String daoName;

	public GenericDAOImpl(){
		daoName = "GenericDAO";
	}

	/**
	 * Desassocia o objeto com a sess�o.
	 */
	public void detach(PersistDB p) throws DAOException{

		try {
			getSession().evict(p);
		} catch (HibernateException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Retorna a int�ncia do session factory
	 * @return SessionFactory
	 */
	public SessionFactory getSF() {
		return DAOFactory.getInstance().getSessionFactory();
	}

	/**
	 * Busca um PersistDB pela sua chave prim�ria.
	 * @param <T> � o tipo do PersistDB.
	 * @param primaryKey � a chamve prim�ria.
	 * @param classe � a classe do PersistDB.
	 * 
	 * @return o PersistDB com a chave prim�ria informada.
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T extends PersistDB> T findByPrimaryKey(int primaryKey, Class<T> classe) throws DAOException {

		try {
			T obj = (T) getSession().get(classe, new Integer(primaryKey));

			return obj;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	/**
	 * Busca todos os PersistDBs associados � classe passada.
	 * @param <T> � o tipo do PersistDB
	 * @param classe � a classe que representa o tipo do PersistDB
	 * 
	 * @return Cole��o com os PersistDBs encontrados
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T extends PersistDB> Collection<T> findAll(Class<T> classe) throws DAOException {
		String hql = "from " + classe.getName();
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * Busca todos os PersistDBs associados � classe passada.
	 * @param <T> � o tipo do PersistDB
	 * @param classe � a classe que representa o tipo do PersistDB
	 * 
	 * @return Cole��o com os PersistDBs encontrados
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T extends PersistDB> Collection<T> findAll(Class<T> classe, String order) throws DAOException {
		String hql = "from " + classe.getName() + " order by " + order;
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * Busca todos os CadastroDBs ativos associados � classe passada.
	 * @param <T> � o tipo do PersistDB
	 * @param classe � a classe que representa o tipo do PersistDB
	 * 
	 * @return Cole��o com os CadastroDBs encontrados
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T extends CadastroDB> Collection<T> findAllAtivos(Class<T> classe) throws DAOException{
		String hql = "from " + classe.getName() + " where inativo = :inativo";
		return getSession().createQuery(hql).setBoolean("inativo", false).list();
	}

	/**
	 * Abre uma sess�o com o banco.
	 * 
	 * @return Sess�o com o banco recem aberta.
	 * @throws DAOException
	 */
	public synchronized Session getSession() throws DAOException {
		if (session == null || !session.isOpen()) {
			try {
				session = getSF().openSession();
			} catch (HibernateException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
				throw new DAOException("Nao foi possivel abrir sessao: " + e.getMessage(), e);
			}
		}
		
		return session;
	}

	/**
	 * M�todo para se efetuar uma opera��o com o banco.
	 * @param obj
	 * @param operacao
	 * @throws DAOException
	 */
	protected void changeOperation(PersistDB obj, int operacao) throws DAOException {
		try {
			tx = getSession().beginTransaction();
		
			switch (operacao) {
			case INSERIR:
				getSession().save(obj);
				break;
			case ATUALIZAR:
				getSession().update(obj);
				break;
			case REMOVER:
				getSession().delete(obj);
				break;
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	/**
	 * M�todo para efetuar a inser��o de um objeto mapedo no banco
	 * @param obj
	 * @throws DAOException
	 */
//	@UseCase(name="usecase_registration_user", step="3.2",
//			performance=@Performance(name="performance_registration_dao", max_value=2000),
//			security=@Security("security_registration_dao"))
	@Performance(name="Performance: save", limit_time=2000)
	@Reliability(name="Reliability: save")
	public void save(PersistDB obj) throws DAOException{
		changeOperation(obj, INSERIR);
	}
	
	/**
	 * M�todo para efetuar a inser��o de um log de anota��o
	 * @param obj
	 * @throws DAOException
	 */
	public void saveAnnotation(PersistDB obj) throws DAOException{
		changeOperation(obj, INSERIR);
	}

	/**
	 * M�todo para efetuar a atualiz�o de um objeto mapedo no banco.
	 * @param obj
	 * @throws DAOException
	 */
	public void update(PersistDB obj) throws DAOException{
		changeOperation(obj, ATUALIZAR);
	}

	/**
	 * M�todo para efetuar a remoro de um objeto mapedo no banco
	 * @param obj
	 * @throws DAOException
	 */
	public void delete(PersistDB obj) throws DAOException{
		changeOperation(obj, REMOVER);
	}

	/**
	 * M�todo para se fechar a session com o banco se esta esta aberta.
	 * @throws HibernateException
	 * @throws DAOException
	 */
	public void close() throws HibernateException, DAOException{
		if(getSession().isOpen())
			getSession().close();
	}
	
	/**
	 * Busca gen�rica com Query.
	 *
	 * @param classe
	 * @param field
	 * @param value
	 * @param exact
	 * @param orderType
	 * @param orderFields
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	private <T> Collection<T> findWithQuery(Class<T> classe, String field, Object value, boolean exact, boolean init, String orderType, String... orderFields) throws DAOException {
		try {

			String query = "from " + classe.getSimpleName() + " obj ";
			String orderQuery = "";
			if (exact) {
				if (value instanceof String || value instanceof Character) {
					value = "'" + StringUtils.trataAspasSimples(value.toString()) + "'";
				}
				query += " where obj." + field + "=" + value;
			} else if (!init){
				query += " where to_ascii(upper(obj." + field + "), 'LATIN9') like to_ascii(upper('%" + StringUtils.trataAspasSimples(value.toString()) + "%'),'LATIN9')";
			} else{
				query += " where to_ascii(upper(obj." + field + "), 'LATIN9') like to_ascii(upper('" + StringUtils.trataAspasSimples(value.toString()) + "%'),'LATIN9')";
			}
			
			Class classeAtual = classe;
			
			while(classeAtual != null && classeAtual.getSuperclass() != null){
				if(classeAtual.equals(CadastroDB.class)){
					query += " and obj.inativo = false ";
				}
				
				classeAtual = classeAtual.getSuperclass();
			}
			
			if (orderType != null & orderFields.length > 0) {
				orderQuery += " order by ";
				for (String f : orderFields) {
					orderQuery += f + ",";
				}
				orderQuery = orderQuery.substring(0, orderQuery
						.lastIndexOf(','));
				orderQuery += " " + orderType;

			}

			Query q = getSession().createQuery(query + orderQuery);

			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
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
	public <T> Collection<T> findByExactField(Class<T> classe, String field, Object value) throws DAOException {
		return findWithQuery(classe, field, value, true, false, null, new String[0]);
	}

}
