package testtracker.tests;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufrn.taskanalyser.framework.ui.TestTrackerBean;

@RunWith(Suite.class)
@SuiteClasses({ br.ufrn.tests.OperacaoAdicaoTeste.class,
	br.ufrn.tests.OperacaoBeanTeste.class,
	br.ufrn.tests.OperacaoDivisaoTeste.class, br.ufrn.tests.OperacaoMultiplicacaoTeste.class,
	br.ufrn.tests.OperacaoSubtracaoTeste.class
})
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
