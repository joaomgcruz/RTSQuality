package br.ufrn.testtracker.data;

import java.util.LinkedHashSet;

public class CoveredMethod {
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

}
