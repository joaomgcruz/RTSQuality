package br.plugin.doutorado.test;

import java.util.List;

import br.plugin.doutorado.test2.Calc;
import br.plugin.doutorado.test2.IRun;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

public class Main {

	public static void main(String[] args) {
		new Main().teste(null);
	}
	
	@Scenario(name="testing")
	@Security(name="in yahoo")
	public List<Integer> teste(List<Integer> list) {
		int a, b;

		a = b = 10;

		if (a == b) {
			System.out.println(a + b + yahoo("") + yahoo("") + new Calc().somar_i(a, b));
			yahoo("");
		}
		
		return null;
	}

	public float yahoozzz(String a, Float f) {
		return new Calc().somar_f(1F, 2);
	}
	
	public static float yahoo(int a) {
		return new Calc().somar_f(1F, 2);
	}
	
	public static float yahoo(Class<?> i) {
		return new Calc().somar_f(1F, 2);
	}
	
	@Reliability(name="re in teste")
	public static float yahoo(String a) {
		IRun run = new RunImpl();
		run.run();
		return new Calc().somar_f(1F, 2);
	}

}
