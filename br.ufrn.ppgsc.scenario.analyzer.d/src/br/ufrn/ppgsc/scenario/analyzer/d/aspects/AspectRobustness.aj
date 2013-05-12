package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeRobustnessData;

public aspect AspectRobustness {
	
	// dessa forma pega com try-catch
	@ScenarioIgnore
	before(Throwable t): handler(Throwable+) && args(t) {
		System.out.println("handler");
		
		Method method = ((MethodSignature) thisEnclosingJoinPointStaticPart.getSignature()).getMethod();
		
		Robustness annotation = method.getAnnotation(Robustness.class);
		
		// ignora se o método não estiver anotado
		if (annotation != null) {
			RuntimeRobustnessData runtime = new RuntimeRobustnessData();
			runtime.setAnnotation(annotation);
			runtime.setMethod(method);
			runtime.setFail(false);
			
			ActionAnnotationFactory.createActionAnnotation(Robustness.class).execute(runtime);
		}
	}
	
	// pega exceções sem try-catch
	@ScenarioIgnore
	after() throwing(Throwable t) : execution(* *.*(..)) && @annotation(br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness) {
		System.out.println("throwing");
		
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Robustness annotation = method.getAnnotation(Robustness.class);
		
		RuntimeRobustnessData runtime = new RuntimeRobustnessData();
		runtime.setAnnotation(annotation);
		runtime.setMethod(method);
		runtime.setFail(true);
		
		ActionAnnotationFactory.createActionAnnotation(Robustness.class).execute(runtime);
	}
	
}
