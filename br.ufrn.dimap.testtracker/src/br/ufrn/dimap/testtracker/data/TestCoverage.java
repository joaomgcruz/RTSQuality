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
	
	private Integer idTest;
	private String signature;
	private String classFullName;
	private boolean manual;
	private LinkedHashSet<CoveredMethod> coveredMethods;
	private Date date;
	
	public TestCoverage() {
		this.idTest = TestCoverageMapping.getInstance().getNextId();
		this.signature = new String();
		this.classFullName = new String();
		this.manual = false;
		this.coveredMethods = new LinkedHashSet<CoveredMethod>();
		this.date = new Date();
	}
	
	public Integer getIdTest() {
		return idTest;
	}
	
	public void setIdTest(Integer idTest) {
		this.idTest = idTest;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getClassFullName() {
		return classFullName;
	}

	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coveredMethods == null) ? 0 : coveredMethods.hashCode());
		result = prime * result + ((signature == null) ? 0 : signature.hashCode());
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
		} else if (!coveredMethods.equals(other.coveredMethods))
			return false;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!signature.equals(other.signature))
			return false;
		return true;
	}
	
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Id: "+idTest+"\n");
		stringBuffer.append("Signature: "+signature+"\n");
		stringBuffer.append("Class Full Name: "+classFullName+"\n");
		stringBuffer.append("Type: "+(manual ? "Manual\n" : "Automático\n"));
		stringBuffer.append("Covered Methods:\n");
		for(CoveredMethod coveredMethod : coveredMethods)
			stringBuffer.append("\t"+coveredMethod.toString());
		stringBuffer.append("Date: "+date.toString()+"\n");
		return stringBuffer.toString();
	}

}
