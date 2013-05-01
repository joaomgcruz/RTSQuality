package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeSecurityData;

public aspect AspectSecurity {

	@ScenarioIgnore
	after() : execution(* *.*(..)) && @annotation(br.ufrn.ppgsc.scenario.analyzer.annotations.Security) {
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Security annotation = method.getAnnotation(Security.class);
		
		RuntimeSecurityData runtime = new RuntimeSecurityData();
		runtime.setAnnotation(annotation);
		runtime.setMethod(method);
		
		ActionAnnotationFactory.createActionAnnotation(Security.class).execute(runtime);
	}
	
}
