package br.ufrn.dimap.taskanalyzer.regressiontest;

import java.util.Set;

import br.ufrn.dimap.testtracker.data.TestCoverage;
import br.ufrn.dimap.testtracker.data.TestCoverageMapping;

public abstract class RegressionTestTechnique {
	protected TestCoverageMapping testCoverageMapping;
	
	public abstract Set<TestCoverage> selectTestsByModifiedMethods(Set<String> modifiedMethods);
	public abstract TestCoverageMapping executeTests(Set<String> modifiedMethods); 

	protected abstract TestCoverageMapping getTestCoverageMapping();
	protected abstract void setTestCoverageMapping(TestCoverageMapping testCoverageMapping);
}
