package test;

import java.io.IOException;

import br.ufrn.ppgsc.scenario.analyzer.d.data.DataUtil;
import br.ufrn.ppgsc.scenario.analyzer.d.data.ExecutionPaths;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph;

public class Main {

	public static void main(String[] args) throws IOException {
		IFullCalculator calc = new FullCalculator();

		System.out.println(calc.add(6, 2));
		System.out.println(calc.sub(6, 2));
		System.out.println(calc.mult(6, 2));
		System.out.println(calc.div(6, 2));
		
		for (RuntimeCallGraph cg : ExecutionPaths.getInstance().getAllRuntimeCallGraph()) {
			StringBuilder sb = new StringBuilder();
			DataUtil.printScenarioTree(cg, sb);
			System.out.println(sb);
		}
		
	}

}
