package br.ufrn.ppgsc.scenario.analyzer.processors.impl;

import java.lang.annotation.Annotation;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.processors.AbstractProcessorQA;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

public class PerformanceProcessor extends AbstractProcessorQA {

	public AbstractQAData createInstance() {
		return ScenarioAnalyzerUtil.getFactoryDataElement().createPerformanceData();
	}

	public Class<? extends Annotation> getAnnotationClass() {
		return Performance.class;
	}

}
