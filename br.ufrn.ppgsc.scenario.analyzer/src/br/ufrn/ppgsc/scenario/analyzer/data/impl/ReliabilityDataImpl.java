package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import br.ufrn.ppgsc.scenario.analyzer.data.ReliabilityData;

public class ReliabilityDataImpl extends AbstractQADataImpl implements ReliabilityData {

	private double failure_rate;

	public double getFailureRate() {
		return failure_rate;
	}

	public void setFailureRate(double failure_rate) {
		this.failure_rate = failure_rate;
	}

}
