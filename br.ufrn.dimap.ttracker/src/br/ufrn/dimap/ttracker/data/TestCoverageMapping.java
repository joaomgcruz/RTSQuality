/**
 * 
 */
package br.ufrn.dimap.ttracker.data;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.ufrn.dimap.ttracker.util.FileUtil;

/**
 * @author João Guedes
 * 
 */
public class TestCoverageMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	private static TestCoverageMapping instance = new TestCoverageMapping();

	private String name;
	private Integer currentRevision;
	private Map<String, MethodData> methodPool;
	private Map<MethodState, Map<String, MethodData>> methodStatePool;
	private Map<Long, TestCoverage> testCoverageBuilding;
	private Map<Integer, TestCoverageGroup> testCoverageGroup;
	private Set<TestCoverage> testCoverages;
	private Integer nextId;
	private Integer nextGroupId;
	private String fileDirectory;
	private boolean building;

	private TestCoverageMapping() {
		this.name = "TestCoverageMapping";
		this.currentRevision = 0;
		this.methodPool = new HashMap<String, MethodData>();
		this.methodStatePool = new HashMap<MethodState, Map<String, MethodData>>(4);
		this.methodStatePool.put(new MethodState(true, true), new HashMap<String, MethodData>());
		this.methodStatePool.put(new MethodState(true, false), new HashMap<String, MethodData>());
		this.methodStatePool.put(new MethodState(false, true), new HashMap<String, MethodData>());
		this.methodStatePool.put(new MethodState(false, false), new HashMap<String, MethodData>());
		this.testCoverageBuilding = new HashMap<Long, TestCoverage>();
		this.testCoverageGroup = new HashMap<Integer, TestCoverageGroup>();
		this.testCoverages = new HashSet<TestCoverage>();
		this.nextId = 1;
		this.nextGroupId = 1;
		this.fileDirectory = "C:/";
		this.building = false;
	}

	public MethodData addCoveredMethod(String methodSignature) {
		MethodData methodData = findOrCreateMethodData(methodSignature);
		if (!methodData.getMethodState().isCovered()) {
			methodStatePool.get(methodData.getMethodState()).remove(methodData.getSignature());
			methodData.getMethodState().setCovered(true);
			methodStatePool.get(methodData.getMethodState()).put(methodData.getSignature(), methodData);
		}
		return methodData;
	}

	public void removeCoveredMethod(MethodData methodData) {
		methodStatePool.get(methodData.getMethodState()).remove(methodData.getSignature());
		methodPool.remove(methodData.getSignature());
	}

	/**
	 * Atualiza o TestCoverageMapping definindo quais métodos sofreram
	 * modificações, excluindo os métodos que ainda não foram implementados (nas
	 * versões iniciais) ou excluindo os métodos que não existem mais (nas
	 * versões finais)
	 * 
	 * @param modifiedMethods
	 * @return Set<String> dos métodos que ainda são válidos
	 */
	public Set<String> setModifiedMethods(Set<String> modifiedMethods) {
		Set<String> validModifiedMethods = new HashSet<String>(modifiedMethods.size());
		for (String methodSignature : modifiedMethods) {
			MethodData methodData = findMethodData(methodSignature);
			if (methodData != null) {
				if(!methodData.getMethodState().isModified()) {
					methodStatePool.get(methodData.getMethodState()).remove(methodData.getSignature());
					methodData.getMethodState().setModified(true);
					if(methodData.getMethodState().isCovered())
						System.out.println("Aqui!");
					methodStatePool.get(methodData.getMethodState()).put(methodData.getSignature(), methodData);
					validModifiedMethods.add(methodSignature);
				}
			}
			else {
				if(methodSignature.contains(".biblioteca."))
					System.out.println("Aqui!");
				System.out.println("O seguinte m�todo n�o existe nesta vers�o: "+methodSignature);
			}
		}
		return validModifiedMethods;
	}

	/**
	 * Remove, da relação de métodos modificados, os métodos que foram excluídos
	 * na versão antiga ou os métodos que foram incluídos na versão nova
	 * 
	 * @param modifiedMethods
	 * @return
	 */
	public Set<String> getValidModifiedMethods(Set<String> modifiedMethods) {
		Set<String> validModifiedMethods = new HashSet<String>(modifiedMethods.size());
		for (String methodSignature : modifiedMethods) {
			MethodData methodData = findMethodData(methodSignature);
			if (methodData != null)
				validModifiedMethods.add(methodSignature);
		}
		return validModifiedMethods;
	}

	/**
	 * Remove na versão antiga todos os métodos que foram excluídos e remove na
	 * versão nova todos os métodos que foram adicionados
	 * 
	 * @param oldTestCoverageMapping
	 * @param newTestCoverageMapping
	 */
	public static void intersection(TestCoverageMapping oldTestCoverageMapping,
			TestCoverageMapping newTestCoverageMapping) {
		Set<MethodData> oldInvalidMethods = new HashSet<MethodData>(oldTestCoverageMapping.getMethodPool().values());
		Set<MethodData> newInvalidMethods = new HashSet<MethodData>(newTestCoverageMapping.getMethodPool().values());
		oldInvalidMethods.removeAll(newTestCoverageMapping.getMethodPool().values());
		newInvalidMethods.removeAll(oldTestCoverageMapping.getMethodPool().values());
		for (MethodData methodData : oldInvalidMethods) {
			if (oldTestCoverageMapping.getMethodPool().remove(methodData.getSignature()) != null)
				oldTestCoverageMapping.getMethodStatePool().get(methodData.getMethodState())
						.remove(methodData.getSignature());
		}
		for (MethodData methodData : newInvalidMethods) {
			if (newTestCoverageMapping.getMethodPool().remove(methodData.getSignature()) != null)
				newTestCoverageMapping.getMethodStatePool().get(methodData.getMethodState())
						.remove(methodData.getSignature());
		}
	}

	/**
	 * Based on the method signature verifies if it is on the Mapping, if true
	 * return a set with all the tests that covered this method, else return an
	 * empty set.
	 * 
	 * @param methodSignature
	 * @return
	 */
	public Set<TestCoverage> getTestsCoverageByCalledMethod(String methodSignature) {
		if (methodPool.containsKey(methodSignature)) {
			return methodPool.get(methodSignature).getTestsCoverage();
		}
		return new HashSet<TestCoverage>(1);
	}

	/**
	 * Based on the set of changed methods signatures verifies if each one is on
	 * the Mapping, if true include their tests coverage on the resultant test
	 * coverage set
	 * 
	 * @return
	 */
	public Set<TestCoverage> getModifiedChangedTestsCoverage() {	
		Set<TestCoverage> testsCoverage = new HashSet<TestCoverage>(0);
		Set<MethodData> modifiedChangedMethods = new HashSet<MethodData>(methodStatePool.get(new MethodState(true, true)).values());
		for (MethodData modifiedChangedMethod : modifiedChangedMethods) {
			testsCoverage.addAll(modifiedChangedMethod.getTestsCoverage());
		}
		return testsCoverage;
	}
	
	public Set<TestCoverageGroup> getModifiedChangedTestsCoverageGroup() {
		Set<TestCoverageGroup> testsCoverageGroups = new HashSet<TestCoverageGroup>(0);
		Set<MethodData> modifiedChangedMethods = new HashSet<MethodData>(methodStatePool.get(new MethodState(true, true)).values());
		for (MethodData modifiedChangedMethod : modifiedChangedMethods) {
			for(Integer group : modifiedChangedMethod.getTestsCoverageGroup())
				testsCoverageGroups.add(testCoverageGroup.get(group));
		}
		return testsCoverageGroups;
	}

	public Set<TestCoverage> getModifiedCoveredMethods(Set<String> modifiedMethods) {
		Set<TestCoverage> testsCoverage = new HashSet<TestCoverage>(0);
		for(String modifiedMethod : modifiedMethods) {
			if(methodPool.containsKey(modifiedMethod))
				testsCoverage.addAll(methodPool.get(modifiedMethod).getTestsCoverage());
		}
		return testsCoverage;
	}

	public Set<TestCoverageGroup> getModifiedCoveredMethodsGroup(Set<String> modifiedMethods) {
		Set<TestCoverageGroup> testsCoverageGroup = new HashSet<TestCoverageGroup>(0);
		for(String modifiedMethod : modifiedMethods) {
			if(methodPool.containsKey(modifiedMethod)) {
				for(Integer group : methodPool.get(modifiedMethod).getTestsCoverageGroup())
					testsCoverageGroup.add(testCoverageGroup.get(group));
			}
		}
		return testsCoverageGroup;
	}

	public Map<String, MethodData> getModifiedNotCoveredMethods(Set<String> modifiedMethods) {
		return methodStatePool.get(new MethodState(true, false));
	}

	public Map<String, MethodData> getNotModifiedCoveredMethods(Set<String> modifiedMethods) {
		return methodStatePool.get(new MethodState(false, true));
	}

	public Map<String, MethodData> getNotModifiedNotCoveredMethods(Set<String> modifiedMethods) {
		return methodStatePool.get(new MethodState(false, false));
	}

	public Set<String> getTestsFullyQuilifiedNames() {
		Set<String> fullyQuilifiedNames = new HashSet<String>(testCoverages.size());
		for (TestCoverage testCoverage : testCoverages)
			fullyQuilifiedNames.add(testCoverage.getTestData().getClassFullName());
		return fullyQuilifiedNames;
	}

	/**
	 * Based on the method signature search on the Mapping for this one, if not
	 * found creates one and return it.
	 * 
	 * @param methodSignature
	 * @return
	 */
	public MethodData findOrCreateMethodData(String methodSignature) {
		if (methodSignature != null) {
			if (methodPool.containsKey(methodSignature)) {
				return methodPool.get(methodSignature);
			} else {
				MethodData methodData = new MethodData(methodSignature);
				methodPool.put(methodData.getSignature(), methodData);
				methodStatePool.get(methodData.getMethodState()).put(methodData.getSignature(), methodData);
				return methodData;
			}
		}
		return null;
	}

	public void printMethodPool() {
		System.out
				.println("\n================================= Printing MethodPool =================================\n");
		for (MethodData methodData : methodPool.values()) {
			System.out.println("\n" + methodData.getSignature());
		}
	}

	public String printAllTestsCoverage() {
		Integer numberOfCoveredMethods = methodStatePool.get(new MethodState(false, true)).values().size()+methodStatePool.get(new MethodState(true, true)).values().size();
		Float porcentagemCovered = new Float(numberOfCoveredMethods*100)/new Float(methodPool.keySet().size());
		Integer numberOfModifiedMethods = methodStatePool.get(new MethodState(true,true)).keySet().size()+methodStatePool.get(new MethodState(true,false)).keySet().size();
		Float porcentagemModified = new Float(numberOfModifiedMethods*100)/new Float(methodPool.keySet().size());
		Float porcentagemModifiedCovered = new Float(methodStatePool.get(new MethodState(true,true)).keySet().size()*100)/new Float(methodPool.keySet().size());
		DecimalFormat df = new DecimalFormat("0.0000");  
		String all = "Revision: "+(currentRevision != 0 ? currentRevision.toString() : "?")+"\n"+"Number of Methods: "+methodPool.keySet().size()+"\n"+"Number of Methods Covered by Tests: "+df.format(porcentagemCovered)+"%\nNumber of Modified Methods: "+df.format(porcentagemModified)+"%\nNumber of Modified Methods Covered by Tests: "+df.format(porcentagemModifiedCovered)+"%\n";
		all += "\n================================= Printing All =================================\n\n";
		List<TestCoverage> list = new ArrayList<TestCoverage>(testCoverages);
		Collections.sort(list);
		for (TestCoverage testCoverage : list) {
			all += testCoverage.getPrint()+"\n";
		}
		System.out.println(all);
		return all;
	}

	public MethodData findMethodData(String methodSignature) {
		if (methodSignature != null && methodPool.containsKey(methodSignature))
			return methodPool.get(methodSignature);
		return null;
	}

	public TestCoverage getOpenedTestCoverage(Long threadId) {
		if (threadId != null && testCoverageBuilding.containsKey(threadId)) {
			return testCoverageBuilding.get(threadId);
		}
		return null;
	}

	private TestCoverage removeOpenedTestCoverage(Long threadId) {
		if (threadId != null && testCoverageBuilding.containsKey(threadId)) {
			return testCoverageBuilding.remove(threadId);
		}
		return null;
	}

	public void finishTestCoverage(Long threadId) {
		TestCoverage testCoverage = removeOpenedTestCoverage(threadId);
		if(!testCoverageGroup.containsKey(seeNextGroupId()))
			testCoverageGroup.put(seeNextGroupId(), new TestCoverageGroup(seeNextGroupId()));
		testCoverageGroup.get(seeNextGroupId()).getTestCoverages().add(testCoverage);
		testCoverages.add(testCoverage);
		for(CoveredMethod coveredMethod : testCoverage.getCoveredMethods())
			coveredMethod.getMethodData().getTestsCoverageGroup().add(seeNextGroupId());
	}

	public void cancelTestCoverage(Long threadId) {
		TestCoverage testCoverage = removeOpenedTestCoverage(threadId);
		if(!testCoverageGroup.containsKey(seeNextGroupId()))
			testCoverageGroup.put(seeNextGroupId(), new TestCoverageGroup(seeNextGroupId()));
		testCoverageGroup.get(seeNextGroupId()).getTestCoverages().add(getFirstAddTestCoverage(testCoverage));
		testCoverage.getTestData().setSignature("");
		for(CoveredMethod coveredMethod : testCoverage.getCoveredMethods()) {
			MethodData methodData = coveredMethod.getMethodData();
			methodData.setTestsCoverage(new HashSet<TestCoverage>(methodData.getTestsCoverage()));
			methodData.getTestsCoverage().remove(testCoverage);
			methodData.getTestsCoverageGroup().add(seeNextGroupId());
		}
	}
	
	private TestCoverage getFirstAddTestCoverage(TestCoverage testCoverage) {
		for(TestCoverage testCoverage2 : testCoverages) {
			if(testCoverage.equals(testCoverage2))
				return testCoverage2;
		}
		return null;
	}

	public void save() {
		XStream xstream = new XStream(new DomDriver());
		String xmlText = xstream.toXML(this);
		FileUtil.saveTextToFile(xmlText, fileDirectory, name, "tcm");
//		FileUtil.saveObjectToFile(this, fileDirectory, name, "tcm");
		playBeep();
	}

	private void playBeep() {
		try {
			AudioClip clip = Applet.newAudioClip(new URL("file:///D:/Joao/workspaces/SIGAALast/br.ufrn.dimap.ttracker/sounds/beep-06.wav"));
			clip.play();
		} catch (MalformedURLException e) {}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TestCoverage> getTestCoverages() {
		return testCoverages;
	}

	public Set<TestCoverageGroup> getTestCoveragesGroup() {
		return new HashSet<TestCoverageGroup>(testCoverageGroup.values());
	}

	public Integer getNextId() {
		return nextId++;
	}

	public Integer seeNextGroupId() {
		return nextGroupId;
	}

	public Integer getNextGroupId() {
		return nextGroupId++;
	}

	public Integer seeNextId() {
		return nextId;
	}

	public static TestCoverageMapping getInstance() {
		return instance;
	}

	public static void setInstance(TestCoverageMapping otherInstance) {
		instance = otherInstance;
		instance.setBuilding(true);
	}

	public Map<String, MethodData> getMethodPool() {
		return methodPool;
	}

	public void setMethodPool(Map<String, MethodData> methodPool) {
		this.methodPool = methodPool;
	}

	public Map<MethodState, Map<String, MethodData>> getMethodStatePool() {
		return methodStatePool;
	}

	public Map<Long, TestCoverage> getTestCoverageBuilding() {
		return testCoverageBuilding;
	}

	public void setTestCoverageBuilding(Map<Long, TestCoverage> testCoverageBuilding) {
		this.testCoverageBuilding = testCoverageBuilding;
	}

	public Integer getCurrentRevision() {
		return currentRevision;
	}

	public void setCurrentRevision(Integer currentRevision) {
		this.currentRevision = currentRevision;
	}

	public String getFileDirectory() {
		return fileDirectory;
	}

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

}
