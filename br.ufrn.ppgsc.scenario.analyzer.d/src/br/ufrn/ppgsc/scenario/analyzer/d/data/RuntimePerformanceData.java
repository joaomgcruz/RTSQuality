package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;

public class RuntimePerformanceData extends RuntimeQAData<Performance> {
	
	private long lastTime;
	
	public long getLastTime() {
		return lastTime;
	}
	
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}	

}
