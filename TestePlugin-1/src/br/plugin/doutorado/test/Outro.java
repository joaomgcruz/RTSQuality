package br.plugin.doutorado.test;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Component;


@Component(name="cOutro")
public class Outro {
	
	@Security(name="sec run")
	public void run() {
		
	}
	
}
