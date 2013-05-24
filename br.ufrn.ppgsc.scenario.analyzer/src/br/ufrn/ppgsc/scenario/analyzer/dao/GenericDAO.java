package br.ufrn.ppgsc.scenario.analyzer.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class GenericDAO <T extends Serializable> {

    public abstract T create(T newInstance);
    public abstract T read(T entity);
    public abstract T read(Long id);
    public abstract List<T> readAll(Class clazz);
    public abstract void update(T transientObject);
    public abstract void delete(T persistentObject);
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
	

	static EntityManager getEntityManager() {
		if (emf == null && em == null) {
			emf = Persistence.createEntityManagerFactory("NONJTAPersistenceUnit");
			em = emf.createEntityManager();
		}
		return em;
	}
	
}
