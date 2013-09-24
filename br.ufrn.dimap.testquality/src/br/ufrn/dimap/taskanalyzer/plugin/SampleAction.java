package br.ufrn.dimap.taskanalyzer.plugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaElementInfo;
import org.eclipse.jdt.internal.core.JavaModelManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.tmatesoft.svn.core.SVNException;

import br.ufrn.dimap.taskanalyzer.history.History;
import br.ufrn.dimap.taskanalyzer.history.SVNConfig;
import br.ufrn.dimap.taskanalyzer.regressiontest.DiffRegressionTest;
import br.ufrn.dimap.taskanalyzer.regressiontest.RegressionTestTechnique;
import br.ufrn.dimap.taskanalyzer.util.ProjectUtil;
import br.ufrn.dimap.taskanalyzer.util.TestUtil;
import br.ufrn.dimap.testtracker.aspects.TestTracker;
import br.ufrn.dimap.testtracker.data.MethodData;
import br.ufrn.dimap.testtracker.data.Revision;
import br.ufrn.dimap.testtracker.data.Task;
import br.ufrn.dimap.testtracker.data.TestCoverage;
import br.ufrn.dimap.testtracker.data.TestCoverageMapping;
import br.ufrn.dimap.testtracker.tests.TestFactory;
import br.ufrn.dimap.testtracker.util.FileUtil;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(
			window.getShell(),
			"TestTracker",
			"Hello, Eclipse world");
		List<Task> tasks = new ArrayList<Task>(1);
		Task task1 = new Task(1, new ArrayList<Revision>(1), Task.REFACTOR);
//		Task task2 = new Task(1, new ArrayList<Revision>(1), Task.CORRECTION);
		task1.getRevisions().add(new Revision(154,task1));
//		task2.getRevisions().add(new Revision(138,task2));
		tasks.add(task1);
//		tasks.add(task2);
		
		SVNConfig sVNConfig = new SVNConfig("http://scenario-analyzer.googlecode.com/svn/trunk","/Exemplo","","");
		RegressionTestTechnique regressionTestTechnique = new DiffRegressionTest();
		passo0(tasks,sVNConfig,regressionTestTechnique);
	}
	
	public void passo0(List<Task> tasks, SVNConfig sVNConfig, RegressionTestTechnique regressionTestTechnique){
		/*
		 * coletar as revisões de cada task
		 * chamar passo1 passando todas as revisões
		 */
		Set<Revision> forExecuteAllTests = new HashSet<Revision>();
		Set<Revision> forCheckout = new HashSet<Revision>();
		Set<Revision> forRegression = new HashSet<Revision>();
		for (Task task : tasks) {
			forCheckout.addAll(task.getRevisions());
			forRegression.addAll(task.getRevisions());
			for(Revision revision : task.getRevisions()){
				if(revision.getId() > 1){
					Revision oldRevision = new Revision(revision.getId()-1,task);
					revision.setOldRevision(oldRevision);
					forCheckout.add(oldRevision);
					forExecuteAllTests.add(oldRevision);
				}
			}
		}
		try {
			History history = new History(sVNConfig,ResourcesPlugin.getWorkspace());
			for(Revision revision : forCheckout) {
				history.checkoutProject(revision);
			}
			for(Revision revision : forExecuteAllTests) { //TODO: É provável que haja a necessidade de criar e executar uma função clear no TestCoverageMapping para cada novo projeto, isso evitará que todos os testes se misturem
				IProject iProject = ProjectUtil.getIProject(sVNConfig, revision);
				ClassLoader iProjectClassLoader = ProjectUtil.getIProjectClassLoader(iProject);
				TestUtil.executeTests(iProjectClassLoader, ProjectUtil.getAllTestClasses(iProject), "AllTests");
			}
			for(Revision revision : forRegression){
				try {
					regressionTestTechnique.setRevision(revision);
					IProject iProject = ProjectUtil.getIProject(sVNConfig, revision);
					regressionTestTechnique.setIProject(iProject);
					IProject oldIProject = ProjectUtil.getIProject(sVNConfig, revision.getOldRevision());
					TestCoverageMapping oldTestCoverageMapping = ProjectUtil.getTestCoverageMapping(oldIProject,"AllTests");
					Set<String> allCoveredModifications = oldTestCoverageMapping.getMethodPool().keySet();
					Object configurations[] = {oldTestCoverageMapping};
					regressionTestTechnique.setConfiguration(configurations);
					Set<String> modifiedMethods = history.getChangedMethodsSignatures(revision);
					regressionTestTechnique.setModifiedMethods(modifiedMethods);
					//TODO: identificar porque a lista selectedTests está vazia
					Set<String> selectionCoveredModifications = regressionTestTechnique.getCoveredModifiedMethods();
//					TestUtil.executeTests(iProjectClassLoader, selectedTests, "SelectedTests"); //TODO: para acrescentar esta execução deveria primeiro limpar o TestCoverageMapping
					
					ClassLoader iProjectClassLoader = ProjectUtil.getIProjectClassLoader(iProject);
					TestUtil.executeTests(iProjectClassLoader, ProjectUtil.getAllTestClasses(iProject), "AllTests");
					TestCoverageMapping testCoverageMapping = ProjectUtil.getTestCoverageMapping(iProject,"AllTests");
					Set<String> idealCoveredModifications = testCoverageMapping.getCoveredModifiedMethods(modifiedMethods);
					TestUtil.metricMeansurement(selectionCoveredModifications, idealCoveredModifications, allCoveredModifications, revision);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SVNException e1) {
			e1.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Collection<String> getSourceFolders(IProject iProject) throws JavaModelException {
		IJavaProject iJavaProject = JavaCore.create(iProject);
		Set<String> testSourceFolders = new HashSet<String>();
		for(IPackageFragmentRoot iPackageFragmentRoot : iJavaProject.getAllPackageFragmentRoots()) {
			if(iPackageFragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
				testSourceFolders.add(iPackageFragmentRoot.getPath().toString());
			}
		}
		return testSourceFolders;
	}

	private String getTestDirectory(IProject iProject, String sourceFolder) {
		return iProject.getWorkspace().getRoot().getLocation().toString()+sourceFolder+"/br/ufrn/dimap/testtracker/tests";
	}
	
//	private TestCoverageMapping getTestCoverageMapping(String fileDirectories, Revision currentRevision) {
//		TestCoverageMapping testCoverageMapping = new TestCoverageMapping();
//		testCoverageMapping.setCurrentRevision(oldRevision);
//		testCoverageMapping.setOldRevision = new Revision();
//		this.methodPool = new HashMap<String, MethodData>();
//		this.testCoverageBuilding = new HashMap<Long,TestCoverage>();
//		this.testCoverage = new TreeSet<TestCoverage>();
//		this.nextId = 1;
//		fileDirectory = "C:/";
//		FileUtil.loadFileToObject(fileDirectory, fileName, extension)
//	}
	
	private static boolean aRun(String workspace){
		boolean failure = false;
		try {
			URL classUrl = new URL("file://"+workspace+"/WebContent/WEB-INF/classes/br/ufrn/tests");
	    	URL[] classUrls = { classUrl };
	    	URLClassLoader ucl = new URLClassLoader(classUrls);
	    	Class<?> allTestsClass = ucl.loadClass("OperacaoBeanTeste");
			Runner r = new BlockJUnit4ClassRunner(allTestsClass);
			JUnitCore c = new JUnitCore();
			Result result = c.run(Request.runner(r));
			failure = result.getFailureCount() > 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InitializationError e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return failure;
	}
	
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}