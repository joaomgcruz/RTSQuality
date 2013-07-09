package testtracker.tests;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ufrn.taskanalyser.framework.TestCoverageMapping;
import br.ufrn.taskanalyser.framework.ui.TestTrackerBean;
import br.ufrn.tests.OperacaoBeanTeste;

@RunWith(Suite.class)
@SuiteClasses({ OperacaoBeanTeste.class })
public class SelectedTests {
	@ClassRule
	public static ExternalResource externalResource = new ExternalResource() {
		@Override
		protected void before() {
			TestTrackerBean testTrackerBean = new TestTrackerBean();
			TestCoverageMapping allTestsOldExecution = testTrackerBean.getOldTestCoverageMapping();
			if(allTestsOldExecution != null){
				
			}
		}
		@Override
		protected void after(){
			TestTrackerBean testTrackerBean = new TestTrackerBean();
			String className = getClass().getName(); 
			testTrackerBean.saveWithName(className.lastIndexOf('$') != -1 ? className.substring(0, className.lastIndexOf('$')) : className);
		}
	};
}
