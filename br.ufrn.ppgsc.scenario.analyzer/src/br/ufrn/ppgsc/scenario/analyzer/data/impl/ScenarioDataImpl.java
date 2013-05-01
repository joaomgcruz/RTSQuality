package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;

public class ScenarioDataImpl implements ScenarioData {

	private String name;
	private MethodData startMethod;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MethodData getStartMethod() {
		return startMethod;
	}

	public void setStartMethod(MethodData startMethod) {
		this.startMethod = startMethod;
	}

}
