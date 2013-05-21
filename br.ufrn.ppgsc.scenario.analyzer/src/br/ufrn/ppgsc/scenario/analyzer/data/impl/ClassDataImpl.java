package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.ComponentData;

public class ClassDataImpl implements ClassData {

	private String name;
	private ComponentData declaringComponent;
	private String signature;
	private String classPackage;
	private ClassData superClass;
	private boolean test = false;
	private List<ClassData> childrenClasses;
	private List<MethodData> declaringMethods;
	private Set<MethodData> inheritedMethods;
	private List<ScenarioData> scenarios;
	
	public ClassDataImpl(){
		this.childrenClasses = new ArrayList<ClassData>();
		this.declaringMethods = new ArrayList<MethodData>();
		this.inheritedMethods = new HashSet<MethodData>();
		this.scenarios = new ArrayList<ScenarioData>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComponentData getDeclaringComponent() {
		return declaringComponent;
	}

	public void setDeclaringComponent(ComponentData declaringComponent) {
		this.declaringComponent = declaringComponent;
	}
	
	public String getClassPackage() {
		return classPackage;
	}
	
	public void setClassPackage(String classPackage) {
		this.classPackage = classPackage;
	}
	
	public ClassData getSuperClass() {
		return superClass;
	}
	
	public void setSuperClass(ClassData superClass) {
		this.superClass = superClass;
	}
	
	public boolean isTest() {
		return test;
	}
	
	public void setTest(boolean test) {
		this.test = test;
	}
	
	public List<ClassData> getChildrenClasses() {
		return childrenClasses;
	}
	
	public List<MethodData> getDeclaringMethods() {
		return declaringMethods;
	}
	
	public List<ScenarioData> getScenarios(){
		return scenarios;
	}
	
	public void inicializarInheritedMethods(int size){
		inheritedMethods = new HashSet<MethodData>(size);
	}
	
	public Set<MethodData> getInheritedMethods() {
		return inheritedMethods;
	}
	
	public List<MethodData> getAllMethods() {
		List<MethodData> allMethods = new ArrayList<MethodData>();
		allMethods.addAll(declaringMethods);
		allMethods.addAll(inheritedMethods);
		return allMethods;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}

}
