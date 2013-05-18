package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;

public class RuntimeRobustnessData extends RuntimeQAData<Robustness> {
	
	private Throwable exception;

	public RuntimeRobustnessData() {
		exception = null;
	}
	
	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
