package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.annotation.Annotation;

import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeQAData;

public interface IActionAnnotation {
	
	public void execute(RuntimeQAData<? extends Annotation> data);
	
}
