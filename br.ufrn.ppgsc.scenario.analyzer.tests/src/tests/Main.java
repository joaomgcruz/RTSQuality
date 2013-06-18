package tests;

import java.io.IOException;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.d.data.DataUtil;
import br.ufrn.ppgsc.scenario.analyzer.d.data.ExecutionPaths;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph;

public class Main {
	
	@Robustness(name="robustness_convert")
	public int convertToInt(String str) {
		try {
			int a = 1 / 1;
		}
		catch (ArithmeticException e) {
			
		}
		
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}
	
	@Robustness(name="robstness_divide")
	public int divide(int a, int b) {
		return a / b;
	}

	@Scenario(name="main")
	public static void main(String args[]) throws IOException {
		Main m = new Main();
		
		System.out.println("mais uma linha");
		System.out.println(m.convertToInt("ss10felipe"));
		
		try {
			System.out.println(m.divide(1, 0));
		} catch (ArithmeticException e) {
			System.out.println("main também pega");
		}
		
		System.out.println(m.convertToInt("10"));
		
		System.out.println("--------------------------------------------------");
		
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
