package br.ufrn.testtracker.data;

import java.io.Serializable;

public class Input implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> type;
	private String name;
	private Object value;
	
	public Input(Class<?> type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public Class<?> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
