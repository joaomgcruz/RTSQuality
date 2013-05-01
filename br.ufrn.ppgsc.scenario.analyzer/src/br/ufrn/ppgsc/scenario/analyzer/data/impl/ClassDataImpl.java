package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.ComponentData;

public class ClassDataImpl implements ClassData {

	private String name;
	private ComponentData declaringComponent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComponentData getDeclaringComponent() {
		return declaringComponent;
	}

	public void setDeclaringComponent(ComponentData declaringComponent) {
		this.declaringComponent = declaringComponent;
	}

}
