/**
 * 
 */
package br.ufrn.dimap.testtracker.data;

import java.io.Serializable;
import java.util.Collection;
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
	
	private Integer idTest; //TODO: Verificar se os IDs estão coerentes ou se estão sendo repetidos ou gerados de forma desordenada. Se sim descobrir o por quê e corrigir o problema.
	private TestData testData;
	private LinkedHashSet<CoveredMethod> coveredMethods;
	private Date date;
	
	public TestCoverage() {
		this.idTest = TestCoverageMapping.getInstance().getNextId();
		this.testData = new TestData();
		this.coveredMethods = new LinkedHashSet<CoveredMethod>();
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

	public void addCoveredMethod(String methodSignature, LinkedHashSet<Input> inputs) {
		MethodData methodData = TestCoverageMapping.getInstance().findOrCreateMethodData(methodSignature);
		methodData.getTestsCoverage().add(this);
		coveredMethods.add(new CoveredMethod(methodData, inputs));
	}
	
	public LinkedHashSet<CoveredMethod> getCoveredMethods() {
		return coveredMethods;
	}

	public Date getDate() {
		return date;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testData == null) ? 0 : testData.hashCode());
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
		if (testData == null) {
			if (other.testData != null)
				return false;
		} else if (!testData.equals(other.testData))
			return false;
		return true;
	}

}
