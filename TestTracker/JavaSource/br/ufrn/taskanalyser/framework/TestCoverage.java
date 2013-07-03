/**
 * 
 */
package br.ufrn.taskanalyser.framework;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author João Guedes
 *
 */
public class TestCoverage implements Comparable<TestCoverage>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idTest;
	private List<Input> inputs;
	private boolean testMethod;
	private LinkedHashSet<MethodData> coveredMethods;
	private Date date;
	
	public TestCoverage(List<Input> inputs, boolean testMethod, String methodSignature) {
		this.idTest = TestCoverageMapping.getInstance().getNextId();
		this.inputs = inputs;
		this.testMethod = testMethod;
		this.coveredMethods = new LinkedHashSet<MethodData>();
		this.date = new Date();
	}

	public Integer getIdTest() {
		return idTest;
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public boolean isTestMethod() {
		return testMethod;
	}

	public void addCoveredMethod(String methodSignature) {
		MethodData methodData = TestCoverageMapping.getInstance().findOrCreateMethodData(methodSignature);
		methodData.getTestsCoverage().add(this);
		coveredMethods.add(methodData);
	}
	
	public LinkedHashSet<MethodData> getCoveredMethods() {
		return coveredMethods;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coveredMethods == null) ? 0 : coveredMethods.hashCode());
		result = prime * result + ((inputs == null) ? 0 : inputs.hashCode());
		result = prime * result + (testMethod ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCoverage other = (TestCoverage) obj;
		if (coveredMethods == null) {
			if (other.coveredMethods != null)
				return false;
		} else{
			if (other.coveredMethods == null)
				return false;
			else{
				if (!coveredMethods.iterator().hasNext()){
					if(other.coveredMethods.iterator().hasNext())
						return false;
				}
				else{
					if(!other.coveredMethods.iterator().hasNext())
						return false;
					else{
						if(!coveredMethods.iterator().next().equals(other.coveredMethods.iterator().next()))
							return false;
					}
				}
			}
		}
		if (inputs == null) {
			if (other.inputs != null)
				return false;
		} else if (!inputs.equals(other.inputs))
			return false;
		if (testMethod != other.testMethod)
			return false;
		return true;
	}

	@Override
	public int compareTo(TestCoverage arg0) {
		TestCoverage other = (TestCoverage) arg0;
		if(this.getIdTest() > other.getIdTest())
			return 1;
		else if(this.getIdTest() < other.getIdTest())
			return -1;
		else
			return 0;
	}

}
