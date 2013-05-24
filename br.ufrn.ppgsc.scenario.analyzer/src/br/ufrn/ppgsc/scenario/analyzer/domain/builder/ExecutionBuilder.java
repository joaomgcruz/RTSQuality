package br.ufrn.ppgsc.scenario.analyzer.domain.builder;

import java.util.Date;

import br.ufrn.ppgsc.scenario.analyzer.domain.Execution;

public final class ExecutionBuilder {

	public static Execution build() {
		Execution execution = new Execution();
		execution.setDate(new Date());
		return execution;
	}
	
}
