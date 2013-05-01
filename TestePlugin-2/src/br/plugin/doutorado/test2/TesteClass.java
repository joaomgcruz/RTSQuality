package br.plugin.doutorado.test2;

import br.plugin.doutorado.test.Main;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Component;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

@Component(name="cTesteClass")
public class TesteClass {
	
	@Scenario(name="run of teste class")
	public void run() {
		Main.yahoo("yap");
	}
	
}
