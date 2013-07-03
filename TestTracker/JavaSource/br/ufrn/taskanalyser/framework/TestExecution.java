package br.ufrn.taskanalyser.framework;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestExecution {
	
	private Integer testId;
	private List<String> coveredMethods;
	private Map<String,String> inputs;
	private Date date;

	public TestExecution(Integer testId, List<String> coveredMethods, Map<String,String> inputs, Date date) {
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

	public List<String> getCoveredMethods() {
		return coveredMethods;
	}

	public void setCoveredMethods(List<String> coveredMethods) {
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
