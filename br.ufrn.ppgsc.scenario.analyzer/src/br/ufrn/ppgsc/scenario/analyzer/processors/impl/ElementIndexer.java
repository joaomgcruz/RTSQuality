package br.ufrn.ppgsc.scenario.analyzer.processors.impl;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.util.JDTWALADataStructure;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;
import br.ufrn.ppgsc.scenario.analyzer.visitors.AnnotationVisitor;

import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.CGNode;

public class ElementIndexer {
	
	public void indexMethod(JDTWALADataStructure data) {
		for (CGNode node : data.getCallGraph()) {
			if (node.getMethod().getDeclaringClass().getClassLoader().toString().equals("Primordial"))
				continue;
			
			IMethod imethod = node.getMethod();
			
			String class_name = imethod.getDeclaringClass().getName().getClassName().toString();
			String method_name = imethod.isInit()? class_name : imethod.getName().toString();
			String method_std_signature = ScenarioAnalyzerUtil.getStandartMethodSignature(imethod);
			
			// recupera/cria a classe e configura os valores
			ClassData cls = data.getClassDataFromIndex(class_name);
			if (cls == null) {
				cls = ScenarioAnalyzerUtil.getFactoryDataElement().createClassData();
				cls.setName(class_name);
				
				data.addClassDataToIndex(class_name, cls);
			}
			
			MethodData method = ScenarioAnalyzerUtil.getFactoryDataElement().createMethodData();
			
			method.setName(method_name);
			method.setSignature(method_std_signature);
			method.setDeclaringClass(cls);
			method.setVersion(data.getVersion());
			
			data.addMethodToIndex(method_std_signature, method, node);
		}
	}
	
	public void indexAnnotation(IJavaProject javaProject, JDTWALADataStructure data) throws JavaModelException {
		for (IPackageFragment mypackage : javaProject.getPackageFragments())
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE)
				for (ICompilationUnit unit : mypackage.getCompilationUnits())
					ScenarioAnalyzerUtil.getCompilationUnitFromUnit(unit).accept(new AnnotationVisitor(data));
	}
	
}
