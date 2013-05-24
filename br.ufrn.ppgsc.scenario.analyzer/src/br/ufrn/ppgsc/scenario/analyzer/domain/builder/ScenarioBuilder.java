package br.ufrn.ppgsc.scenario.analyzer.domain.builder;

import java.lang.reflect.Method;

import br.ufrn.ppgsc.scenario.analyzer.domain.Execution;
import br.ufrn.ppgsc.scenario.analyzer.domain.Scenario;

public final class ScenarioBuilder {

	public static Scenario build(Method method, Execution execution) {
		String scenarioName = method.getAnnotation(br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario.class).name();
		Scenario scenario = new Scenario();
		scenario.setName(scenarioName);
		scenario.setExecution(execution);
		return scenario;
	}
	
}
