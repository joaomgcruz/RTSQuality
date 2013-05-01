package br.com.ecommerce.arq.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.util.SessionUtil;

/**
 * Fabrica de DAOs
 * @author Rodrigo Dutra de Oliveira
 */ 
public class DAOFactory {

	/**
	 * Padrão de projeto singleton.
	 */
	protected static DAOFactory singleton = null;

	/** 
	 * SessionFactory.
	 */
	protected SessionFactory sessionFactory;

	protected DAOFactory(){
		
	}

	/**
	 * Cria a session Factory
	 */
	private void createSessionFactory(){
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure("/br/com/ecommerce/arq/dao/hibernate.xml");
		sessionFactory = cfg.buildSessionFactory();
	}

	/** 
	 * Re-Constroi o SessionFactory Do IProject
	 */
	public void reloadSessionFactory() {
			if (sessionFactory != null)
				sessionFactory.close();
			createSessionFactory();
	}

	/**
	 * Retorna a única intância desta classe. Note que o synchronized torna este mtodo em mdia 100 vezes mais lento.
	 * @return nica instncia desta classe.
	 */
	public static DAOFactory getInstance() {
		if (singleton == null)
			singleton = new DAOFactory();
		return singleton;
	}
	
	/**
	 * Cria as tabelas no banco de dados
	 * @param classe
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public synchronized static void criarTabela(Class[] classe) throws DAOException{

		try{
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.configure("/br/com/ecommerce/arq/dao/hibernate.xml");
			
			for(Class classen : classe)
				cfg.addAnnotatedClass(classen);
			
			new SchemaExport(cfg).create(true, true);			
		}catch (Exception e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Retorna o SessionFactory
	 * @see Remover o synchronized e tornar estático.
	 * @return SessionFactory do IProject
	 */
	public synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			createSessionFactory();
		return sessionFactory;
	}

	/**
	 * Retorna a instância do DAO
	 * @param daoClass Herda de GenericDAO
	 * @param session sessão para implementação do open session in view.
	 * @return A instncia do DAO
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public <T extends GenericDAO> T getDAO(Class<T> daoClass, HttpSession session) throws DAOException {

		try {
			T dao = daoClass.newInstance();
			
			//Implementação do Open Session in View
			if(session != null){
				
				Class classeAtual = daoClass.getClass();
				
				while(classeAtual != null && classeAtual.getSuperclass() != null){
					if(classeAtual.equals(GenericDAOImpl.class)){
						//Atributo que guarda a sessão
						Field field = classeAtual.getDeclaredField("session");
						field.setAccessible(true);
						
						if(session.getAttribute(SessionUtil.SESSIONS_ATRIBUTE) != null){
							//Seta a sessão se já disponível
							field.set(dao, session.getAttribute(SessionUtil.SESSIONS_ATRIBUTE));
						}else{
							Method method = classeAtual.getDeclaredMethod("getSession");
							method.setAccessible(true);
							method.invoke(dao);
							session.setAttribute(SessionUtil.SESSIONS_ATRIBUTE, field.get(dao));
						}
					}
					
					classeAtual = classeAtual.getSuperclass();
				}
				
			}
				
			return dao;

		} catch (Exception e) {
			throw new DAOException("Erro ao obter DAO " + e.getMessage(), e);
		}
	}

	/**
	 * Fecha o SessionFactory e o disponibiliza para o GB
	 */
	public void clean(){
		if(sessionFactory != null){			
			sessionFactory.close();
			sessionFactory = null;
		}
	}

	/**
	 * Fecha o SessionFactory
	 */
	protected void finalize() {
		if(sessionFactory != null)
			sessionFactory.close();
	}
}
