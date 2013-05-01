package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.ComponentData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.PerformanceData;
import br.ufrn.ppgsc.scenario.analyzer.data.ReliabilityData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.data.SecurityData;
import br.ufrn.ppgsc.scenario.analyzer.util.FactoryDataElement;

public class FactoryDataElementImpl implements FactoryDataElement {

	public ClassData createClassData() {
		return new ClassDataImpl();
	}

	public ComponentData createComponentData() {
		return new ComponentDataImpl();
	}

	public MethodData createMethodData() {
		return new MethodDataImpl();
	}

	public PerformanceData createPerformanceData() {
		return new PerformanceDataImpl();
	}

	public ScenarioData createScenarioData() {
		return new ScenarioDataImpl();
	}

	public SecurityData createSecurityData() {
		return new SecurityDataImpl();
	}
	
	public ReliabilityData createReliabilityData() {
		return new ReliabilityDataImpl();
	}

}
