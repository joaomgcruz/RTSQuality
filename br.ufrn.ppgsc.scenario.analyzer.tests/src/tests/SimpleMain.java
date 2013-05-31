package tests;

import org.junit.Test;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

public class SimpleMain {

	@Test
	@Scenario(name = "main scenario")
	@Performance(name = "main scenario performance", limit_time = 1000)
	@Security(name = "main scenario security")
	public void testeQualquer() {
		System.out.println("CORPO DO MÉTODO PRINCIPAL");
		privateMethod();
	}

	private void privateMethod() {
		System.out.println("PRIVATE METHOD");
	}
	
//	@Test
//	@Scenario(name = "main scenario 2")
//	@Robustness(name = "main scenario 2 robustness")
//	public void testeQualquer2() {
//		System.out.println("CORPO DO MÉTODO PRINCIPAL 2");
//	}
//	
//	@Test
//	@Scenario(name = "main scenario 3")
//	@Security(name = "main scenario 3 security")
//	public void testeQualquer3() {
//		System.out.println("CORPO DO MÉTODO PRINCIPAL 3");
//	}
//	
//	@Test
//	@Scenario(name = "main scenario 4")
//	@Reliability(name = "main scenario 4 reliability", failure_rate = 5)
//	public void testeQualquer4() {
//		System.out.println("CORPO DO MÉTODO PRINCIPAL 4");
//	}
	
}
