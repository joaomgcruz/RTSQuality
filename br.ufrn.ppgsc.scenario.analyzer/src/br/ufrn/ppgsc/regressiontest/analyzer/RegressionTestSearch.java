/**
 * 
 */
package br.ufrn.ppgsc.regressiontest.analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import br.ufrn.ppgsc.scenario.analyzer.data.AbstractData;
import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

/**
 * @author João Guedes
 *
 */
public class RegressionTestSearch {
	
	private static Map<ClassData,Set<MethodData>> testSelection = new HashMap<ClassData,Set<MethodData>>();
	
	private static Set<MethodData> definingHeritageImpact(Set<AbstractData> changes){
		Set<MethodData> heritageImpact = new HashSet<MethodData>();
		for (AbstractData abstractData : changes) {
			if(abstractData != null){
				if(abstractData instanceof ScenarioData){
					ScenarioData scenarioData = (ScenarioData) abstractData;
					Set<AbstractData> scenarioCoverage = new HashSet<AbstractData>(definingScenarioCoverage(scenarioData));
					scenarioCoverage.add(scenarioData.getStartMethod());
					heritageImpact.addAll(definingHeritageImpact(scenarioCoverage));
				}
				else if(abstractData instanceof ClassData){
					ClassData classData = (ClassData) abstractData;
					heritageImpact.addAll(definingHeritageImpact(new HashSet<AbstractData>(classData.getDeclaringMethods())));
				}
				else if(abstractData instanceof MethodData){
					MethodData methodData = (MethodData) abstractData;
					heritageImpact.add(methodData); //Inclui o próprio método
					if(!methodData.isInit()){
						heritageImpact.addAll(definingParentHeritageImpact(methodData));
						heritageImpact.addAll(definingChildHeritageImpact(methodData));
					}
				}
			}
		}
		return heritageImpact;
	}

	/**
	 * Inclui o método do pai que foi sobrescrito, pois chamar tal método do pai possuindo uma instância do filho acarreta na execução do método do filho.
	 * @param directImpact
	 * @param methodData
	 * @return
	 */
	private static Set<MethodData> definingParentHeritageImpact(MethodData methodData) {
		Set<MethodData> parentHeritageImpact = new HashSet<MethodData>();
		for(MethodData superMethodData : methodData.getDeclaringClass().getSuperClass().getAllMethods()){
			if(superMethodData.getPartialSignature().equals(methodData.getPartialSignature())){
				parentHeritageImpact.add(superMethodData);
				parentHeritageImpact.addAll(definingParentHeritageImpact(superMethodData));
				break;
			}
		}
		return parentHeritageImpact;
	}
	
	/**
	 * Inclui os métodos herdado pelos filhos que não foram sobrescritos, pois a modificação do método do pai afetará tais filhos.
	 * @param methodData
	 * @return
	 */
	private static Set<MethodData> definingChildHeritageImpact(MethodData methodData){
		Set<MethodData> childHeritageImpact = new HashSet<MethodData>();
		for(ClassData childClassData : methodData.getDeclaringClass().getChildrenClasses()){
			for(MethodData childMethodData : childClassData.getInheritedMethods()){
				if(childMethodData.getPartialSignature().equals(methodData.getPartialSignature())){
					childHeritageImpact.add(childMethodData);
					childHeritageImpact.addAll(definingChildHeritageImpact(childMethodData));
					break;
				}
			}
		}
		return childHeritageImpact;
	}
	
