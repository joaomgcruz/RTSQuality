/**
 * 
 */
package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author João Guedes
 *
 */
public class MethodData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String signature;
	private MethodState methodState;
	private Set<TestCoverage> testsCoverage;
	private Set<Integer> testsCoverageGroup;
	
	public MethodData(String signature) {
		this.signature = signature;
		this.methodState = new MethodState();
		testsCoverage = new HashSet<TestCoverage>();
		testsCoverageGroup = new HashSet<Integer>();
	}
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public MethodState getMethodState() {
		return methodState;
	}

	public Set<TestCoverage> getTestsCoverage() {
		return testsCoverage;
	}

	public void setTestsCoverage(Set<TestCoverage> testsCoverage) {
		this.testsCoverage = testsCoverage;
	}
	
	public Set<Integer> getTestsCoverageGroup() {
		return testsCoverageGroup;
	}

	public void setTestsCoverageGroup(Set<Integer> testsCoverageGroup) {
		this.testsCoverageGroup = testsCoverageGroup;
	}

	public void combineWith(MethodData other) throws Exception {  //TODO: Traduzir as mensagens das exceções para o inglês
		//TODO: Criar uma exception própria para esta situação
		if(!signature.equals(other.getSignature()))
			throw new Exception("As signatures dos MethodDatas não são iguais!");
		for(TestCoverage otherTestCoverage : other.getTestsCoverage())
			testsCoverage.add(otherTestCoverage);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((signature == null) ? 0 : signature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodData other = (MethodData) obj;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!signature.equals(other.signature))
			return false;
		return true;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((signature == null) ? 0 : signature.hashCode());
//		return result;
//	}


}
