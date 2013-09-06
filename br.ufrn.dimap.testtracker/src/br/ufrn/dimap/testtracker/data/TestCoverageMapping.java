/**
 * 
 */
package br.ufrn.dimap.testtracker.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import br.ufrn.dimap.testtracker.util.FileUtil;

/**
 * @author João Guedes
 *
 */
public class TestCoverageMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static TestCoverageMapping instance = new TestCoverageMapping();
	
	private String name;
	private Revision currentRevision;
	private Revision oldRevision;
	private Map<String, MethodData> methodPool;
	private Map<Long,TestCoverage> testCoverageBuilding;
	private SortedSet<TestCoverage> testCoverages;
	private Integer nextId;
	private String fileDirectory;
	private Integer testCount;
	
	private TestCoverageMapping() {
		this.name = "TestCoverageMapping";
		this.currentRevision = new Revision();
		this.oldRevision = new Revision();
		this.methodPool = new HashMap<String, MethodData>();
		this.testCoverageBuilding = new HashMap<Long,TestCoverage>();
		this.testCoverages = new TreeSet<TestCoverage>();
		this.nextId = 1;
		this.fileDirectory = "C:/";
		this.testCount = 0;
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
		Set<TestCoverage> testsCoverage = new HashSet<TestCoverage>(0);
		for (String changedMethodSignature : changedMethodsSignatures) {
			if(methodPool.containsKey(changedMethodSignature))
				testsCoverage.addAll(methodPool.get(changedMethodSignature).getTestsCoverage());
		}
		return testsCoverage;
	}
	
	public Set<String> getTestsFullyQuilifiedNames() {
		Set<String> fullyQuilifiedNames = new HashSet<String>(testCoverages.size());
		for(TestCoverage testCoverage : testCoverages)
			fullyQuilifiedNames.add(testCoverage.getTestData().getClassFullName());
		return fullyQuilifiedNames;
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
		testCoverages.add(removeOpenedTestCoverage(threadId));
		testCount++;
	}
	
	public void save(){
		FileUtil.saveObjectToFile(getInstance(), fileDirectory, name, ".tcm");
	}
	
	public void combineWith(TestCoverageMapping other) throws Exception { //TODO: Traduzir as mensagens das exceções para o inglês
		//TODO: Criar uma nova Exception específica para estas situações
		//TODO: lembrar-se do setFileDirectory para a pasta lib na raiz do projeto 
		if(!currentRevision.equals(other.getCurrentRevision()))
			throw new Exception("A currentRevision deveria ser igual em todos os testCoverageMappings");
		if(!oldRevision.equals(other.getOldRevision()))
			throw new Exception("A oldRevision deveria ser igual em todos os testCoverageMappings");
		for (String signature : other.getMethodPool().keySet()) {
			if(methodPool.containsKey(signature))
				methodPool.get(signature).combineWith(other.getMethodPool().get(signature));
			else
				methodPool.put(signature,other.getMethodPool().get(signature));
		}
		if(!this.testCoverageBuilding.isEmpty())
			throw new Exception("A lista testCoverageBuilding deveria estar vazia!");
		Integer testCoverageSize = testCoverages.size();
		for(TestCoverage otherTestCoverage : other.getTestCoverages()) {
			if(!testCoverages.contains(otherTestCoverage)) {
				otherTestCoverage.setIdTest(otherTestCoverage.getIdTest()+testCoverageSize);
				testCoverages.add(otherTestCoverage);
			}
		}
		nextId += other.seeNextId()-1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Long,TestCoverage> getTestsCoverageBuilding() {
		return testCoverageBuilding;
	}
	
	public Set<TestCoverage> getTestCoverages() {
		return testCoverages;
	}

	public Integer getNextId() {
		return nextId++;
	}

	public Integer seeNextId() {
		return nextId;
	}

	public static TestCoverageMapping getInstance(){
		return instance;
	}

	public Map<String, MethodData> getMethodPool() {
		return methodPool;
	}

	public Revision getCurrentRevision() {
		return currentRevision;
	}

	public void setCurrentRevision(Revision currentRevision) {
		if(currentRevision.getId() > this.currentRevision.getId()){
			this.oldRevision = this.currentRevision;
			this.currentRevision = currentRevision;
		}
	}

	public Revision getOldRevision() {
		return oldRevision;
	}

	public String getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public Integer getTestCount() {
		return testCount;
	}

}
