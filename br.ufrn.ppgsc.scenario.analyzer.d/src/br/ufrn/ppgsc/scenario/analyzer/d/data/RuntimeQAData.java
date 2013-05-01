package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

public abstract class RuntimeQAData<T extends Annotation> {

	private T annotation;
	private Method method;

	@ScenarioIgnore
	public T getAnnotation() {
		return annotation;
	}

	@ScenarioIgnore
	public void setAnnotation(T annotation) {
		this.annotation = annotation;
	}

	@ScenarioIgnore
	public Method getMethod() {
		return method;
	}

	@ScenarioIgnore
	public void setMethod(Method method) {
		this.method = method;
	}
	
}
