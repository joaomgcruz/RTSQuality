package br.ufrn.ppgsc.scenario.analyzer.domain.builder;

import java.lang.reflect.Method;

import br.ufrn.ppgsc.scenario.analyzer.domain.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.domain.StartMethod;

public class StartMethodBuilder {

	public static StartMethod build(Method method, Scenario scenario, Long executionTime, Boolean failed) {
		StartMethod startMethod = new StartMethod();
		startMethod.setName(method.getName());
		startMethod.setScenario(scenario);
		startMethod.setExecutionTime(executionTime);
		startMethod.setFailed(failed);
		return startMethod;
	}

}
