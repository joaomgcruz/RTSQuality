package br.ufrn.dimap.taskanalyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrn.testtracker.data.CoveredMethod;
import br.ufrn.testtracker.data.Input;
import br.ufrn.testtracker.data.MethodData;
import br.ufrn.testtracker.data.TestCoverage;

public class Selection {

	private List<TestExecution> selection;
	private String revision;
	
	public Selection(Integer size, String revision) {
		selection = new ArrayList<TestExecution>(size);
		this.revision = revision;
	}
	
	public void addTestCoverage(TestCoverage testCoverage) {
		List<CoveredMethod> coveredMethods = new ArrayList<CoveredMethod>(testCoverage.getCoveredMethods().size());
		for(CoveredMethod coveredMethod : testCoverage.getCoveredMethods()){
			coveredMethods.add(coveredMethod);
		}
		Map<String,String> inputs = new HashMap<String,String>(testCoverage.getInputs().size());
		for(Input input : testCoverage.getInputs()){
			inputs.put(input.getName(), input.getValue().toString());
		}
		TestExecution testExecution = new TestExecution(testCoverage.getIdTest(), coveredMethods, inputs, testCoverage.getDate());
		selection.add(testExecution);
	}

	public List<TestExecution> getSelection() {
		return selection;
	}

	public void setSelection(List<TestExecution> selection) {
		this.selection = selection;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

}
