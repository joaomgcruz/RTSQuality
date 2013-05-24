package br.ufrn.ppgsc.scenario.analyzer.domain.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.domain.QualityAnnotationAttribute;
import br.ufrn.ppgsc.scenario.analyzer.domain.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.domain.ScenarioQualityAnnotation;

public class ScenarioQualityAnnotationBuilder {

	public static List<ScenarioQualityAnnotation> build(Method method, Scenario scenario) {
		Annotation[] annotations = method.getAnnotations();
		List<ScenarioQualityAnnotation> scenarioQualityAnnotations = new ArrayList<ScenarioQualityAnnotation>();
		for (Annotation annotation : annotations) {
			try {
				ScenarioQualityAnnotation sqa = new ScenarioQualityAnnotation();
				br.ufrn.ppgsc.scenario.analyzer.domain.QualityAnnotation qa = QualityAnnotationBuilder.build(annotation);
				if (qa != null) {
					List<QualityAnnotationAttribute> qualityAnnotationAttributes = QualityAnnotationAttributeBuilder.build(annotation, sqa);
					sqa.setQualityAnnotationAttributes(qualityAnnotationAttributes);
					sqa.setQualityAnnotation(qa);
					sqa.setScenario(scenario);
					scenarioQualityAnnotations.add(sqa);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return scenarioQualityAnnotations;
	}

}
