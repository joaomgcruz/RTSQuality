package br.ufrn.ppgsc.scenario.analyzer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Annotation;

import br.ufrn.ppgsc.scenario.analyzer.Activator;
import br.ufrn.ppgsc.scenario.analyzer.data.ClassData;
import br.ufrn.ppgsc.scenario.analyzer.data.MethodData;
import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.ElementIndexer;

import com.ibm.wala.cast.java.client.JDTJavaSourceAnalysisEngine;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.client.AbstractAnalysisEngine;
import com.ibm.wala.ide.util.EclipseFileProvider;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.collections.Pair;

public class JDTWALADataStructure {
	
	private CallGraph callGraph;
	
	// Versão do sistema da estrutura de dados
	private String version;
	
	/* TODO
	 * Reusei o pair do wala, ver implicações disso!
	 */
	private Map<String, Pair<MethodData, CGNode>> indexMethod;
	private Map<String, List<Annotation>> indexAnnotation;
	private Map<String, ClassData> indexClassData;
	private List<ScenarioData> listScenario;

	protected JDTWALADataStructure(String version) {
		this.version = version; 
		
		indexAnnotation = new HashMap<String, List<Annotation>>();
		indexMethod = new HashMap<String, Pair<MethodData, CGNode>>();
		indexClassData = new HashMap<String, ClassData>();
		listScenario = new ArrayList<ScenarioData>();
	}

	public static JDTWALADataStructure getNewInstance(String version) {
		return new JDTWALADataStructure(version);
	}
	
	public String getVersion() {
		return version;
	}
	
