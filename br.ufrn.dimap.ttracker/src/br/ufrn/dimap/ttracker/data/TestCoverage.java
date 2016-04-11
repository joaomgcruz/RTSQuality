/**
 * 
 */
package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author João Guedes
 *
 */
public class TestCoverage implements Comparable<TestCoverage>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer idTest;
	private TestData testData;
	private Set<CoveredMethod> coveredMethods;
	private Date date;
	
	public TestCoverage() {
		this.idTest = 0;
		this.testData = new TestData();
		this.coveredMethods = new HashSet<CoveredMethod>();
		this.date = new Date();
	}
	
	public Integer getIdTest() {
		return idTest;
	}
	
	public void setIdTest(Integer idTest) {
		this.idTest = idTest;
	}

	public TestData getTestData() {
		return testData;
	}

	public void setTestData(TestData testData) {
		this.testData = testData;
	}

	public void addCoveredMethod(String methodSignature, LinkedHashSet<Variable> inputs) {
		MethodData methodData = TestCoverageMapping.getInstance().addCoveredMethod(methodSignature);
		methodData.getTestsCoverage().add(this);
		coveredMethods.add(new CoveredMethod(methodData, null, inputs));
	}
	
	public String getPrint() {
		String inputs = " <- (";
		for(Variable input : this.getTestData().getInputs()){
			inputs += (input.getValue() == null ? "null" : input.getValue().toString()+", ");
		}
		inputs = this.getTestData().getInputs().size() == 0 ? "" : inputs.substring(0,inputs.length()-2)+")";
		String all = "TestCoverage "+this.getIdTest()+": "+this.getTestData().getSignature()+inputs+"\n";
		all += "MethodDatas:\n";
		for (CoveredMethod coveredMethod : this.getCoveredMethods()) {
			String returnString = ((coveredMethod.getTheReturn() == null || coveredMethod.getTheReturn().getValue() == null) ? "" : "("+coveredMethod.getTheReturn().getValue().toString()+") -> ");
			String inputString = " <- (";
			for(Variable input : coveredMethod.getInputs()){
				inputString += (input.getValue() == null ? "null" : input.getValue().toString()+", ");
			}
			inputString = coveredMethod.getInputs().size() == 0 ? "" : inputString.substring(0,inputString.length()-2)+")";
			all += "\t"+returnString+coveredMethod.getMethodData().getSignature()+inputString+"\n";
		}
		all += "\n---------------------------------------------------------------\n\n";
		return all;
	}

	public void updateCoveredMethod(String methodSignature, Variable theReturn) { //TODO: Verificar se ocorrem erros quando na existência de um método recursivo que receba os mesmos parâmetros de entrada que o seu antecessor
		if(theReturn == null)
			return;
		MethodData methodData = TestCoverageMapping.getInstance().findOrCreateMethodData(methodSignature);
		if(methodData.getTestsCoverage().contains(this)){
			CoveredMethod array[] = coveredMethods.toArray(new CoveredMethod[0]);
			for(int i=coveredMethods.size()-1;i>=0;i--) {
				if(array[i].getMethodData().equals(methodData) && array[i].getTheReturn() == null) {
					array[i].setTheReturn(theReturn);
					break;
				}
			}
		}
	}
	
	public Set<CoveredMethod> getCoveredMethods() {
		return coveredMethods;
	}
	
	public void setCoveredMethods(Set<CoveredMethod> coveredMethods) {
		this.coveredMethods = coveredMethods;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int compareTo(TestCoverage arg0) {
		TestCoverage other = (TestCoverage) arg0;
		if(this.equals(other))
			return 0;
		else if(this.getIdTest() > other.getIdTest())
			return 1;
		else
			return -1;
	}
	
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Id: "+idTest+"\n");
		stringBuffer.append("Signature: "+testData.getSignature()+"\n");
		stringBuffer.append("Class Full Name: "+testData.getClassFullName()+"\n");
		stringBuffer.append("Type: "+(testData.isManual() ? "Manual\n" : "Automático\n"));
		stringBuffer.append("Covered Methods:\n");
		for(CoveredMethod coveredMethod : coveredMethods)
			stringBuffer.append("\t"+coveredMethod.toString());
		stringBuffer.append("Date: "+date.toString()+"\n");
		return stringBuffer.toString();
	}
	

	
	public Set<String> methodDataToSet() {
		if(coveredMethods == null)
			return null;
		Set<String> set = new HashSet<String>();
		for(CoveredMethod coveredMethod : coveredMethods)
			set.add(coveredMethod.getMethodData().getSignature());
		return set;
	}
	
	public static Set<TestCoverage> intersection(Set<TestCoverage> A, Set<TestCoverage> B) {
		if(A == null || B == null)
			return new HashSet<TestCoverage>(0);
		Set<TestCoverage> auxB = new HashSet<TestCoverage>(B);
		Set<TestCoverage> intersection = new HashSet<TestCoverage>(A.size()+B.size());
		for (TestCoverage testA : A) {
			TestCoverage aux = null;
			for(TestCoverage testB : auxB) {
				if(testA.equals(testB)) {
					aux = testB;
					intersection.add(aux);
					break;
				}
			}
			if(aux != null)
				auxB.remove(aux);
		}
		return new HashSet<TestCoverage>(intersection);
	}
	
	public static Set<TestCoverageGroup> intersectionGroup(Set<TestCoverageGroup> A, Set<TestCoverageGroup> B) {
		if(A == null || B == null)
			return new HashSet<TestCoverageGroup>(0);
		Set<TestCoverageGroup> auxB = new HashSet<TestCoverageGroup>(B);
		Set<TestCoverageGroup> intersection = new HashSet<TestCoverageGroup>(A.size()+B.size());
		for (TestCoverageGroup testA : A) {
			TestCoverageGroup aux = null;
			for(TestCoverageGroup testB : auxB) {
				if(testA.equals(testB)) {
					aux = testB;
					intersection.add(aux);
					break;
				}
			}
			if(aux != null)
				auxB.remove(aux);
		}
		return new HashSet<TestCoverageGroup>(intersection);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testData == null) ? 0 : testData.hashCode());
		result = prime * result + ((methodDataToSet() == null) ? 0 : methodDataToSet().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCoverage other = (TestCoverage) obj;
		if (testData == null) {
			if (other.testData != null)
				return false;
		} else if (!testData.equals(other.testData))
			return false;
		if (coveredMethods == null) {
			if (other.coveredMethods != null)
				return false;
		} else if (!methodDataToSet().equals(other.methodDataToSet()))
			return false;
		return true;
	}

}
