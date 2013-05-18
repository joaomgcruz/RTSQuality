package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;

public class RuntimeReliabilityData extends RuntimeQAData<Reliability> {
	
	private boolean fail;

	public boolean isFail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}
	
}
