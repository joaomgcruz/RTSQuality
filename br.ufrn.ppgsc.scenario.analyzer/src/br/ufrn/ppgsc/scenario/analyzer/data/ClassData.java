package br.ufrn.ppgsc.scenario.analyzer.data;

import java.util.List;
import java.util.Set;

public interface ClassData extends AbstractData {

	public ComponentData getDeclaringComponent();

	public void setDeclaringComponent(ComponentData declaringComponent);
	public String getSignature();
	
	public void setSignature(String signature);
	
	public String getClassPackage();
	
	public void setClassPackage(String classPackage);
	
	public ClassData getSuperClass();
	
	public boolean isTest();
	
	public void setTest(boolean test);
	
	public List<ClassData> getChildrenClasses();
	
	public void setSuperClass(ClassData superClass);
	
	public List<MethodData> getDeclaringMethods();
	
	public void inicializarInheritedMethods(int size);
	
	public Set<MethodData> getInheritedMethods();
	
	public List<MethodData> getAllMethods();
	
	public List<ScenarioData> getScenarios();
	
}
