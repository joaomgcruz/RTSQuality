package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;

public class RuntimePerformanceData extends RuntimeQAData<Performance> {
	
	private long time;
	
	public long getLastTime() {
		return time;
	}
	
	public void setLastTime(long time) {
		this.time = time;
	}	

}
