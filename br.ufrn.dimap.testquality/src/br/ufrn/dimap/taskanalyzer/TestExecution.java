package br.ufrn.dimap.taskanalyzer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.ufrn.dimap.testtracker.data.CoveredMethod;

public class TestExecution {
	
	private Integer testId;
	private List<CoveredMethod> coveredMethods;
	private Map<String,String> inputs;
	private Date date;

	public TestExecution(Integer testId, List<CoveredMethod> coveredMethods, Map<String,String> inputs, Date date) {
		this.testId = testId;
		this.coveredMethods = coveredMethods;
		this.inputs = inputs;
		this.date = date;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public List<CoveredMethod> getCoveredMethods() {
		return coveredMethods;
	}

	public void setCoveredMethods(List<CoveredMethod> coveredMethods) {
		this.coveredMethods = coveredMethods;
	}

	public Map<String, String> getInputs() {
		return inputs;
	}

	public void setInputs(Map<String, String> inputs) {
		this.inputs = inputs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
