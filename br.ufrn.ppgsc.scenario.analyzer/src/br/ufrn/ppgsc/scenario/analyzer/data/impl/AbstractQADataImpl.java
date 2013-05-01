package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import java.lang.annotation.Annotation;

import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;

public abstract class AbstractQADataImpl implements AbstractQAData {

	private String name;
	private Class<? extends Annotation> cls;
	private MethodData method;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<? extends Annotation> getType() {
		return cls;
	}

	public void setType(Class<? extends Annotation> cls) {
		this.cls = cls;
	}

	public MethodData getMethod() {
		return method;
	}

	public void setMethod(MethodData method) {
		this.method = method;
	}

}
