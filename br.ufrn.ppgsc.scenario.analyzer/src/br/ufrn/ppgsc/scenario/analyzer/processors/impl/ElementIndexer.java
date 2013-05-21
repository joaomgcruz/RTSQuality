package br.ufrn.ppgsc.scenario.analyzer.processors.impl;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;
import br.ufrn.ppgsc.scenario.analyzer.visitors.AnnotationVisitor;

import com.ibm.wala.cast.java.ipa.callgraph.JavaSourceAnalysisScope;
import com.ibm.wala.ipa.callgraph.CGNode;

public class ElementIndexer {
	
	public void indexMethod(JDTWALADataStructure data) {
		for (CGNode node : data.getCallGraph()) {
			if (!node.getMethod().getDeclaringClass().getClassLoader().toString().equals(JavaSourceAnalysisScope.PRIMORDIAL.toString()))
				data.findOrCreateMethodDataFromIndex(node);
		}
		data.populateInheritedMethods();
	}

	public void indexAnnotation(IJavaProject javaProject, JDTWALADataStructure data) throws JavaModelException {
		for (IPackageFragment mypackage : javaProject.getPackageFragments())
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE)
				for (ICompilationUnit unit : mypackage.getCompilationUnits())
					ScenarioAnalyzerUtil.getCompilationUnitFromUnit(unit).accept(new AnnotationVisitor(data));
	}
	
}
