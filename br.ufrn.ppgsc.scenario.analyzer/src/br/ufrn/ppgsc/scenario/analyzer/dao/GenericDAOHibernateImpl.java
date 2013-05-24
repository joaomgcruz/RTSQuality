package br.ufrn.ppgsc.scenario.analyzer.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;

public class GenericDAOHibernateImpl <T extends Serializable> extends GenericDAO<T> {

	@Override
	public T create(T newInstance) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(newInstance);
		em.getTransaction().commit();
		return newInstance;
	}

	@Override
	public T read(T entity) {
		return null;
	}

	@Override
	public T read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T transientObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T persistentObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> readAll(Class clazz) {
		List objects = null;
        try {
        	EntityManager em = getEntityManager();
            Query query = em.createQuery("from " + clazz.getName());
            em.getTransaction().begin();
            objects = query.getResultList();
            em.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return objects;
	}

}
