package br.ufrn.tests;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufrn.taskanalyser.framework.ui.TestTrackerBean;

@RunWith(Suite.class)
@SuiteClasses({ OperacaoAdicaoTeste.class, OperacaoBeanTeste.class,
		OperacaoDivisaoTeste.class, OperacaoMultiplicacaoTeste.class,
		OperacaoSubtracaoTeste.class })
public class AllTests {
	
	@ClassRule
	public static ExternalResource externalResource = new ExternalResource() {
		@Override
		protected void after(){
			TestTrackerBean testTrackerBean = new TestTrackerBean();
			String className = getClass().getName(); 
			testTrackerBean.saveWithName(className.lastIndexOf('$') != -1 ? className.substring(0, className.lastIndexOf('$')) : className);
		}
	};
}
