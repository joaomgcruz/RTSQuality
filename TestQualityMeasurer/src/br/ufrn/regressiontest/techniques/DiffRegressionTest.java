package br.ufrn.regressiontest.techniques;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.tmatesoft.svn.core.SVNException;

import br.ufrn.taskanalyser.framework.miner.HistoryMiner;
import br.ufrn.taskanalyser.framework.miner.SVNConfig;
import br.ufrn.testtracker.data.CoveredMethod;
import br.ufrn.testtracker.data.MethodData;
import br.ufrn.testtracker.data.TestCoverage;
import br.ufrn.testtracker.data.TestCoverageMapping;

public class DiffRegressionTest extends RegressionTestTechnique {
	private SVNConfig sVNConfig;

	public DiffRegressionTest(TestCoverageMapping testCoverageMapping) {
		setTestCoverageMapping(testCoverageMapping);
	}
	
	private TestCoverageMapping getOldTestCoverageMapping(long currentRevision) {
		return null; //TODO: recuperar arquivo de teste da revisão atual ou de alguma anterior, nunca uma futura. Deve retornar null caso não haja.
	}

	public Set<TestCoverage> getTestsCoverages(Integer currentRevision) {
		Set<TestCoverage> testsCoverages = new HashSet<TestCoverage>(1);
		try {
			TestCoverageMapping oldTestCoverageMapping = getOldTestCoverageMapping(currentRevision);
			if(testCoverageMapping != null){
				Integer oldRevision = new Integer(oldTestCoverageMapping.getCurrentRevision() == currentRevision ? oldTestCoverageMapping.getOldRevision() : oldTestCoverageMapping.getCurrentRevision());
				Set<String> modifiedMethods = HistoryMiner.minerModifications(sVNConfig,oldRevision,currentRevision);
				testsCoverages = selectTestsByModifiedMethods(modifiedMethods);
			}
			else{
				executeAllTests(currentRevision);
				testsCoverages = TestCoverageMapping.getInstance().getTestsCoverage();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return testsCoverages;
	}
				
	public TestCoverageMapping executeTests(Set<String> modifiedMethods, Integer currentRevision) {
		Set<TestCoverage> testsCoverages = testCoverageMapping.getTestsCoverageByChangedMethodsSignatures(modifiedMethods);
		Set<String> testClassSignatures = new HashSet<String>(testsCoverages.size());
		for (TestCoverage testCoverage : testsCoverages) {
			if(!testCoverage.isManual()){
				Iterator<CoveredMethod> iterator = testCoverage.getCoveredMethods().iterator();
				if(iterator.hasNext()){
					String testClassSignature = iterator.next().getMethodData().getSignature();
					int returnType = testClassSignature.indexOf(" ");
					int classDot = testClassSignature.substring(0, testClassSignature.indexOf("(")).lastIndexOf(".");
					testClassSignatures.add(testClassSignature.substring(returnType == -1 ? 0 : returnType, classDot));
					break;
				}
			}
		}
		TestFactory.getInstance().setTestName(getClass().getName());
		TestFactory.getInstance().setTestClassSignatures(testClassSignatures);
		TestFactory.getInstance().updateAllTests();
		TestFactory.getInstance().runTests();
		
		TestCoverageMapping.getInstance().setCurrentRevision(currentRevision);
		return TestCoverageMapping.getInstance();
	}
	
	private void executeAllTests(Integer currentRevision){
		try {
			HistoryMiner.checkoutProject(sVNConfig,currentRevision);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		//TODO: Baixar a versão da respectiva revisão
		//TODO: Executar todos os testes salvando o mapping no local de destino específico
		TestCoverageMapping.getInstance().setCurrentRevision(currentRevision);
	}

	@Override
	public TestCoverageMapping getTestCoverageMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTestCoverageMapping(TestCoverageMapping testCoverageMapping) {
		// TODO Auto-generated method stub

	}

	public SVNConfig getsVNConfig() {
		return sVNConfig;
	}

	public void setsVNConfig(SVNConfig sVNConfig) {
		this.sVNConfig = sVNConfig;
	}

	@Override
	public Set<TestCoverage> selectTestsByModifiedMethods(Set<String> modifiedMethods) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestCoverageMapping executeTests(Set<String> modifiedMethods) {
		// TODO Auto-generated method stub
		return null;
	}

}
