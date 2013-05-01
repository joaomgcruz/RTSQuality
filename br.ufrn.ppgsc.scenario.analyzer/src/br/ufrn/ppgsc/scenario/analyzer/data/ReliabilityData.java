package br.ufrn.ppgsc.scenario.analyzer.data;

public interface ReliabilityData extends AbstractQAData {

	public double getFailureRate();

	public void setFailureRate(double failure_rate);
	
}
