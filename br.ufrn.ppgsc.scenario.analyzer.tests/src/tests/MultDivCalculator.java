package tests;


import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Component;

@Component(name="cp - MultDivCalculator")
public class MultDivCalculator implements IMultDivCalculator {

	public float mult(float a, float b) {
		return a * b;
	}

	@Reliability(name="reli", failure_rate=0.5)
	public float div(float a, float b) {
		return a / b;
	}

}
