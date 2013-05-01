package test;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Component;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

@Component(name = "cp - AddSubCalculator")
public class AddSubCalculator implements IAddSubCalculator {
	
	@Scenario(name="sub in add")
	public float add(float a, float b) {
		test();
		return a + b;
	}
	
	@Scenario(name="subsub in test")
	@Security(name="sec in test")
	public void test() {
			
	}
	
	public void test2() {
		
	}

	public float sub(float a, float b) {
		try {
			Thread.sleep(2);
			test2();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return a - b;
	}
	
}
