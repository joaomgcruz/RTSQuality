package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

public class RuntimeRobustnessData extends RuntimeQAData<Robustness> {
	
	// TODO: Será que vale a pena armazenar informações sobre a exceção?
	
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