	private static Set<MethodData> definingIndirectImpact(Set<MethodData> directImpact){
		Set<MethodData> indirectImpact = new HashSet<MethodData>();
		testSelection.clear();
		List<MethodData> notVisited = new ArrayList<MethodData>(directImpact);
		while(!notVisited.isEmpty()){
			MethodData visited = notVisited.remove(0);
			indirectImpact.add(visited);
			if(visited.getDeclaringClass().isTest()){
				if(testSelection.get(visited.getDeclaringClass()) == null)
					testSelection.put(visited.getDeclaringClass(),new HashSet<MethodData>());
				testSelection.get(visited.getDeclaringClass()).add(visited);
			}
			for(MethodData methodData : visited.getMethodParents()){
				if(!indirectImpact.contains(methodData) && !notVisited.contains(methodData))
					notVisited.add(methodData);
			}
		}
		return indirectImpact;
	}
	
	public Set<MethodData> definingScenarioCoverage(List<ScenarioData> scenarios){
		Set<MethodData> startMethods = new HashSet<MethodData>();
		for (ScenarioData scenarioData : scenarios) {
			startMethods.add(scenarioData.getStartMethod()); //TODO: ScenarioData não deveria ter uma lista de startMethods ao invés de um único método?
		}
		return definingMethodCoverage(startMethods);
	}
	
	private static Set<MethodData> definingScenarioCoverage(ScenarioData scenario){
		Set<MethodData> methods = new HashSet<MethodData>();
		methods.add(scenario.getStartMethod()); //TODO: ScenarioData não deveria ter uma lista de startMethods ao invés de um único método?
		return definingMethodCoverage(methods);
	}
	
	private static Set<MethodData> definingMethodCoverage(Set<MethodData> methods){
		Set<MethodData> coverage = new HashSet<MethodData>();
		List<MethodData> notVisited = new ArrayList<MethodData>();
		for (MethodData methodData : methods) {
			for (MethodData methodD : methodData.getMethodInvocations()){
				if(!notVisited.contains(methodD))
					notVisited.add(methodD);
			}
		}
		while(!notVisited.isEmpty()){
			MethodData visited = notVisited.remove(0);
			if(!coverage.contains(visited)){
				coverage.add(visited);
				for(MethodData visitedInvocation : visited.getMethodInvocations()){
					if(!coverage.contains(visitedInvocation) && !notVisited.contains(visitedInvocation))
						notVisited.add(visitedInvocation);
				}
			}
		}
		return coverage;
	}
	
	/**
	 * Define a cobertura de um dado método.
	 * @param method Método a ser analisado.
	 */
	public Set<MethodData> definingMethodCoverage(MethodData method){
		Set<MethodData> coverage = new HashSet<MethodData>();
		List<MethodData> notVisited = new ArrayList<MethodData>(method.getMethodInvocations());
		while(!notVisited.isEmpty()){
			MethodData visited = notVisited.remove(0);
			if(!coverage.contains(visited)){
				coverage.add(visited);
				for(MethodData visitedInvocation : visited.getMethodInvocations()){
					if(!coverage.contains(visitedInvocation) && !notVisited.contains(visitedInvocation))
						notVisited.add(visitedInvocation);
				}
			}
		}
		return coverage;
	}
	
	public Set<MethodData> definingUsefulMethodCoverage(List<MethodData> indirectImpact, List<MethodData> coverage){
		List<MethodData> uselessCoverage = new ArrayList<MethodData>();
		List<MethodData> usefulTestCoverage = new ArrayList<MethodData>();
		Collections.copy(uselessCoverage, coverage);
		Collections.copy(usefulTestCoverage, coverage);
		uselessCoverage.removeAll(indirectImpact);
		usefulTestCoverage.removeAll(uselessCoverage);
		return new HashSet<MethodData>(usefulTestCoverage);
	}
	
	public Set<ScenarioData> definingScenariosCoveredByTests(Set<MethodData> usefulTestCoverage){
		Set<ScenarioData> scenariosCoveredByTests = new HashSet<ScenarioData>();
		for (MethodData methodData : usefulTestCoverage) {
			if(methodData.getScenario() != null)
				scenariosCoveredByTests.add(methodData.getScenario());
		}
		return scenariosCoveredByTests;
	}
	
