/**
 * 
 */
package br.ufrn.taskanalyser.framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author João Guedes
 *
 */
public class TestCoverageMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static TestCoverageMapping instance = new TestCoverageMapping();
	
	private Map<String, MethodData> methodPool = new HashMap<String, MethodData>();
	private Map<Long,TestCoverage> testCoverageBuilding = new HashMap<Long,TestCoverage>();
	private Map<Long,List<Input>> testCoverageInputs = new HashMap<Long,List<Input>>();
	private SortedSet<TestCoverage> testCoverage = new TreeSet<TestCoverage>();
	private Integer nextId = 1;
	
	private TestCoverageMapping() {
	}

	/**
	 * Based on the method signature verifies if it is on the Mapping, if true return a set with all the tests that covered this method, else return an empty set.
	 * @param methodSignature
	 * @return
	 */
	public Set<TestCoverage> getTestsCoverageByCalledMethod(String methodSignature){
		if(methodPool.containsKey(methodSignature)){
			return methodPool.get(methodSignature).getTestsCoverage();
		}
		return new HashSet<TestCoverage>(1);
	}
	
	/**
	 * Based on the set of changed methods signatures verifies if each one is on the Mapping, if true include their tests coverage on the resultant test coverage set
	 * @param changedMethodsSignatures
	 * @return
	 */
	public Set<TestCoverage> getTestsCoverageByChangedMethodsSignatures(Set<String> changedMethodsSignatures){
		Set<TestCoverage> testsCoverage = new HashSet<TestCoverage>(1);
		for (String changedMethodSignature : changedMethodsSignatures) {
			if(methodPool.containsKey(changedMethodSignature))
				testsCoverage.addAll(methodPool.get(changedMethodSignature).getTestsCoverage());
		}
		return testsCoverage;
	}
	
	/**
	 * Based on the method signature search on the Mapping for this one, if not found creates one and return it.
	 * @param methodSignature
	 * @return
	 */
	public MethodData findOrCreateMethodData(String methodSignature){
		if(methodSignature != null){
			if(methodPool.containsKey(methodSignature)){
				return methodPool.get(methodSignature);
			}else{
				MethodData methodData = new MethodData(methodSignature);
				methodPool.put(methodSignature, methodData);
				return methodData;
			}
		}
		return null;
	}

	public TestCoverage getOpenedTestCoverage(Long threadId) {
		if(threadId != null && testCoverageBuilding.containsKey(threadId)){
			return testCoverageBuilding.get(threadId);
		}
		return null;
	}
	
	private TestCoverage removeOpenedTestCoverage(Long threadId) {
		if(threadId != null && testCoverageBuilding.containsKey(threadId)){
			return testCoverageBuilding.remove(threadId);
		}
		return null;
	}
	
	public void finishTestCoverage(Long threadId){
		testCoverage.add(removeOpenedTestCoverage(threadId));
	}

	public Map<Long,List<Input>> getTestsCoverageInputs() {
		return testCoverageInputs;
	}
	
	public List<Input> getOpenedTestCoverageInputs(Long threadId) {
		if(threadId != null && testCoverageInputs.containsKey(threadId)){
			return testCoverageInputs.get(threadId);
		}
		return null;
	}
	
	public List<Input> removeOpenedTestCoverageInputs(Long threadId) {
		if(threadId != null && testCoverageInputs.containsKey(threadId)){
			return testCoverageInputs.remove(threadId);
		}
		return null;
	}
	
	public Map<Long,TestCoverage> getTestsCoverageBuilding() {
		return testCoverageBuilding;
	}
	
	public Set<TestCoverage> getTestsCoverage() {
		return testCoverage;
	}

	public Integer getNextId() {
		return nextId++;
	}
	
	public static TestCoverageMapping getInstance(){
		return instance;
	}

}
