package br.plugin.doutorado.test2;

import br.plugin.doutorado.test.Main;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Component;

@Component(name="C_Calc")
public class Calc {
	
	public void C() {
		B();
	}
	
	@Performance(name="in A()")
	public void A() {
		C();
	}
	
	@Security(name="secB")
	@Performance(name="perfB")
	public void B() {
		Main.yahoo("");
		B();
		A();
	}
	
	public int somar_i(int a, int b) {
//		A();
		B();
		return a + b;
	}
	
	public int somar_f(float a, int b) {
		return (int) (a + b);
	}
	
}