	/**
	 * Monta uma suite de testes de regressão para junit 3
	 * @param changes
	 * @param iProject
	 */
	public static void definingRegressionTestSuite(Set<AbstractData> changes,IProject iProject){
		File testPackage = new File(iProject.getLocation()+"/src/regressiontest/"); //TODO: Talvez seja necessário recuperar a source folder utilizada, pois nem sempre será src
		File regressionTestSuiteFile = new File(iProject.getLocation()+"/src/regressiontest/RegressionTestSuite.java");
		if(regressionTestSuiteFile.exists())
			regressionTestSuiteFile.delete(); 
		definingIndirectImpact(definingHeritageImpact(changes));
		String regressionTestSuiteText = buildTestSuiteText();
		if(!regressionTestSuiteText.isEmpty()){
			try{
				if(!testPackage.exists())
					testPackage.mkdir();
				BufferedWriter out = new BufferedWriter(new FileWriter(regressionTestSuiteFile));
		        out.write(regressionTestSuiteText);
		        out.close();
		    }catch(Throwable e){
		        e.printStackTrace();
		    }
		}
		else if(testPackage.exists())
			testPackage.delete();
	}

	/**
	 * Gera o corpo da classe java de teste
	 * @return
	 */
	private static String buildTestSuiteText() {
		StringBuffer testSuiteBuffer = new StringBuffer();
		if(testSelection != null && !testSelection.isEmpty()){
			testSuiteBuffer.append("package regressiontest;\n\n");
			testSuiteBuffer.append("import junit.framework.Test;\n");
			testSuiteBuffer.append("import junit.framework.TestSuite;\n\n");
			testSuiteBuffer.append("public class RegressionTestSuite {\n\n");
			testSuiteBuffer.append("\tpublic static Test suite() {\n");
			testSuiteBuffer.append("\t\tTestSuite testSuite = new TestSuite(\"regressiontest.RegressionTestSuite\");\n");
			testSuiteBuffer.append("\t\ttry {\n");
			for(ClassData klass : testSelection.keySet()){
				testSuiteBuffer.append("\t\t\ttestSuite.addTestSuite(Class.forName(\""+ klass.getClassPackage() + "." + klass.getName() +"\"));\n");
			}
			testSuiteBuffer.append("\t\t} catch (ClassNotFoundException e) {\n");
			testSuiteBuffer.append("\t\t\te.printStackTrace();\n");
			testSuiteBuffer.append("\t\t}\n");
			testSuiteBuffer.append("\t\treturn testSuite;\n");
			testSuiteBuffer.append("\t}\n\n");
			testSuiteBuffer.append("}\n");
		}
		return testSuiteBuffer.toString();
	}
	
	public void regressionSearch(Set<AbstractData> changes){
		Set<MethodData> heritageImpact = definingHeritageImpact(changes);
		Set<MethodData> indirectImpact = definingIndirectImpact(heritageImpact);
//		definingRegressionTestSuite(changes,iProject);
		
		Set<MethodData> tests = new HashSet<MethodData>();
		for (Set<MethodData> methodsData : testSelection.values())
			tests.addAll(methodsData);
		Set<MethodData> testsCoverage = definingMethodCoverage(tests);
		Set<MethodData> usefulTestsCoverage = definingUsefulMethodCoverage(new ArrayList<MethodData>(indirectImpact), new ArrayList<MethodData>(testsCoverage));
		Set<ScenarioData> scenariosCoveredByTests = definingScenariosCoveredByTests(usefulTestsCoverage);
	}
	
	public Map<ScenarioData,Set<MethodData>> scenariosTests(){
		Map<ScenarioData,Set<MethodData>> scenarioCoveredTests = new HashMap<ScenarioData,Set<MethodData>>();
		//TODO: Inverter a estrutura de cenários cobertos pelos testes ou construir a estrutura já invertida (a última opção é a mais recomendada)
		return scenarioCoveredTests;
	}
	
}
