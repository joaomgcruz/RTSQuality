package br.ufrn.ppgsc.scenario.analyzer.util;

import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.ComponentData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.PerformanceData;
import br.ufrn.ppgsc.scenario.analyzer.data.ReliabilityData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.data.SecurityData;

public interface FactoryDataElement {

	public ClassData createClassData();

	public ComponentData createComponentData();

	public MethodData createMethodData();

	public PerformanceData createPerformanceData();

	public ScenarioData createScenarioData();

	public SecurityData createSecurityData();
	
	public ReliabilityData createReliabilityData();

}
