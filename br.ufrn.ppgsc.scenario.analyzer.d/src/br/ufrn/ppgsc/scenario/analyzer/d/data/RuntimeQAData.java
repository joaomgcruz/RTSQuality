package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class RuntimeQAData<T extends Annotation> {

	private T annotation;
	private Method method;

	public T getAnnotation() {
		return annotation;
	}

	public void setAnnotation(T annotation) {
		this.annotation = annotation;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
}
