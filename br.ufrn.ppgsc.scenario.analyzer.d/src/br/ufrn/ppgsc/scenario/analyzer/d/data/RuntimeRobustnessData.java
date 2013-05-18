package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;

public class RuntimeRobustnessData extends RuntimeQAData<Robustness> {
	
	// TODO: Será que vale a pena armazenar informações sobre a exceção?
	
	private boolean fail;

	public boolean isFail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}

}
