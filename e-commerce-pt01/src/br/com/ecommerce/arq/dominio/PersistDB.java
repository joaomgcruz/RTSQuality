package br.com.ecommerce.arq.dominio;

import java.io.Serializable;

/**
 * Interface que deve ser implementada por todos os objetos que forem ser persisidos.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public interface PersistDB extends Serializable{

	public int getId();

	public void setId(int id);
}
