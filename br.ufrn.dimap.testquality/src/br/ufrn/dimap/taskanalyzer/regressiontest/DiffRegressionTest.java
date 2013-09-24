package br.ufrn.dimap.taskanalyzer.regressiontest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.ufrn.dimap.testtracker.data.Revision;
import br.ufrn.dimap.testtracker.data.TestCoverage;
import br.ufrn.dimap.testtracker.data.TestCoverageMapping;

public class DiffRegressionTest extends RegressionTestTechnique {
	protected TestCoverageMapping testCoverageMapping;
	protected TestCoverageMapping oldTestCoverageMapping;
	
	public DiffRegressionTest() {
	}
	
	@Override
	public Set<TestCoverage> executeRegression() {
		if(modifiedMethods != null)
			return oldTestCoverageMapping.getTestsCoverageByChangedMethodsSignatures(modifiedMethods);
		return new HashSet<TestCoverage>(0);
	}
	
	@Override
	public Set<String> getCoveredModifiedMethods() {
		if(modifiedMethods != null)
			return oldTestCoverageMapping.getCoveredModifiedMethods(modifiedMethods);
		return new HashSet<String>(0);
	}
	
	@Override
	public void setConfiguration(Object configuration[]) throws Exception {
		if(configuration.length != 1)
			throw new Exception("Número de parâmetros diferente de 1");
		if(configuration[0] instanceof TestCoverageMapping)
			oldTestCoverageMapping = (TestCoverageMapping) configuration[0];
	}
	
	@Override
	public void setRevision(Revision revision) {
		try{
			if(revision.getId() == 1)
				throw new Exception("Não é possível aplicar uma técnica de regressão se não houver ao menos uma revisão anterior deste projeto");
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.revision = revision;
	}

	public TestCoverageMapping getTestCoverageMapping() {
		return testCoverageMapping;
	}
	
	public TestCoverageMapping getOldTestCoverageMapping() {
		return oldTestCoverageMapping;
	}
	
	public void setOldTestCoverageMapping(TestCoverageMapping oldTestCoverageMapping) {
		this.oldTestCoverageMapping = oldTestCoverageMapping;
	}

}
