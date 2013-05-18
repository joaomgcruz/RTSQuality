package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimePerformanceData;

public aspect AspectPerformance {
	
	Object around() : execution(* *.*(..)) && @annotation(br.ufrn.ppgsc.scenario.analyzer.annotations.Performance) {
		long begin, end;
		
		begin = System.currentTimeMillis();
		Object o = proceed();
		end = System.currentTimeMillis();
		
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Performance annotation = method.getAnnotation(Performance.class);
		
		RuntimePerformanceData runtime = new RuntimePerformanceData();
		runtime.setAnnotation(annotation);
		runtime.setMethod(method);
		runtime.setLastTime(end - begin);
		
		ActionAnnotationFactory.createActionAnnotation(Performance.class).execute(runtime);

		return o;
	}

}
