package br.ufrn.ppgsc.scenario.analyzer.data.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.ipa.callgraph.CGNode;

// TODO Adicionar signature para este elemento como key
public class MethodDataImpl implements MethodData {

	/* TODO Está referência será setada apenas para o startMethod.
	 *  Depois podemos considerar uma classe especializada desta
	 *  para tratar o primeiro method do cenário, evitando que
	 *  o atributo seja null na maioria dos casos
	 */
	private ScenarioData scenario;
	
	/* TODO Depois preciso ver uma forma de não colocar isso
	 * em todos os métodos.
	 * Esta key representa a versão da estrutura do grafo que
	 * deve ser usada para buscar o método
	 */
	private String version;
	
	private String name;
	private String signature;
	private ClassData declaringClass;
	private List<AbstractQAData> qualityAttributes;

	public MethodDataImpl() {
		qualityAttributes = new ArrayList<AbstractQAData>();
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public ClassData getDeclaringClass() {
		return declaringClass;
	}

	public void setDeclaringClass(ClassData declaringClass) {
		this.declaringClass = declaringClass;
	}

	public ScenarioData getScenario() {
		return scenario;
	}

	public void setScenario(ScenarioData scenario) {
		this.scenario = scenario;
	}

	public List<MethodData> getMethodInvocations() {
		List<MethodData> result = new ArrayList<MethodData>();
		JDTWALADataStructure data = ScenarioAnalyzerUtil.getDataStructureInstance(version);
		CGNode node = data.getMethodNodeFromIndex(signature);
		
		for (Iterator<CallSiteReference> it = node.iterateCallSites(); it.hasNext();) {
			for (CGNode child : data.getCallGraph().getPossibleTargets(node, it.next())) {
				if (child.getMethod().getDeclaringClass().getClassLoader().getReference().equals(JavaSourceAnalysisScope.SOURCE))
					result.add(data.getMethodDataFromIndex(
						ScenarioAnalyzerUtil.getStandartMethodSignature(child.getMethod())));
			}
		}
		
		return result;
	}

	public List<MethodData> getMethodParents() {
		List<MethodData> result = new ArrayList<MethodData>();
		JDTWALADataStructure data = ScenarioAnalyzerUtil.getDataStructureInstance(version);
		CGNode node = data.getMethodNodeFromIndex(signature);
		
		for (Iterator<CGNode> itr = data.getCallGraph().getPredNodes(node); itr.hasNext();) {
			CGNode parent = itr.next();
			
			if (parent.getMethod().getDeclaringClass().getClassLoader().getReference().equals(JavaSourceAnalysisScope.SOURCE))
				result.add(data.getMethodDataFromIndex(
					ScenarioAnalyzerUtil.getStandartMethodSignature(parent.getMethod())));
		}
		
		return result;
	}

	public List<AbstractQAData> getQualityAttributes() {
		return qualityAttributes;
	}

	public void setQualityAttributes(List<AbstractQAData> qualityAttributes) {
		this.qualityAttributes = qualityAttributes;
	}
	
	@Override
	public boolean equals(Object m) {
		return this.getSignature().equals(((MethodData) m).getSignature());
	}

}
