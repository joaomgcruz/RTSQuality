package br.ufrn.dimap.taskanalyzer.regressiontest;

import java.util.Set;

import org.eclipse.core.resources.IProject;

import br.ufrn.dimap.testtracker.data.Revision;
import br.ufrn.dimap.testtracker.data.TestCoverage;

public abstract class RegressionTestTechnique { //TODO: em algumas situações será necessário seaprar novos testes dos testes velhos, dependendo da implementação os testes novos não podem ser incluídos nas medições
	protected Revision revision;
	protected IProject iProject;
	protected Set<String> modifiedMethods;
	protected Set<TestCoverage> selectedTestCoverages;
	
	public abstract Set<TestCoverage> executeRegression();
	public abstract void setConfiguration(Object args[]) throws Exception;

	public Revision getRevision() {
		return revision;
	}

	public void setRevision(Revision revision) {
		this.revision = revision;
	}
	
	public IProject getIProject() {
		return iProject;
	}

	public void setIProject(IProject iProject) {
		this.iProject = iProject;
	}

	public Set<String> getModifiedMethods() {
		return modifiedMethods;
	}
	
	public void setModifiedMethods(Set<String> modifiedMethods) {
		this.modifiedMethods = modifiedMethods;
	}

	public Set<TestCoverage> getSelectedTestCoverages() {
		return selectedTestCoverages;
	}
	
}
