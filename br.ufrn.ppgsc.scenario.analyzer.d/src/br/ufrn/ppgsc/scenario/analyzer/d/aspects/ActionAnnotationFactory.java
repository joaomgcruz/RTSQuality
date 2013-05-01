package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.d.actions.logging.LoggingText;

public abstract class ActionAnnotationFactory {

	private static Map<Class<? extends Annotation>, Class<? extends IActionAnnotation>> factory;
	
	static {
		factory = new HashMap<Class<? extends Annotation>, Class<? extends IActionAnnotation>>();
		
		factory.put(Performance.class, LoggingText.class);
		factory.put(Security.class, LoggingText.class);
		factory.put(Reliability.class, LoggingText.class);
	}
	
	@ScenarioIgnore
	public static IActionAnnotation createActionAnnotation(Class<? extends Annotation> cls_ann) {
		try {
			return (IActionAnnotation) factory.get(cls_ann).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@ScenarioIgnore
	public static void setClass(Class<? extends Annotation> ann, Class<? extends IActionAnnotation> action) {
		factory.put(ann, action);
	}
	
}
