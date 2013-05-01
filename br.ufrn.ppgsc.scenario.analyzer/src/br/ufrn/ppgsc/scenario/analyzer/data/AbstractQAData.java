package br.ufrn.ppgsc.scenario.analyzer.data;

import java.lang.annotation.Annotation;

public interface AbstractQAData extends AbstractData {
	
	public Class<? extends Annotation> getType();
	
	public void setType(Class<? extends Annotation> cls);
	
	public MethodData getMethod();

	public void setMethod(MethodData method);

}
