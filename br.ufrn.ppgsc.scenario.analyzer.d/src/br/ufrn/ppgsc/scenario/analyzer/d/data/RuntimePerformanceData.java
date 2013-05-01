package br.ufrn.ppgsc.scenario.analyzer.d.data;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

public class RuntimePerformanceData extends RuntimeQAData<Performance> {
	
	private long lastTime;
	
	@ScenarioIgnore
	public long getLastTime() {
		return lastTime;
	}
	
	@ScenarioIgnore
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}	

}
