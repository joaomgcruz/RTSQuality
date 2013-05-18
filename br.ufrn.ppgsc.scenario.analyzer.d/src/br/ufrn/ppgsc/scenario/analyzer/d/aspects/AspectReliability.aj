package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeReliabilityData;

public aspect AspectReliability {
	
	/*
	 * TODO: Atualmente a métrica para failure_rate não está sendo calculado, portanto
	 * podemos apenas identificar os cenários com este atributo de qualidade
	 */
	after() : execution(* *.*(..)) && @annotation(br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability) {
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Reliability annotation = method.getAnnotation(Reliability.class);
		
		RuntimeReliabilityData runtime = new RuntimeReliabilityData();
		runtime.setAnnotation(annotation);
		runtime.setMethod(method);
		runtime.setFail(true);
		
		ActionAnnotationFactory.createActionAnnotation(Reliability.class).execute(runtime);
	}
	
}
