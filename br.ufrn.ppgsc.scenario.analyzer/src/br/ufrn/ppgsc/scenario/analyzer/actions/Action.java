package br.ufrn.ppgsc.scenario.analyzer.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import br.ufrn.ppgsc.scenario.analyzer.data.impl.FactoryDataElementImpl;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.AnnotationProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.JavaProjectProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.PerformanceProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.ReliabilityProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.SecurityProcessor;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

public class Action implements IObjectActionDelegate {

	private Shell shell;
	private ISelection selection;
	
	/**
	 * Constructor for Action1.
	 */
	public Action() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		Object element = ((StructuredSelection) this.selection).getFirstElement();
		IAdaptable adaptable = (IAdaptable) element;
		IResource resource = (IResource) adaptable.getAdapter(IResource.class);
		IProject project = resource.getProject();
		
		ScenarioAnalyzerUtil.setFactoryDataElement(FactoryDataElementImpl.class);
		ScenarioAnalyzerUtil.setProjectProcessor(JavaProjectProcessor.class);
		ScenarioAnalyzerUtil.setAnnotationProcessor(AnnotationProcessor.class);
		
		ScenarioAnalyzerUtil.getAnnotationProcessor().addProcessorQA(PerformanceProcessor.class);
		ScenarioAnalyzerUtil.getAnnotationProcessor().addProcessorQA(SecurityProcessor.class);
		ScenarioAnalyzerUtil.getAnnotationProcessor().addProcessorQA(ReliabilityProcessor.class);
		
		ScenarioAnalyzerUtil.getProjectProcessor().process(project);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
