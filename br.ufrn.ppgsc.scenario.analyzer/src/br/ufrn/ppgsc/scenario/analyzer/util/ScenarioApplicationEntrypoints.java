package br.ufrn.ppgsc.scenario.analyzer.util;

import java.util.HashSet;

import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.ArgumentTypeEntrypoint;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.types.annotations.Annotation;

public class ScenarioApplicationEntrypoints extends HashSet<Entrypoint> {

	private static final long serialVersionUID = 1L;

	public ScenarioApplicationEntrypoints(IClassHierarchy cha) {
		if (cha == null)
			throw new IllegalArgumentException("cha is null");

		for (IClass klass : cha)
			if (!klass.isInterface() && isSourceClassLoader(klass))
				for (IMethod method : klass.getDeclaredMethods())
					if (!method.isAbstract())
						if (hasAnnotation(method, Scenario.class))
							add(new ArgumentTypeEntrypoint(method, cha));
	}

	private boolean isSourceClassLoader(IClass klass) {
		return klass.getClassLoader().getReference().equals(JavaSourceAnalysisScope.SOURCE);
	}
	
	private boolean hasAnnotation(IMethod method, Class<? extends java.lang.annotation.Annotation> cls_annotation) {
		if (method.getAnnotations() == null)
			return false;
		
		for (Annotation wala_annoation : method.getAnnotations())
			if (wala_annoation.getType().getName().getClassName().toString().equals(cls_annotation.getSimpleName()))
				return true;
		
		return false;
	}
	
}
