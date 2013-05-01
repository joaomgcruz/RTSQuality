package br.ufrn.ppgsc.scenario.analyzer.processors.impl;

import java.lang.annotation.Annotation;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.processors.AbstractProcessorQA;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

public class SecurityProcessor extends AbstractProcessorQA {

	public AbstractQAData createInstance() {
		return ScenarioAnalyzerUtil.getFactoryDataElement().createSecurityData();
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return Security.class;
	}

}
