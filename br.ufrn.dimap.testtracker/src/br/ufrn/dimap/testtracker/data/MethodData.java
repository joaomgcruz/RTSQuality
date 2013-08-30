/**
 * 
 */
package br.ufrn.dimap.testtracker.data;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author João Guedes
 *
 */
public class MethodData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String signature;
	private SortedSet<TestCoverage> testsCoverage;
	
	public MethodData(String signature) {
		this.signature = signature;
		testsCoverage = new TreeSet<TestCoverage>();
	}
	
	public String getSignature() {
		return signature;
	}

	public Set<TestCoverage> getTestsCoverage() {
		return testsCoverage;
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
		result = prime * result
				+ ((signature == null) ? 0 : signature.hashCode());
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
		MethodData other = (MethodData) obj;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!signature.equals(other.signature))
			return false;
		return true;
	}

}
