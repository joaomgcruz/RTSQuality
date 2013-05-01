package br.ufrn.ppgsc.scenario.analyzer.processors.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Component;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.ComponentData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.processors.AbstractProcessorQA;
import br.ufrn.ppgsc.scenario.analyzer.processors.IAnnotationProcessor;
import br.ufrn.ppgsc.scenario.analyzer.util.FactoryDataElement;
import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

public class AnnotationProcessor implements IAnnotationProcessor {
	
	private List<AbstractProcessorQA> listProcessorQA;
	
	public AnnotationProcessor() {
		this.listProcessorQA = new ArrayList<AbstractProcessorQA>();
	}
	
	public void addProcessorQA(Class<? extends AbstractProcessorQA> cls) {
		try {
			listProcessorQA.add(cls.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void process(JDTWALADataStructure data) {
		// Primeiro processa os casos de uso anotados
		for (Annotation annotation : data.getAnnotations(Scenario.class))
			processScenario(data, annotation);
		System.out.println("[AnnotationProcessor]...Scenario...OK!");
		
		// Depois processa os componentes
		for (Annotation annotation : data.getAnnotations(Component.class))
			processComponent(data, annotation);
		System.out.println("[AnnotationProcessor]...Component...OK!");
		
		// Por último, processa os atributos de qualidade
		for (AbstractProcessorQA p : listProcessorQA) {
			p.processQA(data);
			System.out.println("[AnnotationProcessor]..." + p.getAnnotationClass().getSimpleName() + "...OK!");
		}
	}
	
	private void processComponent(JDTWALADataStructure data, Annotation node) {
		ASTNode node_parent = node.getParent();
		
		if (node_parent instanceof TypeDeclaration) {
			ITypeBinding type_binding = ((TypeDeclaration) node_parent).resolveBinding();
			ClassData cls = data.getClassDataFromIndex(type_binding.getQualifiedName());
			
			if (cls != null) {
				String component_name = ScenarioAnalyzerUtil.getAnnotationValue(node.resolveAnnotationBinding(), "name");
				
				if (component_name.equals(""))
					component_name = type_binding.getName();
				
				ComponentData component = ScenarioAnalyzerUtil.getFactoryDataElement().createComponentData();
				component.setName(component_name);
				
				cls.setDeclaringComponent(component);
			}
			else
				System.err.println("[AnnotationProcessor] Ignoring annotation @Component in " + type_binding.getQualifiedName());
		}
	}

	private void processScenario(JDTWALADataStructure data, Annotation node) {
		// Fábrica para os elementos de dados
		FactoryDataElement factory = ScenarioAnalyzerUtil.getFactoryDataElement();
		
		// pega o nome do caso de uso
		String uc_name = ScenarioAnalyzerUtil.getAnnotationValue(node.resolveAnnotationBinding(), "name");
		
		// cria o caso de uso e configura os valores
		ScenarioData scenario = factory.createScenarioData();
		scenario.setName(uc_name);

		// descobrir o método onde está a anotação de caso de uso e a classe que ele pertence
		IMethodBinding m_binding = ((MethodDeclaration) node.getParent()).resolveBinding();
		
		// verifica se o método já foi analisado
		MethodData method = data.getMethodDataFromIndex(ScenarioAnalyzerUtil.getStandartMethodSignature(m_binding));
		
		// associar o cenário com o método inicial e vice-versa
		scenario.setStartMethod(method);
		method.setScenario(scenario);
		
		// adiciona o caso de uso para o modelo do sistema
		data.addScenario(scenario);
	}


}
