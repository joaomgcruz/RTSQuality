package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeRobustnessData;

public aspect AspectRobustness {
	
	private pointcut executionIgnored() : within(br.ufrn.ppgsc.scenario.analyzer..*) || adviceexecution();
	
	// dessa forma pega com try-catch
	before(Throwable t): handler(Throwable+) && args(t) && !executionIgnored() {
		Method method = ((MethodSignature) thisEnclosingJoinPointStaticPart.getSignature()).getMethod();
		Robustness annotation = method.getAnnotation(Robustness.class);
		
		// ignora se o método não estiver anotado
		if (annotation != null) {
			RuntimeRobustnessData runtime = new RuntimeRobustnessData();
			runtime.setAnnotation(annotation);
			runtime.setMethod(method);
			runtime.setException(t);
			
			ActionAnnotationFactory.createActionAnnotation(Robustness.class).execute(runtime);
		}
	}
	
	// pega exceções sem try-catch
	after() throwing(Throwable t): execution(* *.*(..)) && !executionIgnored() && @annotation(br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness) {
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Robustness annotation = method.getAnnotation(Robustness.class);
		
		RuntimeRobustnessData runtime = new RuntimeRobustnessData();
		runtime.setAnnotation(annotation);
		runtime.setMethod(method);
		runtime.setException(t);
		
		ActionAnnotationFactory.createActionAnnotation(Robustness.class).execute(runtime);
	}
	
}
