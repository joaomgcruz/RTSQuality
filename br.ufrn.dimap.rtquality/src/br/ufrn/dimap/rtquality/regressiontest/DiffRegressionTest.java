package br.ufrn.dimap.rtquality.regressiontest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.ufrn.dimap.ttracker.data.MethodData;
import br.ufrn.dimap.ttracker.data.MethodState;
import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.TestCoverageGroup;
import br.ufrn.dimap.ttracker.data.TestCoverageMapping;

public class DiffRegressionTest extends RegressionTestTechnique {
	protected TestCoverageMapping testCoverageMapping;
	protected TestCoverageMapping oldTestCoverageMapping;
	
	public DiffRegressionTest() {
		super("Pythia");
	}
	
	@Override
	public Set<TestCoverageGroup> executeRegression() {
		if(modifiedMethods != null)
			return oldTestCoverageMapping.getModifiedChangedTestsCoverageGroup();
		return new HashSet<TestCoverageGroup>(0);
	}

	@Override
	public Map<MethodState,Map<String,MethodData>> getMethodStatePool() {
		return oldTestCoverageMapping.getMethodStatePool();
	}
	
	@Override
	public void setConfiguration(Object configuration[]) throws Exception {
		if(configuration.length != 1)
			throw new Exception("Número de parâmetros diferente de 1");
		if(configuration[0] instanceof TestCoverageMapping)
			oldTestCoverageMapping = (TestCoverageMapping) configuration[0];
	}

	@Override
	public Object[] getConfiguration() throws Exception {
		Object objs[] = {oldTestCoverageMapping};
		return objs;
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
	
	@Override
	public Set<String> setModifiedMethods(Set<String> modifiedMethods) {
		return super.setModifiedMethods(oldTestCoverageMapping.setModifiedMethods(modifiedMethods));
	}

}
