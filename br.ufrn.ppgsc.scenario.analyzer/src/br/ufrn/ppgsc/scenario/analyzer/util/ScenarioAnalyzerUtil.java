package br.ufrn.ppgsc.scenario.analyzer.util;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import br.ufrn.ppgsc.scenario.analyzer.data.AbstractQAData;
import br.ufrn.ppgsc.scenario.analyzer.data.ComponentData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.processors.IAnnotationProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.IProjectProcessor;

import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.types.TypeName;


public abstract class ScenarioAnalyzerUtil {
	
	private static Map<String, JDTWALADataStructure> data;
	private static FactoryDataElement factory;
	private static IProjectProcessor projectProcessor;
	private static IAnnotationProcessor annotationProcessor;
	
	public static FactoryDataElement getFactoryDataElement() {
		return factory;
	}
	
	public static IProjectProcessor getProjectProcessor() {
		return projectProcessor;
	}
	
	public static IAnnotationProcessor getAnnotationProcessor() {
		return annotationProcessor;
	}
	
	public static void setFactoryDataElement(Class<? extends FactoryDataElement> cls) {
		try {
			factory = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void setProjectProcessor(Class<? extends IProjectProcessor> cls) {
		try {
			projectProcessor = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void setAnnotationProcessor(Class<? extends IAnnotationProcessor> cls) {
		try {
			annotationProcessor = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static JDTWALADataStructure createDataStructure(String version) {
		if (data == null)
			data = new HashMap<String, JDTWALADataStructure>();
		
		JDTWALADataStructure ds = new JDTWALADataStructure(version);
		
		data.put(ds.getVersion(), ds);
		
		return ds;
	}
	
	public static JDTWALADataStructure getDataStructureInstance(String version) {
		return data.get(version);
	}

	public static CompilationUnit getCompilationUnitFromUnit(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);

		parser.setSource(unit);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);

		return (CompilationUnit) parser.createAST(null);
	}
	
	public static void printDataStructure(JDTWALADataStructure data, PrintStream out) {
		for (ScenarioData scenario : data.getScenarios()) {
			out.println("**********************\nScenario: " + scenario.getName());
			printMethod(scenario.getStartMethod(), new HashSet<MethodData>(), "   ", out);
		}
	}
	
	public static void printMethod(String pname, String signature, PrintStream out) {
		printMethod(data.get(pname).getMethodDataFromIndex(signature), new HashSet<MethodData>(), "   ", out);
	}
	
	private static void printMethod(MethodData method, Set<MethodData> visited, String space, PrintStream out) {
		out.print(space + "Method: " + method.getName() + " [" + method.getDeclaringClass().getName() + "]");
		
		ComponentData component = method.getDeclaringClass().getDeclaringComponent();
		if (component != null)
			out.println("[" + component.getName() + "]");
		else
			out.println();
		
		for (AbstractQAData qa : method.getQualityAttributes())
			out.println(space + " # Quality Atributte: " + qa.getName() + " [" + qa.getClass().getSimpleName() + "]");
		
		visited.add(method);
		space += "   ";
		
		for (MethodData m : method.getMethodInvocations()) {
			if (m == method)
				out.println(space + "Method: *" + m.getName() + " [" + m.getDeclaringClass().getName() + "]");
			else if (visited.contains(m))
				out.println(space + "Method: **" + m.getName() + " [" + m.getDeclaringClass().getName() + "]");
			else {
				printMethod(m, visited, space, out);
				visited.remove(m);
			}
		}
	}
	
	public static String getStandartMethodSignature(IMethodBinding method) {
		StringBuffer result = new StringBuffer();
		
		result.append(method.getDeclaringClass().getQualifiedName());
		result.append(".");
		result.append(method.getName());
		result.append("(");
		
		for (ITypeBinding type : method.getParameterTypes()) {
			if (type.isArray()) {
				result.append(convertTypeSignatureToName(type.getElementType().getBinaryName()));
				
				for (int i = 0; i < type.getDimensions(); i++)
					result.append("[]");
			}
			else {
				result.append(convertTypeSignatureToName(type.getBinaryName()));
			}
			
			result.append(",");
		}
		
		if (result.charAt(result.length() - 1) == ',')
			result.deleteCharAt(result.length() - 1);
		
		return result + ")";
	}
	
	public static String getStandartMethodSignature(IMethod method) {
		StringBuffer result = new StringBuffer();
		
		result.append(method.getDeclaringClass().getName().getPackage().toString().replaceAll("/", "."));
		result.append(".");
		result.append(method.getDeclaringClass().getName().getClassName());
		result.append(".");
		
		if (method.isInit())
			result.append(method.getDeclaringClass().getName().getClassName());
		else
			result.append(method.getName());
		
		result.append("(");
		
		for (int i = 0; i < method.getSelector().getDescriptor().getNumberOfParameters(); i++) {
			TypeName type = method.getSelector().getDescriptor().getParameters()[i];
			
			if (type.getPackage() != null) {
				result.append(type.getPackage().toString().replaceAll("/", "."));
				result.append(".");
			}
			
			result.append(convertTypeSignatureToName(type.getClassName().toString()));
			
			if (type.isArrayType()) {
				int j = 0;
				
				while (type.toString().charAt(j++) == '[')
					result.append("[]");
			}
			
			result.append(",");
		}
		
		if (result.charAt(result.length() - 1) == ',')
			result.deleteCharAt(result.length() - 1);
		
		return result + ")";
	}
	
	public static String getStandartMethodSignature(Method method) {
		StringBuffer result = new StringBuffer();
		
		result.append(method.getDeclaringClass().getName());
		result.append(".");
		result.append(method.getName());
		result.append("(");
		
		for (Class<?> cls : method.getParameterTypes()) {
			result.append(cls.getCanonicalName());
			result.append(",");
		}
		
		if (result.charAt(result.length() - 1) == ',')
			result.deleteCharAt(result.length() - 1);
		
		return result + ")";
	}
	
	private static String convertTypeSignatureToName(String type) {
		switch (type) {
			case "Z": return "boolean";
			case "B": return "byte";
			case "C": return "char";
			case "D": return "double";
			case "F": return "float";
			case "I": return "int";
			case "J": return "long";
			case "S": return "short";
			case "V": return "void";
			default: return type;
		}
	}
	
	public static void printNodes(CallGraph cg) {
		for (Iterator<CGNode> it = cg.getSuccNodes(cg.getFakeRootNode()); it.hasNext();)
			printNodes(cg, it.next(), new HashSet<CGNode>(), "");
	}
	
	private static void printNodes(CallGraph cg, CGNode root, Set<CGNode> visited, String str) {
		if (root.getMethod().getDeclaringClass().getClassLoader().toString().equals("Primordial"))
			return;
		
		if (visited.contains(root)) {
			System.out.println(str + "[*]" + root.getMethod().getSignature());
			return;
		}
		else
			System.out.println(str + root.getMethod().getSignature());
		
		visited.add(root);
		
		for (Iterator<CallSiteReference> it = root.iterateCallSites(); it.hasNext();)
			for (CGNode cgNode : cg.getPossibleTargets(root, it.next()))
				printNodes(cg, cgNode, visited, str + "\t");
		
		visited.remove(root);
	}
	
	public static String getAnnotationValue(IAnnotationBinding annotation_binding, String attribute) {
		for (IMemberValuePairBinding pair : annotation_binding.getAllMemberValuePairs())
			if (pair.getName().endsWith(attribute))
				return (String) pair.getValue();
		
		return null;
	}
	
	/* TODO
	 * Ver como tornar este método não sensível a troca de nome do cenário
	 * */
	public static Set<ScenarioData> getAddedScenarios(JDTWALADataStructure datav1, JDTWALADataStructure datav2) {
		Set<ScenarioData> result = new HashSet<ScenarioData>();
		
		for (ScenarioData sv2 : datav2.getScenarios()) {
			boolean found = false;
			
			for (ScenarioData sv1 : datav1.getScenarios()) {
				if (sv2.getName().equals(sv1.getName())) {
					found = true;
					break;
				}
			}
			
			if (!found)
				result.add(sv2);
		}
		
		return result;
	}
	
	private static boolean checkSameInvocations(MethodData root1, MethodData root2) {
		
		if (!root1.getSignature().equals(root2.getSignature()))
			return false;
		
		System.out.println(root1.getSignature() + " && " + root2.getSignature());
		
		List<MethodData> list1 = root1.getMethodInvocations();
		List<MethodData> list2 = root2.getMethodInvocations();
		
		if (list1.isEmpty() && list2.isEmpty()) {
			System.out.println("isEmpty");
			return true;
		}
		
		if (list1.equals(list2)) {
			System.out.println("equals");
			
			for (int i = 0; i < list1.size(); i++) {
				System.out.println(i);
				if (!checkSameInvocations(list1.get(i), list2.get(i)))
					return false;
			}
			
			return true;
		}
		
		System.out.println("false");
		return false;
		
	}
	
	/* TODO: Implementar este método
	 * Podem existir outras formar do cenário diferencias, por exemplo,
	 * quando a assinatura dos métodos mudam, apesar do path ser o mesmo
	 */
	public static Set<ScenarioData> getUpdatedScenarios(JDTWALADataStructure dv1, JDTWALADataStructure dv2) {
		Set<ScenarioData> result = new HashSet<ScenarioData>();
		
		for (ScenarioData sv1 : dv1.getScenarios()) {
			
			for (ScenarioData sv2 : dv2.getScenarios()) {
				
				if (sv1.getName().equals(sv2.getName())) {
					
					if (!checkSameInvocations(sv1.getStartMethod(), sv2.getStartMethod())) {
						result.add(sv2);
						break;
					}

				}
				
			}
			
		}
		
		return result;
	}
	
	public static Set<ScenarioData> getRemovedScenarios(JDTWALADataStructure datav1, JDTWALADataStructure datav2) {
		Set<ScenarioData> result = new HashSet<ScenarioData>();
		
		for (ScenarioData sv1 : datav1.getScenarios()) {
			boolean found = false;
			
			for (ScenarioData sv2 : datav2.getScenarios()) {
				if (sv1.getName().equals(sv2.getName())) {
					found = true;
					break;
				}
			}
			
			if (!found)
				result.add(sv1);
		}
		
		return result;
	}
	
	public static Set<ScenarioData> getUnchangedScenarios(JDTWALADataStructure datav1, JDTWALADataStructure datav2) {
		Set<ScenarioData> result = new HashSet<ScenarioData>();
		
		for (ScenarioData sv1 : datav1.getScenarios())
			for (ScenarioData sv2 : datav2.getScenarios())
				if (sv1.getName().equals(sv2.getName()))
					result.add(sv1);
		
		return result;
	}

}
