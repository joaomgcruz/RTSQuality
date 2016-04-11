package br.ufrn.dimap.rtquality.regressiontest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import br.ufrn.dimap.ttracker.data.MethodData;
import br.ufrn.dimap.ttracker.data.MethodState;
import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.TestCoverage;
import br.ufrn.dimap.ttracker.data.TestCoverageGroup;

public abstract class RegressionTestTechnique { //TODO: em algumas situações será necessário seaprar novos testes dos testes velhos, dependendo da implementação os testes novos não podem ser incluídos nas medições
	protected String name; 
	protected Revision revision;
	protected IProject iProject;
	protected Set<String> modifiedMethods;
	protected Set<TestCoverage> selectedTestCoverages;
	
	public abstract Set<TestCoverageGroup> executeRegression();
	public abstract void setConfiguration(Object args[]) throws Exception;
	public abstract Object[] getConfiguration() throws Exception;
	public abstract Map<MethodState,Map<String,MethodData>> getMethodStatePool();
	
	public RegressionTestTechnique(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Revision getRevision() {
		return revision;
	}

	public void setRevision(Revision revision) {
		this.revision = revision;
	}
	
	public Set<String> getModifiedMethods() {
		return modifiedMethods;
	}
	
	public Set<String> setModifiedMethods(Set<String> modifiedMethods) {
		this.modifiedMethods = modifiedMethods;
		return this.modifiedMethods;
	}

	public Set<TestCoverage> getSelectedTestCoverages() {
		return selectedTestCoverages;
		
	}
	
}
