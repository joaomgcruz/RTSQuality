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
	private String partialSignature;
	private ClassData declaringClass;
	private List<AbstractQAData> qualityAttributes;
	private List<MethodData> methodInvocations;
	private List<MethodData> methodParents;
	private boolean init;

	public MethodDataImpl() {
		qualityAttributes = new ArrayList<AbstractQAData>();
		methodInvocations = new ArrayList<MethodData>();
		methodParents = new ArrayList<MethodData>();
		init = false;
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

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public ScenarioData getScenario() {
		return scenario;
	}

	public void setScenario(ScenarioData scenario) {
		this.scenario = scenario;
	}

	public List<MethodData> getMethodInvocations() {
		JDTWALADataStructure data = ScenarioAnalyzerUtil.getDataStructureInstance(version);
		CGNode node = data.getMethodNodeFromIndex(signature);
		if(methodInvocations.isEmpty()){
			//Adiciona os métodos chamados por este método 
			Iterator<CGNode> successors = data.getCallGraph().getSuccNodes(node);
			while (successors.hasNext()) {
				CGNode invocation = successors.next();
				if (invocation.getMethod().getDeclaringClass().getClassLoader().getReference().equals(JavaSourceAnalysisScope.SOURCE))
					methodInvocations.add(data.findOrCreateMethodDataFromIndex(invocation));
			}
		}
		return methodInvocations;
	}

	public List<MethodData> getMethodParents() {
		JDTWALADataStructure data = ScenarioAnalyzerUtil.getDataStructureInstance(version);
		CGNode node = data.getMethodNodeFromIndex(signature);
		if(methodParents.isEmpty()){
			//Adiciona os chamadores deste método 
			Iterator<CGNode> predecessors = data.getCallGraph().getPredNodes(node);
			while (predecessors.hasNext()) {
				CGNode parent = predecessors.next();//TODO: Porque o método setTermoUm de OperacaoAdicao não tem predecessores que não sejam rootFake?
				if (parent.getMethod().getDeclaringClass().getClassLoader().getReference().equals(JavaSourceAnalysisScope.SOURCE))
					methodParents.add(data.findOrCreateMethodDataFromIndex(parent));
			}
		}
		return methodParents;
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

	public MethodData clone() throws CloneNotSupportedException {
        return (MethodDataImpl) super.clone();
    }

	public String getPartialSignature() {
		return partialSignature;
	}

	public void setPartialSignature(String partialSignature) {
		this.partialSignature = partialSignature;
	}

	@Override
	public String toString() {
		return "MethodDataImpl [" + signature + "]";
	}
	
}
