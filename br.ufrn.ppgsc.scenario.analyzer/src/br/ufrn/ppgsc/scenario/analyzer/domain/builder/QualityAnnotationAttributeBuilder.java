package br.ufrn.ppgsc.scenario.analyzer.domain.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.domain.QualityAnnotationAttribute;
import br.ufrn.ppgsc.scenario.analyzer.domain.ScenarioQualityAnnotation;

public class QualityAnnotationAttributeBuilder {

	public static List<QualityAnnotationAttribute> build(Annotation annotation, ScenarioQualityAnnotation sqa) {
		List<QualityAnnotationAttribute> resultList = new ArrayList<QualityAnnotationAttribute>();
		if (annotation instanceof Performance) {
			for (Method method : ((Performance) annotation).annotationType().getDeclaredMethods()) {
				QualityAnnotationAttribute qaa = new QualityAnnotationAttribute();
				qaa.setName(method.getName());
				qaa.setType(method.getReturnType().getName());
				qaa.setScenarioQualityAnnotation(sqa);
				executeAnnotationMethod(((Performance) annotation), qaa);
				resultList.add(qaa);
			}
		} else if (annotation instanceof Robustness) {
			for (Method method : ((Robustness) annotation).annotationType().getDeclaredMethods()) {
				QualityAnnotationAttribute qaa = new QualityAnnotationAttribute();
				qaa.setName(method.getName());
				qaa.setType(method.getReturnType().getName());
				qaa.setScenarioQualityAnnotation(sqa);
				executeAnnotationMethod(((Robustness) annotation), qaa);
				resultList.add(qaa);
			}
		} else if (annotation instanceof Security) {
			for (Method method : ((Security) annotation).annotationType().getDeclaredMethods()) {
				QualityAnnotationAttribute qaa = new QualityAnnotationAttribute();
				qaa.setName(method.getName());
				qaa.setType(method.getReturnType().getName());
				qaa.setScenarioQualityAnnotation(sqa);
				executeAnnotationMethod(((Security) annotation), qaa);
				resultList.add(qaa);
			}
		} else if (annotation instanceof Reliability) {
			for (Method method : ((Reliability) annotation).annotationType().getDeclaredMethods()) {
				QualityAnnotationAttribute qaa = new QualityAnnotationAttribute();
				qaa.setName(method.getName());
				qaa.setType(method.getReturnType().getName());
				qaa.setScenarioQualityAnnotation(sqa);
				executeAnnotationMethod(((Reliability) annotation), qaa);
				resultList.add(qaa);
			}
		}
		return resultList;
	}

	private static void executeAnnotationMethod(Annotation annotation,
			QualityAnnotationAttribute qaa) {
		for (Method method : annotation.getClass().getDeclaredMethods()) {
			if (method.getName().equals(qaa.getName())) {
				try {
					qaa.setValue(method.invoke(annotation, null).toString());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
