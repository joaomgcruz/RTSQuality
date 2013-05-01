package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

public class ExecutionPaths {

	private static final ExecutionPaths instance = new ExecutionPaths();
	
	private List<RuntimeCallGraph> cg_list;

	private ExecutionPaths() {
		cg_list = new ArrayList<RuntimeCallGraph>();
	}

	@ScenarioIgnore
	public static ExecutionPaths getInstance() {
		return instance;
	}
	
	@ScenarioIgnore
	public void addRuntimeCallGraph(RuntimeCallGraph cg) {
		cg_list.add(cg);
	}

	@ScenarioIgnore
	public RuntimeCallGraph[] getAllRuntimeCallGraph() {
		return cg_list.toArray(new RuntimeCallGraph[0]);
	}
	
	@ScenarioIgnore
	public RuntimeCallGraph[] getRuntimeCallGraphsByScenarioName(String scenario_name) {
		List<RuntimeCallGraph> list = new ArrayList<RuntimeCallGraph>();
		
		for (RuntimeCallGraph cg : cg_list)
			if (cg.getScenarioName().equals(scenario_name))
				list.add(cg);
		
		return list.toArray(new RuntimeCallGraph[0]);
	}

}
