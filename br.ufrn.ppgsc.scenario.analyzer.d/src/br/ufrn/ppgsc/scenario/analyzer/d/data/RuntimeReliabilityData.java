package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

public class RuntimeReliabilityData extends RuntimeQAData<Reliability> {
	
	private boolean fail;

	@ScenarioIgnore
	public boolean isFail() {
		return fail;
	}

	@ScenarioIgnore
	public void setFail(boolean fail) {
		this.fail = fail;
	}
	
}
