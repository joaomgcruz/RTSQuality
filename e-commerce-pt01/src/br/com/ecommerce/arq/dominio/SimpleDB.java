package br.com.ecommerce.arq.dominio;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;


/**
 * Classe que deve ser herdada por qualquer objeto simples que deverá ser persistido.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@MappedSuperclass
public abstract class SimpleDB implements PersistDB, Comparable<SimpleDB>{
	
	/**
	 * Identificador do objeto.
	 */
	@Id
	@Column(name="id")
	@SequenceGenerator(name="SEQ_ID", sequenceName="public.seq_id", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ID")
	private int id;
	
	public SimpleDB(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Para que dois objetos persistentes sejam iguais eles só precisam ser de ids iguais.
	 */
	public int compareTo(SimpleDB o) {
		Integer i1 = new Integer(this.id);
		Integer i2 = new Integer(o.id);
		
		return i1.compareTo(i2);
	}
}
