package br.ufrn.testtracker.plugin.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.junit.model.JUnitModel;
import org.eclipse.jdt.internal.junit.model.TestRunSession;
import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.pde.ui.launcher.JUnitWorkbenchLaunchShortcut;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.tmatesoft.svn.core.SVNException;

import br.ufrn.taskanalyser.framework.miner.History;
import br.ufrn.taskanalyser.framework.miner.SVNConfig;
import br.ufrn.taskanalyser.framework.ui.Revision;
import br.ufrn.taskanalyser.framework.ui.Task;
import br.ufrn.testtracker.data.TestCoverage;
import br.ufrn.testtracker.data.TestCoverageMapping;

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
		List<Revision> revisions = new ArrayList<Revision>(1);
		revisions.add(new Revision(118));
		tasks.add(new Task(1,revisions,"Refactor"));
		
		SVNConfig sVNConfig = new SVNConfig("http://scenario-analyzer.googlecode.com/svn/trunk","/Calculadora","","");
		
		passo0(tasks,sVNConfig);
	}
	
	public void passo0(List<Task> tasks, SVNConfig sVNConfig){
		/*
		 * coletar as revisões de cada task
		 * chamar passo1 passando todas as revisões
		 */
		for (Task task : tasks) {
			for (Revision revision : task.getRevisions()) {
				try {
					History history = new History(sVNConfig,ResourcesPlugin.getWorkspace());
					IProject iProject = history.checkoutProject(revision.getId()-1);
					
					Set<TestCoverage> allTestsOld = executeAllTests(sVNConfig, revision);
				} catch (SVNException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
//				tempDir = checkoutRevision(sVNConfig, revision.getId());
//				Set<TestCoverage> selecao = executeRevisionRegression(revision.getId());
//				
//				Set<TestCoverage> allTestsCurrent = executeAllTests(iProject);
//				Set<TestCoverage> selecaoIdeal = executeRevisionRegression(revision.getId());
//				
//				metricMeansurement(selecao, allTestsCurrent, selecaoIdeal, revision);
			}
		}
	}
	
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
	
	private static Set<TestCoverage> executeAllTests(SVNConfig sVNConfig, Revision revision) throws CoreException, InterruptedException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(sVNConfig.getProjectPath().substring(1)+"_"+(revision.getId()-1));
		IJavaProject javaProject = JavaCore.create(project);
//		aRun(project.getWorkspace().getRoot().getLocation().toString()+"/"+project.getName());
		SysOutProgressMonitor.out.println("CREATE ALL TESTS PROJECT...");
		ILaunchConfiguration lc[] = new JUnitWorkbenchLaunchShortcut().getLaunchConfigurations(new StructuredSelection(javaProject));
		if (lc != null && lc.length > 0) {    	
			SysOutProgressMonitor.out.println("Launch configurations found: " + lc.length);
			for (ILaunchConfiguration launchConfig : lc) {
				SysOutProgressMonitor.out.println("LAUNCH configuration " + launchConfig.getLocation().toOSString());
				ILaunch launch = launchConfig.launch(ILaunchManager.RUN_MODE, new SysOutProgressMonitor(), true);
				SysOutProgressMonitor.out.println("TEST RUN SESSION FINISHED...");
				TestRunSession testRunSession = new TestRunSession(launch,javaProject, SocketUtil.findFreePort());
				File exportfile = new File("D:/UFRN/test-export.xml");
				SysOutProgressMonitor.out.println("EXPORT RUN SESSION to " + exportfile.getAbsolutePath());
				JUnitModel.exportTestRunSession(testRunSession, exportfile);
				
				while (!((Launch) launch).isDisconnected() || !launch.isTerminated())
					Thread.sleep(1000);
				// wait log to be written out
				Thread.sleep(5000);
			}
		}
		else
			SysOutProgressMonitor.out.println("ERROR: Failed to get launch configuration...");
		SysOutProgressMonitor.out.println("END runAllTests");
        
		return TestCoverageMapping.getInstance().getTestsCoverage();
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