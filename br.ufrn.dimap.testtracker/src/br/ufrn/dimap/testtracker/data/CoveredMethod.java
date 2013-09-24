package br.ufrn.dimap.testtracker.data;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class CoveredMethod implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private MethodData methodData;
	private Variable theReturn;
	private LinkedHashSet<Variable> inputs;
	
	public CoveredMethod(MethodData methodData, Variable theReturn, LinkedHashSet<Variable> inputs) {
		this.methodData = methodData;
		this.theReturn = theReturn;
		this.inputs = inputs;
	}

	public MethodData getMethodData() {
		return methodData;
	}

	public Variable getTheReturn() {
		return theReturn;
	}

	public void setTheReturn(Variable theReturn) {
		this.theReturn = theReturn;
	}

	public LinkedHashSet<Variable> getInputs() {
		return inputs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result + ((methodData == null) ? 0 : methodData.hashCode());
		result = prime * result + ((theReturn == null) ? 0 : theReturn.hashCode());
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
		if (theReturn == null) {
			if (other.theReturn != null)
				return false;
		} else if (!theReturn.equals(other.theReturn))
			return false;
		return true;
	}

	public String toString() {
		String inputStrings = "";
		for(Variable input : inputs)
			inputStrings += input.getValue()+", ";
		return methodData.getSignature()+(inputs.size() == 0 ? "\n" : " <- ("+inputStrings.substring(0,inputStrings.length()-2)+")\n");
	}

}