	/**
	 * Popula as classes filhas de classData com os métodos herdados
	 * @param classData
	 */
	private void populateInheritedMethods(ClassData classData){
		for(ClassData childClassData : classData.getChildrenClasses()){
			List<Pair<MethodData,CGNode>> allMethods = new ArrayList<Pair<MethodData,CGNode>>();
			childClassData.inicializarInheritedMethods(classData.getAllMethods().size());
			for(MethodData methodData : classData.getAllMethods()){
				CGNode node = getMethodNodeFromIndex(methodData.getSignature()); //O CGNode continua sendo o mesmo da classe pai
				if(!methodData.isInit()){
					try {
						MethodData childMethodData = methodData.clone();
						String signature = new String(childClassData.getClassPackage() + "." + childClassData.getName() + "." + childMethodData.getPartialSignature());
						childMethodData.setSignature(signature);
						childMethodData.setDeclaringClass(childClassData);
						allMethods.add(Pair.make(childMethodData,node));
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}
			for (Pair<MethodData,CGNode> pair : allMethods) {
				if(!childClassData.getDeclaringMethods().contains(pair.fst)){
					addMethodToIndex(pair.fst, pair.snd);
					childClassData.getInheritedMethods().add(pair.fst);
				}
			}
			populateInheritedMethods(childClassData);
		}
	}
	
	/**
	 * Cria um ClassData para classes que só possuam métodos herdados e popula os métodos herdados pelas classes pai, excluindo os que foram sobrescritos.
	 * Tal procedimento é necessário pois só é possível determinar por completo o conjunto de métodos herdados quando as classes pai já tiverem a relação de todos os seus métodos.
	 */
	public void populateInheritedMethods(){
		IClassHierarchy cha = getCallGraph().getClassHierarchy();
		for (IClass iClass : cha) {
			findOrCreateClassData(iClass, null, null); //
		}
		for (ClassData classData : indexClassData.values()) {
			if(classData.getInheritedMethods().isEmpty() && classData.getSuperClass() == null)
				populateInheritedMethods(classData);
		}
	}
	
	/**
	 * Recupera ou cria a classe configurando seus valores, a partir de uma iClass e de uma childClass
	 * @param iClass
	 * @param addChildClass
	 * @param addDeclaringMethod
	 * @return
	 */
	public ClassData findOrCreateClassData(IClass iClass, ClassData addChildClass, MethodData addDeclaringMethod) {
		String class_name = iClass.getName().getClassName().toString();
		String class_signature = iClass.getName().getPackage().toString().replaceAll("/", ".") + "." + iClass.getName().getClassName().toString();//iClass.getName().getPackage() + class_name;
		ClassData classData = getClassDataFromIndex(class_name);
		if (classData == null) {
			classData = ScenarioAnalyzerUtil.getFactoryDataElement().createClassData();
			classData.setName(class_name);
			classData.setSignature(class_signature);
			classData.setClassPackage(iClass.getName().getPackage().toString().replaceAll("/", "."));
			if(iClass.getReference().getName().toString().equals("Ljunit/framework/TestCase"))
				classData.setTest(true);
			else if(iClass.getSuperclass() != null){ //TODO: Só é null quando chega em Object, não deveriamos limitar ao escopo do projeto? Dá forma que está parece acessar as classes extendidas que estão contidas nas bibliotecas
				classData.setSuperClass(findOrCreateClassData(iClass.getSuperclass(),classData,null));
				classData.setTest(classData.getSuperClass().isTest());
			}
			addClassDataToIndex(class_name, classData);
		}
		if(addChildClass != null && !classData.getChildrenClasses().contains(addChildClass))
			classData.getChildrenClasses().add(addChildClass);
		if(addDeclaringMethod != null && !classData.getDeclaringMethods().contains(addDeclaringMethod))
			classData.getDeclaringMethods().add(addDeclaringMethod);
		return classData;
	}
	
	/**
	 * Recupera ou cria o método configurando seus valores, a partir de um Call Graph Node
	 * @param node
	 * @return
	 */
	public MethodData findOrCreateMethodDataFromIndex(CGNode node) {
		IMethod iMethod = node.getMethod();
		String signature = ScenarioAnalyzerUtil.getStandartMethodSignature(iMethod);
		MethodData method = getMethodDataFromIndex(signature);
		if(method == null){
			method = ScenarioAnalyzerUtil.getFactoryDataElement().createMethodData();
			method.setName(iMethod.isInit()? iMethod.getDeclaringClass().getName().getClassName().toString() : iMethod.getName().toString());
			method.setSignature(signature);
			method.setPartialSignature(ScenarioAnalyzerUtil.getPartialMethodSignature(iMethod));
			method.setVersion(getVersion());
			method.setDeclaringClass(findOrCreateClassData(iMethod.getDeclaringClass(), null,method));
			method.setInit(iMethod.isInit());
			if(!method.getDeclaringClass().getDeclaringMethods().contains(method))
				method.getDeclaringClass().getDeclaringMethods().add(method);
			addMethodToIndex(method, node);
		}
		return method;
	}

	public void addMethodToIndex(MethodData method, CGNode node) {
		indexMethod.put(method.getSignature(), Pair.make(method, node));
	}
	
	public MethodData getMethodDataFromIndex(String key) {
		if (indexMethod.containsKey(key))
			return indexMethod.get(key).fst;
		return null;
	}
	
	public CGNode getMethodNodeFromIndex(String key) {
		if (indexMethod.containsKey(key))
			return indexMethod.get(key).snd;
		return null;
	}
	
	public MethodData[] getMethodDataAsArray() {
		MethodData list[] = new MethodData[indexMethod.size()];
		
		int i = 0;
		for (Pair<MethodData, CGNode> pair : indexMethod.values())
			list[i++] = pair.fst;
		
		return list;
	}

	public void addClassDataToIndex(String key, ClassData cls) {
		indexClassData.put(key, cls);
	}
	
	public ClassData getClassDataFromIndex(String key) {
		return indexClassData.get(key);
	}
	
	public List<Annotation> getAnnotations(String simple_name) {
		List<Annotation> list = indexAnnotation.get(simple_name);
		
		if (list == null) {
			list = new ArrayList<Annotation>();
			indexAnnotation.put(simple_name, list);
		}
		
		return list;
	}
	
	public List<Annotation> getAnnotations(Class<? extends java.lang.annotation.Annotation> cls) {
		return getAnnotations(cls.getSimpleName());
	}
	
	public void addAnnotationToIndex(String simple_name, Annotation node) {
		getAnnotations(simple_name).add(node);
	}
	
	public void addAnnotationToIndex(Class<? extends java.lang.annotation.Annotation> cls, Annotation node) {
		getAnnotations(cls.getSimpleName()).add(node);
	}

	public void addScenario(ScenarioData scenario) {
		listScenario.add(scenario);
	}

	public List<ScenarioData> getScenarios() {
		return listScenario;
	}
	
	public CallGraph getCallGraph() {
		return callGraph;
	}

	public void printInfo() {
		System.out.println("indexMethod.size() = " + indexMethod.size());
		System.out.println("indexAnnotation.size() = " + indexAnnotation.size());
		System.out.println("indexClassData.size() = " + indexClassData.size());
		System.out.println("listScenario.size() = " + listScenario.size());
		
		for (String sig: indexMethod.keySet()) {
			System.out.println("# " + sig);
		}
	}
	
	/* TODO
	 * O index de métodos deve conter apenas os métodos presentes em algum cenário
	 * - construir o índice enquanto é montado o grafo de chamadas
	 * - este método pode ter o callback para indexar os métodos durante o processo:
	 * - com.ibm.wala.client.AbstractAnalysisEngine.buildDefaultCallGraph()
	 */
	public void buildIndexes(IJavaProject project) {
		ElementIndexer indexer = new ElementIndexer();
		
		indexer.indexMethod(this);
		System.out.println("--- Methods indexed");
		
		try {
			indexer.indexAnnotation(project, this);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		System.out.println("--- Annotations indexed");
	}
	
	public void buildCallGraph(IJavaProject project) {
		try {
			callGraph = makeAnalysisEngine(project).buildDefaultCallGraph();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (CancelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private AbstractAnalysisEngine makeAnalysisEngine(IJavaProject project) {
		AbstractAnalysisEngine engine = null;
		
		try {
			engine = new JDTJavaSourceAnalysisEngine(project) {
				@Override
				public void buildAnalysisScope() throws IOException {
					setExclusionsFile((new EclipseFileProvider()).getFileFromPlugin(
							Activator.getDefault(), "Exclusions.txt").getAbsolutePath());
					
					super.buildAnalysisScope();
				}

				@Override
				protected Iterable<Entrypoint> makeDefaultEntrypoints(AnalysisScope scope, IClassHierarchy cha) {
					return new ScenarioApplicationEntrypoints(cha);
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return engine;
	}

}
