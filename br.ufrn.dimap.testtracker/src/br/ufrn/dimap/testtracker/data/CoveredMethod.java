package br.ufrn.dimap.testtracker.data;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class CoveredMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private MethodData methodData;
	private LinkedHashSet<Input> inputs;
	
	public CoveredMethod(MethodData methodData, LinkedHashSet<Input> inputs) {
		this.methodData = methodData;
		this.inputs = inputs;
	}

	public MethodData getMethodData() {
		return methodData;
	}

	public LinkedHashSet<Input> getInputs() {
		return inputs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result + ((methodData == null) ? 0 : methodData.hashCode());
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
		CoveredMethod other = (CoveredMethod) obj;
		if (inputs == null) {
			if (other.inputs != null)
				return false;
		} else if (!inputs.equals(other.inputs))
			return false;
		if (methodData == null) {
			if (other.methodData != null)
				return false;
		} else if (!methodData.equals(other.methodData))
			return false;
		return true;
	}
	
	public String toString() {
		String inputStrings = "";
		for(Input input : inputs)
			inputStrings += input.getValue()+", ";
		return methodData.getSignature()+(inputs.size() == 0 ? "\n" : " <- ("+inputStrings.substring(0,inputStrings.length()-2)+")\n");
	}

}
