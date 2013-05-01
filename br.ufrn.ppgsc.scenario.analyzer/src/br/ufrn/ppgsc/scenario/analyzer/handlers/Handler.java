package br.ufrn.ppgsc.scenario.analyzer.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;

import br.ufrn.ppgsc.scenario.analyzer.data.ScenarioData;
import br.ufrn.ppgsc.scenario.analyzer.data.impl.FactoryDataElementImpl;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.AnnotationProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.JavaProjectProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.PerformanceProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.ReliabilityProcessor;
import br.ufrn.ppgsc.scenario.analyzer.processors.impl.SecurityProcessor;
import br.ufrn.ppgsc.scenario.analyzer.util.ScenarioAnalyzerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class Handler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public Handler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] projects = root.getProjects();

		String names = "";
		List<IProject> list = new ArrayList<IProject>();
		
		for (IProject p : projects) {
			try {
				if (p.isOpen() && p.hasNature(JavaCore.NATURE_ID)) {
					names += "\t" + p.getName() + "\n";
					list.add(p);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "Analyzer", "Projects:" + names);

		for (IProject p : list) {
			ScenarioAnalyzerUtil.setFactoryDataElement(FactoryDataElementImpl.class);
			ScenarioAnalyzerUtil.setProjectProcessor(JavaProjectProcessor.class);
			ScenarioAnalyzerUtil.setAnnotationProcessor(AnnotationProcessor.class);

			ScenarioAnalyzerUtil.getAnnotationProcessor().addProcessorQA(PerformanceProcessor.class);
			ScenarioAnalyzerUtil.getAnnotationProcessor().addProcessorQA(SecurityProcessor.class);
			ScenarioAnalyzerUtil.getAnnotationProcessor().addProcessorQA(ReliabilityProcessor.class);

			ScenarioAnalyzerUtil.getProjectProcessor().process(p);
		}

		if (list.size() == 2) {
			Set<ScenarioData> result = ScenarioAnalyzerUtil.getAddedScenarios(
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(0).getName()),
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(1).getName()));
			
			System.out.println("Added Scenarios:");
			for (ScenarioData s : result)
				System.out.println("\t" + s.getName());
			
			result = ScenarioAnalyzerUtil.getRemovedScenarios(
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(0).getName()),
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(1).getName()));
			
			System.out.println("Removed Scenarios:");
			for (ScenarioData s : result)
				System.out.println("\t" + s.getName());
			
			result = ScenarioAnalyzerUtil.getUpdatedScenarios(
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(0).getName()),
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(1).getName()));
			
			System.out.println("Updated Scenarios:");
			for (ScenarioData s : result)
				System.out.println("\t" + s.getName());
			
			result = ScenarioAnalyzerUtil.getUnchangedScenarios(
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(0).getName()),
					ScenarioAnalyzerUtil.getDataStructureInstance(list.get(1).getName()));
			
			System.out.println("Not Updated Scenarios:");
			for (ScenarioData s : result)
				System.out.println("\t" + s.getName());
		}

		return null;
	}
}
