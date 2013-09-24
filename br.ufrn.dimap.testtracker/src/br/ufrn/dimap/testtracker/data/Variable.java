package br.ufrn.dimap.testtracker.data;

import java.io.Serializable;

import br.ufrn.dimap.testtracker.util.ObjectUtil;

public class Variable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String name;
	private Object value;
	
	public Variable(String type, String name, Object value) {
		this.type = type;
		this.name = name;
		this.value = ObjectUtil.isSerializable(value);
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
