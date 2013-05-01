package br.ufrn.ppgsc.scenario.analyzer.processors;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

public abstract class AbstractProcessorQA {

	public final void processQA(JDTWALADataStructure data) {
		
		for (Annotation node : data.getAnnotations(getAnnotationClass())) {
			
			MethodDeclaration method_declaration = (MethodDeclaration) node.getParent();
			IMethodBinding method_binding = method_declaration.resolveBinding();
			MethodData method_data = data.getMethodDataFromIndex(
					ScenarioAnalyzerUtil.getStandartMethodSignature(method_binding));
	
			/*
			 * Se null, significa que o método é anotado por uma atributo de
			 * qualidade, mas não está presente nos casos de uso existente
			 */
			if (method_data != null) {
				AbstractQAData qa_data = createInstance();
				qa_data.setName(ScenarioAnalyzerUtil.getAnnotationValue(node.resolveAnnotationBinding(), "name"));
	
				method_data.getQualityAttributes().add(qa_data);
				qa_data.setMethod(method_data);
				qa_data.setType(getAnnotationClass());
	
				setFields(qa_data, node);
			} else {
				System.err.println("[AbstractProcessorQA] Ignoring annotation @" + node.getTypeName().getFullyQualifiedName()
						+ " in " + method_binding.getDeclaringClass().getQualifiedName() + "." + method_binding.getName());
			}
			
		}
		
	}

	public void setFields(AbstractQAData qa_data, Annotation node) {

	}

	public abstract AbstractQAData createInstance();
	
	public abstract Class<? extends java.lang.annotation.Annotation> getAnnotationClass();

}
