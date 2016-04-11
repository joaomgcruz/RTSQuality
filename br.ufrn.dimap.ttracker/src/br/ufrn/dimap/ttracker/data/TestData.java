package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class TestData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String signature;
	private String classFullName;
	private LinkedHashSet<Variable> inputs;
	private boolean manual;
	
	public TestData() {
		this.signature = new String();
		this.classFullName = new String();
		this.inputs = new LinkedHashSet<Variable>();
		this.manual = false;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getClassFullName() {
		return classFullName;
	}

	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}

	public LinkedHashSet<Variable> getInputs() {
		return inputs;
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((signature == null) ? 0 : signature.hashCode());
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestData other = (TestData) obj;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!signature.equals(other.signature))
			return false;
		if (inputs == null) {
			if (other.inputs != null)
				return false;
		} else if (!inputs.equals(other.inputs))
			return false;
		return true;
	}
	
}
