package br.ufrn.ppgsc.scenario.analyzer.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;

public class ScenarioAnalyzerQuery {
	
	private JDTWALADataStructure data;
	
	public ScenarioAnalyzerQuery(JDTWALADataStructure data) {
		this.data = data;
	}
	
	public ScenarioData[] getScenariosFromMethod(Class<?> declaring_class, String method_name, Class<?>[] parameters) {
		Method method = null;
		
		try {
			method = declaring_class.getDeclaredMethod(method_name, parameters);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return getScenariosFromMethod(method);
	}
	
	/*
	 * TODO Testar...
	 */
	public ScenarioData[] getScenariosFromMethod(Method method) {
		MethodData method_data = data.getMethodDataFromIndex(ScenarioAnalyzerUtil.getStandartMethodSignature(method));
		return getScenariosFromMethod(method_data);
	}
	
	public ScenarioData[] getScenariosFromMethod(MethodData method) {
		return getScenariosFromMethodAsSet(method).toArray(new ScenarioData[0]);
	}
	
	private Set<ScenarioData> getScenariosFromMethodAsSet(MethodData method) {
		Set<ScenarioData> scenarios = new HashSet<ScenarioData>();
		getScenariosFromMethod(scenarios, method, new HashSet<MethodData>());
		return scenarios;
	}
	
	// TODO: testar depois para subcenários
	private void getScenariosFromMethod(Set<ScenarioData> scenarios, MethodData method, Set<MethodData> visited) {
		visited.add(method);
		
		if (method.getScenario() == null) {
			for (MethodData m : method.getMethodParents()) {
				if (!visited.contains(m))
					getScenariosFromMethod(scenarios, m, visited);
			}
		}
		else {
			scenarios.add(method.getScenario());
		}
	}

	public MethodData[] getDirectMethods(String annotation_name) {
		return getDirectMethodsAsList(annotation_name).toArray(new MethodData[0]);
	}
	
	private List<MethodData> getDirectMethodsAsList(String annotation_name) {
		List<MethodData> result = new ArrayList<MethodData>();
		
		for (org.eclipse.jdt.core.dom.Annotation ann : data.getAnnotations(annotation_name)) {
			IMethodBinding method_binding = ((MethodDeclaration) ann.getParent()).resolveBinding();
			MethodData method_data = data.getMethodDataFromIndex(ScenarioAnalyzerUtil.getStandartMethodSignature(method_binding));
			
			/* TODO: Se null, significa que o método é anotado por uma atributo de
			 * qualidade, mas não está presente nos casos de uso existentes.
			 * Minha opção aqui foi não incluir o método, mas poderiamos colocá-lo
			 * instanciando o novo MethodData para ele, mas sem adicioná-lo ao index
			 */
			if (method_data != null)
				result.add(method_data);
		}
		
		return result;
	}
	
	public MethodData[] getIndirectMethods(String annotation_name) {
		Set<MethodData> result = new HashSet<MethodData>();
		List<MethodData> directs = getDirectMethodsAsList(annotation_name);
		
		for (MethodData method : directs)
			getPathToRoot(result, method, new HashSet<MethodData>());
		
		result.removeAll(directs);
		
		return result.toArray(new MethodData[0]);
	}
	
	private void getPathToRoot(Collection<MethodData> result, MethodData method, Set<MethodData> visited) {
		result.add(method);
		visited.add(method);
		
		for (MethodData m : method.getMethodParents())
			if (!visited.contains(m))
				getPathToRoot(result, m, visited);
	}
	
	public ScenarioData[] getScenariosByQATradeOff(Annotation annotation) {
		
		Set<ScenarioData> scenarios = new HashSet<ScenarioData>();
		List<MethodData> methods = getDirectMethodsAsList(annotation.getClass().getSimpleName());
		
		for (MethodData m : methods)
			scenarios.addAll(getScenariosFromMethodAsSet(m));
		
		return scenarios.toArray(new ScenarioData[0]);
	}
	
	public ScenarioData[] getScenariosByQA(String annotation_name) {
		
		Set<ScenarioData> scenarios = new HashSet<ScenarioData>();
		List<MethodData> methods = getDirectMethodsAsList(annotation_name);
		
		for (MethodData m : methods)
			scenarios.addAll(getScenariosFromMethodAsSet(m));
		
		return scenarios.toArray(new ScenarioData[0]);
	}

	public AbstractQAData[] getQAsByScenario(ScenarioData scenario) {
		Map<String, AbstractQAData> result = new HashMap<String, AbstractQAData>();
		
		getQAs(result, scenario.getStartMethod(), new HashSet<MethodData>());
		
		return result.values().toArray(new AbstractQAData[0]);
	}

	public String[] getQATypesByScenario(ScenarioData scenario) {
		Map<String, AbstractQAData> result = new HashMap<String, AbstractQAData>();
		
		getQAs(result, scenario.getStartMethod(), new HashSet<MethodData>());
		
		return result.keySet().toArray(new String[0]);
	}
	
	private void getQAs(Map<String, AbstractQAData> result, MethodData method, Set<MethodData> visited) {
		
		for (AbstractQAData qa : method.getQualityAttributes())
			result.put(qa.getType().getName(), qa);
		
		visited.add(method);
		
		for (MethodData m : method.getMethodInvocations())
			if (!visited.contains(m))
				getQAs(result, m, visited);
	}

	public ScenarioData[] getConflitedScenarios() {
		List<ScenarioData> result = new ArrayList<ScenarioData>();
		
		for (ScenarioData scenario : data.getScenarios())
			if (getQATypesByScenario(scenario).length > 1)
				result.add(scenario);
		
		return result.toArray(new ScenarioData[0]);
	}

	public MethodData[] getMethodQAByScenario(ScenarioData scenario) {
		Set<MethodData> result = new HashSet<MethodData>();
		
		getMethodQAByScenario(result, scenario.getStartMethod(), new HashSet<MethodData>());
		
		return result.toArray(new MethodData[0]);
	}
	
	private void getMethodQAByScenario(Set<MethodData> result, MethodData method, Set<MethodData> visited) {
		if (!method.getQualityAttributes().isEmpty())
			result.add(method);

		visited.add(method);
		
		for (MethodData m : method.getMethodInvocations())
			if (!visited.contains(m))
				getMethodQAByScenario(result, m, visited);
	}
	
}
