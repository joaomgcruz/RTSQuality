package br.ufrn.dimap.rtquality.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.internal.resources.mapping.ResourceChangeDescriptionFactory;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNRevision;

import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.ufrn.dimap.rtquality.history.History;
import br.ufrn.dimap.rtquality.history.Project;
import br.ufrn.dimap.rtquality.history.ProjectRevisionInformations;
import br.ufrn.dimap.rtquality.history.SVNConfig;
import br.ufrn.dimap.rtquality.history.Study;
import br.ufrn.dimap.rtquality.regressiontest.DiffRegressionTest;
import br.ufrn.dimap.rtquality.regressiontest.RegressionTestTechnique;
import br.ufrn.dimap.rtquality.util.MathUtil;
import br.ufrn.dimap.rtquality.util.ProjectUtil;
import br.ufrn.dimap.rtquality.util.TestUtil;
import br.ufrn.dimap.ttracker.data.MethodData;
import br.ufrn.dimap.ttracker.data.MethodState;
import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.Task;
import br.ufrn.dimap.ttracker.data.TaskType;
import br.ufrn.dimap.ttracker.data.TaskTypeSet;
import br.ufrn.dimap.ttracker.data.TestCoverage;
import br.ufrn.dimap.ttracker.data.TestCoverageGroup;
import br.ufrn.dimap.ttracker.data.TestCoverageMapping;
import br.ufrn.dimap.ttracker.util.FileUtil;
import br.ufrn.dimap.ttracker.util.ObjectUtil;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
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
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		System.out.println("run(IAction action)");
		MessageDialog.openInformation(window.getShell(), "TestTracker", "Iniciando Estudo Empírico...");
		try {
			estudoEmpirico();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void estudoEmpirico() throws Exception {
		cleanOldFiles();
		Boolean isAutomatic = false;
		RegressionTestTechnique regressionTestTechnique = new DiffRegressionTest();
		IWorkspace iWorkspace = ResourcesPlugin.getWorkspace();
		Map<Integer, Project> projectsBranches = loadProjectsManually();
		Map<Integer, Project> projectsTrunk = loadProjectsManually2();
		List<Project> projectsForExecuteAllTests = getProjectsForExecuteAllTests(projectsBranches);
		try {
			SVNConfig sVNConfig = loadSVNConfig(iWorkspace, projectsBranches);
			History history = new History(sVNConfig, iWorkspace);
			List<Task> tasks = obtainTasksModifiedMethods(iWorkspace, projectsTrunk, history);
			if (tasks == null)
				return;
			tasksRevisionsExecution(isAutomatic, iWorkspace, sVNConfig, regressionTestTechnique, projectsForExecuteAllTests, history, tasks);
		} catch (SVNException e1) {
			e1.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private SVNConfig loadSVNConfig(IWorkspace iWorkspace, Map<Integer, Project> projects) throws Exception {
		String URL = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString() + "/config/URL.txt"));
		String usuario = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString() + "/config/usuario.txt"));
		String senha = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString() + "/config/senha.txt"));
		return new SVNConfig(URL, projects, usuario, senha);
	}

	private List<Project> getProjectsForExecuteAllTests(Map<Integer, Project> projects) {
		List<Project> projectsForExecuteAllTests = new ArrayList<Project>();
		for (int i = 1; i <= projects.size(); i++) {
			Project project = projects.get(i);
			if (project.isAspectJNature())
				projectsForExecuteAllTests.add(project);
		}
		return projectsForExecuteAllTests;
	}

	private void cleanOldFiles() {
		try {
			File tempFolder = new File("temp");
			if (tempFolder != null && tempFolder.isDirectory())
				FileUtils.deleteDirectory(tempFolder); // TODO: Deletar o
														// ProjectsUpdates.xml
														// (Quando?)
			File projectsUpdates = new File("ProjectsUpdates.xml");
			if (projectsUpdates.exists())
				projectsUpdates.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Task> obtainTasksModifiedMethods(IWorkspace iWorkspace, Map<Integer, Project> projects, History history) {
		history.generateTasksXML();
		List<Revision> revisionForCheckout = new ArrayList<Revision>();
		List<Task> tasks = loadTasks(revisionForCheckout, iWorkspace.getRoot().getLocation().toString() + "/config");
		if (tasks == null) {
			System.out.println("Nenhuma informação sobre as tarefas foram encontradas...");
			return null;
		}
		List<Task> tasksToRemove = new ArrayList<Task>();
		for (Task task : tasks) {
			if (task.isDoAndUndoDone()) {
				System.out.println("A tarefa " + task.getId().toString() + " já foi analisada...");
				continue;
			}
			System.out.println("Analisando tarefa: " + task.getId().toString());
			for (Revision revision : task.getRevisions()) {
				if (revision.isDoAndUndoDone()) {
					System.out.println("\tA revisão " + revision.getId() + " já foi analisada...");
					continue;
				}
				System.out.println("\tAnalisando revisão: " + revision.getId());
				try {
					System.out.println("\t\tDiff entre as revisões: " + revision.getOldRevision().getId() + " e " + revision.getId());
					Set<String> A = history.getChangedMethodsSignaturesFromProjects(new HashSet<Project>(projects.values()), revision
							.getOldRevision().getId(), revision.getId());
					Set<String> B = new HashSet<String>(0);
					Set<String> C = new HashSet<String>(0);
					Set<String> D = new HashSet<String>(0);
					if (!A.isEmpty()) {
						if (!revision.getOldRevision().getId().equals(task.getOldRevision().getId())) {
							System.out.println("\t\tDiff entre as revisões: " + task.getOldRevision().getId() + " e " + revision.getId());
							B = history.getChangedMethodsSignaturesFromProjects(new HashSet<Project>(projects.values()), task.getOldRevision()
									.getId(), revision.getId());
							C = MathUtil.intersection(A, B);
							D = new HashSet<String>(A);
							D.removeAll(C);
						} else
							C = new HashSet<String>(A);
					}
					revision.setModifiedMethods(C);
					revision.setUndoModifiedMethods(D);
					revision.setDoAndUndoDone(true);
				} catch (SVNException svne) {
					svne.printStackTrace();
					System.out.println("Conexão Perdida... ou Revisão muito antiga, e o arquivo/projeto ainda nem existe...");
					revision.setDoAndUndoDone(false);
					return null;
				} catch (IOException ioe) {
					System.out.println("Problemas ao acessar arquivos...");
					revision.setDoAndUndoDone(false);
					return null;
				} finally {
					if (revision.isDoAndUndoDone())
						FileUtil.saveObjectToFile(tasks, iWorkspace.getRoot().getLocation().toString() + "/config", "Tasks", "obj");
				}
			}
			Set<String> Undo = new HashSet<String>();
			Set<String> allModifiedMethods = new HashSet<String>();
			for (int index = task.getRevisions().size() - 1; index >= 0; index--) {
				Revision revision = task.getRevisions().get(index);
				Undo.addAll(revision.getUndoModifiedMethods());
				revision.getModifiedMethods().removeAll(Undo);
				allModifiedMethods.addAll(revision.getModifiedMethods());
			}
			if (allModifiedMethods.isEmpty())
				tasksToRemove.add(task);
			task.setModifiedMethods(allModifiedMethods);
			task.setDoAndUndoDone(true);
			FileUtil.saveObjectToFile(tasks, iWorkspace.getRoot().getLocation().toString() + "/config", "Tasks", "obj");
		}
		tasks.removeAll(tasksToRemove);
		history.groupEliminateSortSaveTasks(tasks);
		FileUtil.saveObjectToFile(tasks, iWorkspace.getRoot().getLocation().toString() + "/config", "Tasks", "obj");
		return tasks;
	}

	private void tasksRevisionsExecution(Boolean isAutomatic, IWorkspace iWorkspace, SVNConfig sVNConfig,
			RegressionTestTechnique regressionTestTechnique, List<Project> projectForExecuteAllTests, History history, List<Task> tasks)
			throws SVNException, CoreException, IOException, ClassNotFoundException, JavaModelException, Exception {
		Map<TaskType, Integer> tasksCount = new HashMap<TaskType, Integer>();
		tasksCount.put(TaskType.APRIMORAMENTO, 0);
		tasksCount.put(TaskType.ERROEXECUCAO, 0);
		tasksCount.put(TaskType.ERRONEGOCIOVALIDACAO, 0);
		tasksCount.put(TaskType.ERROPADRONVISUALIZACAO, 0);
		tasksCount.put(TaskType.VERIFICACAO, 0);
		tasksCount.put(TaskType.OTHER, 0);

		Map<TaskType, TaskTypeSet> taskTypes = new HashMap<TaskType, TaskTypeSet>(6);
		taskTypes.put(TaskType.APRIMORAMENTO, new TaskTypeSet(TaskType.APRIMORAMENTO));
		taskTypes.put(TaskType.ERROEXECUCAO, new TaskTypeSet(TaskType.ERROEXECUCAO));
		taskTypes.put(TaskType.ERRONEGOCIOVALIDACAO, new TaskTypeSet(TaskType.ERRONEGOCIOVALIDACAO));
		taskTypes.put(TaskType.VERIFICACAO, new TaskTypeSet(TaskType.VERIFICACAO));
		taskTypes.put(TaskType.ERROPADRONVISUALIZACAO, new TaskTypeSet(TaskType.ERROPADRONVISUALIZACAO));
		taskTypes.put(TaskType.OTHER, new TaskTypeSet(TaskType.OTHER));

		Revision startRevision = new Revision();
		Revision endRevision = new Revision();
		history.getStartAndEndRevisions(startRevision, endRevision);
		Study study = new Study(startRevision, endRevision, tasks);
		Set<String> modifiedMethods = study.getTasksModifiedMethods();
		String saida = "";
		for(Task task : study.getTasks()) {
			for(String method : task.getModifiedMethods()) {
				saida += task.getId()+" "+method+"\n";
			}
		}
		FileUtil.saveTextToFile(saida, iWorkspace.getRoot().getLocation().toString()+"/result", "todosOsMetodos", "txt");
		
		Integer retorno = -1; 
		String start = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString()+"/result/startDone.txt"));
		String end = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString()+"/result/endDone.txt"));
		boolean startDone = (start != null && start.equals("1")) ? true : false;
		boolean endDone = (end != null && end.equals("1")) ? true : false;
		if(!startDone) {
			System.out.println("Analisando a revisão inicial.");
			retorno = checkoutExecuteDelete2(isAutomatic, true, iWorkspace, projectForExecuteAllTests, history, sVNConfig, startRevision, modifiedMethods, tasks, regressionTestTechnique);
			if (retorno.equals(1))
				return;
		}
		if(!endDone) {
			System.out.println("Analisando a revisão final."); //TODO: consertar este if
			retorno = checkoutExecuteDelete2(isAutomatic, false, iWorkspace, projectForExecuteAllTests, history, sVNConfig, endRevision, modifiedMethods, tasks, regressionTestTechnique);
			if (retorno.equals(1))
				return;
		}
		System.out.println("Calculando as métricas.");
		calculateMetricsAndAverages(iWorkspace.getRoot().getLocation().toString(), taskTypes, tasks);
		finalizeAverages(taskTypes);
		System.out.println("Fim da execução do estudo empírito.");
	}

	private void finalizeAverages(Map<TaskType, TaskTypeSet> taskTypes) {
		System.out.println("============================================================");
		System.out.println("Médias dos Tipos de Tarefas:");
		for (TaskTypeSet taskTypeSet : taskTypes.values()) {
			String taxaInclusao = taskTypeSet.getInclusion()+"/"+new Float(taskTypeSet.getTasks().size());
			String taxaPrecisao = taskTypeSet.getPrecision()+"/"+new Float(taskTypeSet.getTasks().size());
			taskTypeSet.setInclusion(taskTypeSet.getInclusion() / new Float(taskTypeSet.getTasks().size()));
			taskTypeSet.setPrecision(taskTypeSet.getPrecision() / new Float(taskTypeSet.getTasks().size()));
			System.out.println("\tTipo da Tarefa: "+taskTypeSet.getName()+"\n\t\tInclusão: "+taxaInclusao+" - "+taskTypeSet.getInclusion()+"%\n\t\tPrecisão: "+taxaPrecisao+" - "+taskTypeSet.getPrecision()+"%");
		}
		System.out.println("============================================================");
	}

	private void calculateMetricsAndAverages(String iWorkspaceFolder, Map<TaskType, TaskTypeSet> taskTypes, List<Task> tasks) {
		System.out.println("============================================================");
		System.out.println("Métricas das Tarefas:");
		for(Task task : tasks) {
			task.setInclusion(TestUtil.getInclusionMeasureGroup(task.getOldSelectionGroup(), task.getCurrentSelectionGroup()) * 100);
			Set<TestCoverageGroup> intersection = TestCoverage.intersectionGroup(task.getOldSelectionGroup(), task.getCurrentSelectionGroup());
			String taxaInclusao = intersection.size()+"/"+task.getCurrentSelectionGroup().size();
			task.setPrecision(TestUtil.getPrecisionMeasureGroup(task.getOldExclusionGroup(), task.getCurrentExclusionGroup()) * 100);
			Set<TestCoverageGroup> intersection2 = TestCoverage.intersectionGroup(task.getOldExclusionGroup(), task.getCurrentExclusionGroup());
			String taxaExclusao = intersection2.size()+"/"+task.getCurrentExclusionGroup().size();
			System.out.println("\tTarefa Número: "+task.getId()+"\tTipo da Tarefa: "+task.getType().getName()+"\n\t\tInclusão: "+taxaInclusao+" - "+task.getInclusion()+"%\n\t\tPrecisão: "+taxaExclusao+" - "+task.getPrecision()+"%");
			TaskTypeSet taskTypeSet = taskTypes.get(task.getType());
			taskTypeSet.setInclusion(taskTypeSet.getInclusion() + task.getInclusion());
			taskTypeSet.setPrecision(taskTypeSet.getPrecision() + task.getPrecision());
			taskTypeSet.getTasks().add(task);
		}
		System.out.println("============================================================");
	}
	
	private Set<Integer> getQtd(Set<TestCoverageGroup> testCoverageGroups) {
		Set<Integer> qtd = new HashSet<Integer>(0);
		for(TestCoverageGroup tCG : testCoverageGroups) {
			qtd.add(tCG.getId());
		}
		return qtd;
	}

	private Integer checkoutExecuteDelete2(Boolean isAutomatic, Boolean startRevision, IWorkspace iWorkspace,
			List<Project> projectForExecuteAllTests, History history, SVNConfig sVNConfig, Revision revision, Set<String> modifiedMethods,
			List<Task> tasks, RegressionTestTechnique regressionTestTechnique) throws SVNException, CoreException, IOException, ClassNotFoundException,
			JavaModelException, Exception {
		String iWorkspaceFolder = iWorkspace.getRoot().getLocation().toString();
		String resultPath = iWorkspaceFolder + "/result";
		if (!(new File(resultPath + "/TCM_" + revision.getId() + ".tcm")).exists()) {
			System.out.println("\tBaixando projetos com a revisão: " + revision.getId());
			Integer retorno = history.checkouOrUpdateProjects(revision);
			if (retorno.equals(-1))
				return -1;
			Project aProject = projectForExecuteAllTests.get(0);
			ProjectUtil.saveUtilInformations2(iWorkspaceFolder + "/SIGAA/app/sigaa.ear/lib.jar", iWorkspaceFolder, revision.getId(),
					aProject.getName());
			if (isAutomatic) {
//				for (Project project : projectForExecuteAllTests) {
//					if (!project.isExecuteTests())
//						continue;
//					IProject iProject = project.getIProject();
//					ClassLoader iProjectClassLoader = ProjectUtil.getIProjectClassLoader(iProject);
//					TestUtil.executeTests(iProjectClassLoader, ProjectUtil.getAllTestClasses(iProject, project.getPackagesToTest()));
//				}
				MessageDialog
				.openInformation(
						window.getShell(),
						"Fase de Testes",
						"1º) Compile os projetos;\n2º) Execute os JUnits;\n3º) Execute novamente o estudo empírico para continuar.");
				return 1;
			} else {
				MessageDialog
						.openInformation(
								window.getShell(),
								"Fase de Testes",
								"1º) Compile os projetos;\n2º) Inicialize o servidor JBoss;\n3º) Execute os testes manualmente e;\n4º) Execute novamente o estudo empírico para continuar.");
				return 1;
			}
		} else {
			System.out.println("Testes finalizados...");
			for (int i = 1; i <= sVNConfig.getProjects().size(); i++) {
				Project project = sVNConfig.getProjects().get(i);
				project.setIProject(iWorkspace.getRoot().getProject(project.getName()));
				// O projeto não precisa existir no workspace para setar esta
				// informação
			}
			for (Project project : projectForExecuteAllTests)
				ProjectUtil.setAllUncoveredMethods(project, iWorkspaceFolder+"/result", "TCM_" + revision.getId());
		}
		if (startRevision) {
			String allOldTestsResultName = "AllOldTests";
			String selectionResultName = "RTSSelection_" + regressionTestTechnique.getName();
			String exclusionResultName = "RTSExclusion_" + regressionTestTechnique.getName();
			if (!(new File(resultPath + "/" + selectionResultName + ".slc")).exists()
					|| !(new File(resultPath + "/" + exclusionResultName + ".slc")).exists()
					|| !(new File(resultPath + "/" + allOldTestsResultName + ".slc")).exists()) { //TODO: Mudar estes arquivos para armazenarem apenas um boolean (mantenha o allOldTests armazenando os testes mesmo, pois ele sim é necessário)
				// Pythia - a regression test selection tool based on textual
				// differencing
				regressionTestTechnique.setName("Pythia");
				regressionTestTechnique.setRevision(revision);
				TestCoverageMapping tcm = TestCoverageMapping.getInstance();
				//ProjectUtil.getTestCoverageMapping(resultPath, "TCM_" + revision.getId());
				Object configurations[] = { tcm };
				regressionTestTechnique.setConfiguration(configurations);
				// TODO: cada técnica de teste de regressão deve implementar sua própria técnica de obtenção dos métodos modificados
				regressionTestTechnique.setModifiedMethods(modifiedMethods);
				FileUtil.saveObjectToFile(regressionTestTechnique.getModifiedMethods(), resultPath, "modifiedMethods", "obj");
				
				String allModified = "";
				for(String method : regressionTestTechnique.getModifiedMethods())
					allModified += method+"\n";
				FileUtil.saveTextToFile(allModified, resultPath, "allModifiedMethods", "txt");
				
				String allBibModified = "";
				for(String method : regressionTestTechnique.getModifiedMethods()) {
					if(method.contains("biblioteca"))
						allBibModified += method+"\n";
				}
				FileUtil.saveTextToFile(allBibModified, resultPath, "allBibModifiedMethods", "txt");
				
				Set<MethodData> methods = new HashSet<MethodData>(regressionTestTechnique.getMethodStatePool().get(new MethodState(false,true)).values());
				methods.addAll(regressionTestTechnique.getMethodStatePool().get(new MethodState(true, true)).values());
				String allCovered = "";
				for(MethodData method : methods)
					allCovered += method.getSignature()+"\n";
				FileUtil.saveTextToFile(allCovered, resultPath, "allCoveredMethods v"+revision.getId(), "txt");
				
				TestCoverageMapping tcm2 = (TestCoverageMapping) regressionTestTechnique.getConfiguration()[0];
				String tcmText = tcm2.printAllTestsCoverage();
				FileUtil.saveTextToFile(tcmText, tcm2.getFileDirectory(), "tcmText", "txt"); //TODO: Utilizado para testes e debug
				// TODO: Verificar se o TCM que está dentro do DiffRegressionTest é uma cópia ou o mesmo objeto (deve ser o
				// mesmo), este objeto não deve ser salvo pois prejudicaria a construção do MethodState de outras versões
				Set<TestCoverageGroup> allOldTests = new HashSet<TestCoverageGroup>(tcm2.getTestCoveragesGroup());
				Set<TestCoverageGroup> techniqueSelection = new HashSet<TestCoverageGroup>(regressionTestTechnique.executeRegression());
				Set<TestCoverageGroup> techniqueExclusion = new HashSet<TestCoverageGroup>(allOldTests);
				techniqueExclusion.removeAll(techniqueSelection);
				saveTestCoverageGroup(resultPath, allOldTestsResultName, allOldTests);
//				saveTestCoverageGroup(resultPath, selectionResultName, techniqueSelection);
//				saveTestCoverageGroup(resultPath, exclusionResultName, techniqueExclusion);
				for(Task task : tasks) {
					task.setOldSelectionGroup(new HashSet<TestCoverageGroup>(tcm2.getModifiedCoveredMethodsGroup(task.getModifiedMethods())));
					task.setOldExclusionGroup(new HashSet<TestCoverageGroup>(allOldTests));
					task.getOldExclusionGroup().removeAll(task.getOldSelectionGroup());
				}
				FileUtil.saveObjectToFile(tasks, iWorkspaceFolder + "/config", "Tasks", "obj");
				
				Set<String> covered1 = new HashSet<String>(tcm2.getMethodStatePool().get(new MethodState(false,true)).keySet());
				covered1.addAll(tcm2.getMethodStatePool().get(new MethodState(true,true)).keySet());
				Set<String> modified1 = new HashSet<String>(tcm2.getMethodStatePool().get(new MethodState(true,false)).keySet());
				modified1.addAll(tcm2.getMethodStatePool().get(new MethodState(true,true)).keySet());
				Set<String> coveredModified1 = tcm2.getMethodStatePool().get(new MethodState(true,true)).keySet();
				String sizes = "========================================Versão 3.11.24========================================\n";
				sizes += "Quantidade Total de Testes: "+allOldTests.size()+"\n";
				sizes += "Quantidade de Testes Incluídos Pela Técnica: "+techniqueSelection.size()+"\n";
				sizes += "Quantidade de Testes Excluídos Pela Técnica: "+techniqueExclusion.size()+"\n";
				sizes += "Quantidade de Métodos: "+tcm2.getMethodPool().keySet().size()+"\n";
				sizes += "Quantidade de Métodos Cobertos Pelos Testes: "+covered1.size()+"\n";
				sizes += "Quantidade de Métodos Modificados Entre as Versões: "+modified1.size()+"\n";
				sizes += "Quantidade de Métodos Cobertos e Modificados: "+coveredModified1.size()+"\n";
				sizes += "====================================================================================================\n";
				FileUtil.saveTextToFile(sizes, resultPath, "Sizes v3.11.24", "txt");
				FileUtil.saveTextToFile("1", resultPath, "startDone", "txt");
			}
		} else { // TODO: Para que tecnicas que utilizam a currente funcionem terei que colocar a execução dela aqui
			String allOldTestsResultName = "AllOldTests";
			String selectionResultName = "PerfectSelection";
			String exclusionResultName = "PerfectExclusion";
			if (!(new File(resultPath + "/" + selectionResultName + ".slc")).exists()
					|| !(new File(resultPath + "/" + exclusionResultName + ".slc")).exists()) {
				TestCoverageMapping TCM = TestCoverageMapping.getInstance();
				TCM.setModifiedMethods(modifiedMethods);
				
				String allBibModified = "";
				Set<String> modifiedBibMethods = new HashSet<String>(TCM.getMethodStatePool().get(new MethodState(true,false)).keySet());
				modifiedBibMethods.addAll(TCM.getMethodStatePool().get(new MethodState(true, true)).keySet());
				for(String method : modifiedBibMethods) {
					if(method.contains("biblioteca"))
						allBibModified += method+"\n";
				}
				FileUtil.saveTextToFile(allBibModified, resultPath, "allBibModifiedMethods", "txt");
				String tcmText = TCM.printAllTestsCoverage();
				FileUtil.saveTextToFile(tcmText, TCM.getFileDirectory(), "tcmText", "txt"); //TODO: Utilizado para testes e debug
				
				Set<String> coveredMethods = new HashSet<String>(TCM.getMethodStatePool().get(new MethodState(false,true)).keySet());
				coveredMethods.addAll(TCM.getMethodStatePool().get(new MethodState(true, true)).keySet());
				String allCovered = "";
				for(String method : coveredMethods)
					allCovered += method+"\n";
				FileUtil.saveTextToFile(allCovered, resultPath, "allCoveredMethods v"+revision.getId(), "txt");
				
				tcmText = TCM.printAllTestsCoverage();
				FileUtil.saveTextToFile(tcmText, TCM.getFileDirectory(), "tcmText", "txt"); //TODO: Utilizado para testes e debug
				Set<TestCoverageGroup> toolSelection = TCM.getModifiedChangedTestsCoverageGroup();
				Set<TestCoverageGroup> allOldTests = loadTestCoverageGroup(resultPath, allOldTestsResultName);
				Set<TestCoverageGroup> allNewTests = new HashSet<TestCoverageGroup>(TCM.getTestCoveragesGroup());
				Set<TestCoverageGroup> perfectExclusion = new HashSet<TestCoverageGroup>(MathUtil.intersection(allOldTests, allNewTests));
				Set<TestCoverageGroup> perfectSelection = MathUtil.intersection(allOldTests, toolSelection);
				perfectExclusion.removeAll(perfectSelection);
//				saveTestCoverageGroup(resultPath, selectionResultName, perfectSelection);
//				saveTestCoverageGroup(resultPath, exclusionResultName, perfectExclusion);
				for(Task task : tasks) {
					task.setCurrentSelectionGroup(MathUtil.intersection(allOldTests, TCM.getModifiedCoveredMethodsGroup(task.getModifiedMethods())));
					task.setCurrentExclusionGroup(new HashSet<TestCoverageGroup>(MathUtil.intersection(allOldTests, allNewTests)));
					task.getCurrentExclusionGroup().removeAll(task.getCurrentSelectionGroup());
				}
				FileUtil.saveObjectToFile(tasks, iWorkspace.getRoot().getLocation().toString() + "/config", "Tasks", "obj");
				
				Set<String> covered2 = new HashSet<String>(TCM.getMethodStatePool().get(new MethodState(false,true)).keySet());
				covered2.addAll(TCM.getMethodStatePool().get(new MethodState(true,true)).keySet());
				Set<String> modified2 = new HashSet<String>(TCM.getMethodStatePool().get(new MethodState(true,false)).keySet());
				modified2.addAll(TCM.getMethodStatePool().get(new MethodState(true,true)).keySet());
				Set<String> coveredModified2 = TCM.getMethodStatePool().get(new MethodState(true,true)).keySet();
				String sizes = "========================================Versão 3.12.36========================================\n";
				sizes += "Quantidade Total de Testes Válidos: "+MathUtil.intersection(allOldTests, allNewTests).size()+"\n";
				sizes += "Quantidade de Testes Que Devem Ser Incluídos: "+perfectSelection.size()+"\n";
				sizes += "Quantidade de Testes Que Devem Ser Excluídos: "+perfectExclusion.size()+"\n";
				sizes += "Quantidade de Métodos: "+TCM.getMethodPool().keySet().size()+"\n";
				sizes += "Quantidade de Métodos Cobertos Pelos Testes: "+covered2.size()+"\n";
				sizes += "Quantidade de Métodos Modificados Entre as Versões: "+modified2.size()+"\n";
				sizes += "Quantidade de Métodos Cobertos e Modificados: "+coveredModified2.size()+"\n";
				sizes += "====================================================================================================\n";
				FileUtil.saveTextToFile(sizes, resultPath, "Sizes v3.12.36", "txt");
				FileUtil.saveTextToFile("1", resultPath, "endDone", "txt");
			}
		}
		deleteProjectsFromCheckout(sVNConfig, revision, iWorkspaceFolder);
		// TODO: cada revisão pode conter novos testes ou excluídos testes da
		// revisão anterior, ao calcular as métricas tem de levar em
		// consideração apenas o que já existia
		return 0;
	}
	
	private void saveTestCoverageGroup(String resultFolder, String name, Set<TestCoverageGroup> tCG) {
		XStream xstream = new XStream(new DomDriver());
		String xmlText = xstream.toXML(tCG);
		FileUtil.saveTextToFile(xmlText, resultFolder, name, "slc");
	}
	
	private Set<TestCoverageGroup> loadTestCoverageGroup(String resultFolder, String name) {
		try{
			String xml = FileUtil.loadTextFromFile(new File(resultFolder + "/" + name + ".slc"));
			Set<TestCoverageGroup> tCG = null;
			if (xml != null) {
				XStream xstream = new XStream(new DomDriver());
				tCG = (Set<TestCoverageGroup>) xstream.fromXML(xml);
			}
			return tCG;
		} catch(ClassCastException cce) {
			cce.printStackTrace();
		}
		return null;
	}

//	private Integer checkoutExecuteDelete(Boolean isAutomatic, Boolean oldTaskRevision, IWorkspace iWorkspace,
//			List<Project> projectForExecuteAllTests, History history, SVNConfig sVNConfig, Task task, Revision revision,
//			RegressionTestTechnique regressionTestTechnique) throws SVNException, CoreException, IOException, ClassNotFoundException,
//			JavaModelException, Exception {
//
//		String iWorkspaceFolder = iWorkspace.getRoot().getLocation().toString();
//		String resultPath = iWorkspaceFolder + "/result";
//		if ((new File(resultPath + "/RMV_" + task.getId() + ".tcm")).exists()) {
//			System.out.println("Pulando tarefa: " + task.getId());
//			return -1;
//		}
//		if (!(new File(resultPath + "/TCM_" + revision.getId() + ".tcm")).exists()) {
//			System.out.println("\tBaixando projetos com a revisão: " + revision.getId());
//			Integer retorno = history.checkouOrUpdateProjects(revision);
//			if (retorno.equals(-1))
//				return -1;
//			Project aProject = projectForExecuteAllTests.get(0);
//			// Class<?> aClass =
//			// ProjectUtil.getIProjectClassLoader(aProject.getIProject()).loadClass(ProjectUtil.getAClass(aProject.getIProject()));
//			// ProjectUtil.saveUtilInformations(FileUtil.getBuildFolderByResource(aClass),
//			// iWorkspaceFolder, revision.getId(), aProject.getName());
//			ProjectUtil.saveUtilInformations(iWorkspaceFolder + "/SIGAA/app/sigaa.ear/lib.jar", iWorkspaceFolder, revision.getId(), task.getId(),
//					aProject.getName());
//			if (isAutomatic) {
//				for (Project project : projectForExecuteAllTests) {
//					if (!project.isExecuteTests())
//						continue;
//					IProject iProject = project.getIProject();
//					ClassLoader iProjectClassLoader = ProjectUtil.getIProjectClassLoader(iProject);
//					TestUtil.executeTests(iProjectClassLoader, ProjectUtil.getAllTestClasses(iProject, project.getPackagesToTest()));
//				}
//			} else {
//				MessageDialog
//						.openInformation(
//								window.getShell(),
//								"Fase de Testes",
//								"1º) Compile os projetos;\n2º) Inicialize o servidor JBoss;\n3º) Execute os testes manualmente e;\n4º) Execute novamente o estudo empírico para continuar.");
//				return 1;
//			}
//		} else {
//			System.out.println("Testes finalizados...");
//			for (int i = 1; i <= sVNConfig.getProjects().size(); i++) {
//				Project project = sVNConfig.getProjects().get(i);
//				project.setIProject(iWorkspace.getRoot().getProject(project.getName()));
//				// O projeto não precisa existir no workspace para setar esta
//				// informação
//			}
//			for (Project project : projectForExecuteAllTests)
//				ProjectUtil.setAllUncoveredMethods(project, iWorkspaceFolder+"\result", "TCM_" + revision.getId());
//		}
//		if (oldTaskRevision) {
//			String allOldTestsResultName = "AllOldTests_" + task.getId();
//			String selectionResultName = "RTSSelection_" + regressionTestTechnique.getName() + "_" + task.getId();
//			String exclusionResultName = "RTSExclusion_" + regressionTestTechnique.getName() + "_" + task.getId();
//			if (!(new File(resultPath + "/" + selectionResultName + ".slc")).exists()
//					|| !(new File(resultPath + "/" + exclusionResultName + ".slc")).exists()
//					|| !(new File(resultPath + "/" + allOldTestsResultName + ".slc")).exists()) {
//				// Pythia - a regression test selection tool based on textual
//				// differencing
//				regressionTestTechnique.setName("Pythia");
//				regressionTestTechnique.setRevision(revision);
//				TestCoverageMapping TCM = ProjectUtil.getTestCoverageMapping(resultPath, "TCM_" + revision.getId());
//				Object configurations[] = { TCM };
//				regressionTestTechnique.setConfiguration(configurations);
//				// TODO: cada técnica de teste de regressão deve implementar sua
//				// própria técnica de obtenção dos métodos modificados
//				regressionTestTechnique.setModifiedMethods(task.getModifiedMethods());
//				// TODO: Verificar se o TCM que está dentro do
//				// DiffRegressionTest é uma cópia ou o mesmo objeto (deve ser o
//				// mesmo), este objeto não deve ser salvo pois prejudicaria a
//				// construção do MethodState de outras versões
//				Set<TestCoverage> allOldTests = TCM.getTestCoverages();
//				Set<TestCoverage> techniqueSelection = regressionTestTechnique.executeRegression();
//				Set<TestCoverage> techniqueExclusion = new HashSet<TestCoverage>(allOldTests);
//				techniqueExclusion.removeAll(techniqueSelection);
//				FileUtil.saveObjectToFile(allOldTests, iWorkspaceFolder + "/result", allOldTestsResultName, "slc");
//				FileUtil.saveObjectToFile(techniqueSelection, iWorkspaceFolder + "/result", selectionResultName, "slc");
//				FileUtil.saveObjectToFile(techniqueExclusion, iWorkspaceFolder + "/result", exclusionResultName, "slc");
//			}
//		} else { // TODO: Para que tecnicas que utilizam a currente funcionem
//					// terei que colocar a execução dela aqui
//			String allOldTestsResultName = "AllOldTests_" + task.getId();
//			String selectionResultName = "PerfectSelection_" + task.getId();
//			String exclusionResultName = "PerfectExclusion_" + task.getId();
//			if (!(new File(resultPath + "/" + selectionResultName + ".slc")).exists()
//					|| !(new File(resultPath + "/" + exclusionResultName + ".slc")).exists()) {
//				Set<String> modifiedMethods = task.getModifiedMethods();
//				TestCoverageMapping TCM = ProjectUtil.getTestCoverageMapping(resultPath, "TCM_" + revision.getId());
//				TCM.setModifiedMethods(modifiedMethods);
//				Set<TestCoverage> toolSelection = TCM.getModifiedChangedTestsCoverage();
//				Set<TestCoverage> allOldTests = (Set<TestCoverage>) FileUtil.loadObjectFromFile(iWorkspaceFolder + "/result", allOldTestsResultName,
//						"slc");
//				Set<TestCoverage> perfectExclusion = new HashSet<TestCoverage>(allOldTests);
//				Set<TestCoverage> perfectSelection = MathUtil.intersection(allOldTests, toolSelection);
//				perfectExclusion.removeAll(perfectSelection);
//				FileUtil.saveObjectToFile(perfectSelection, iWorkspaceFolder + "/result", selectionResultName, "slc");
//				FileUtil.saveObjectToFile(perfectExclusion, iWorkspaceFolder + "/result", exclusionResultName, "slc");
//			}
//		}
//		deleteProjectsFromCheckout(sVNConfig, revision, iWorkspaceFolder);
//		// TODO: cada revisão pode conter novos testes ou excluídos testes da
//		// revisão anterior, ao calcular as métricas tem de levar em
//		// consideração apenas o que já existia
//		return 0;
//	}

	private void deleteProjectsFromCheckout(SVNConfig sVNConfig, Revision revision, String iWorkspaceFolder) throws CoreException {
		File currentRevisionFile = new File(iWorkspaceFolder + "/config/currentRevision.txt");
		String currentRevision = FileUtil.loadTextFromFile(currentRevisionFile);
		if (currentRevision == null || currentRevision.equals(revision.getId().toString())) {
			for (int i = sVNConfig.getProjects().size(); i >= 1; i--) {
				Project project = sVNConfig.getProjects().get(i);
				IProject iProject = project.getIProject();
				if (iProject.exists()) {
					if (iProject.isOpen()) {
						iProject.build(IncrementalProjectBuilder.CLEAN_BUILD, new SysOutProgressMonitor());
						iProject.close(new SysOutProgressMonitor());
					}
					iProject.delete(true, new SysOutProgressMonitor());
				}
				if (project.isForCheckout()) {
					File projectFile = new File(iWorkspaceFolder + project.getName());
					if (projectFile.exists()) {
						try {
							FileUtils.deleteDirectory(projectFile);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
				}
			}
		}
	}

	private Map<Integer, Project> loadProjectsManually() throws Exception {
		Map<Integer, Project> projects = new HashMap<Integer, Project>();
		projects.put(1, new Project("/br.ufrn.dimap.ttracker", "/br.ufrn.dimap.ttracker", null, false, false));
		projects.put(2, new Project("/LIBS", "/LIBS", null, false, false));
		projects.put(3, new Project("/branches/producao/Arquitetura", "/01_Arquitetura", null, false, true));
		projects.put(4, new Project("/branches/producao/ServicosIntegrados", "/03_ServicosIntegrados", null, false, true));
		projects.put(5, new Project("/branches/producao/EntidadesComuns", "/02_EntidadesComuns", null, false, true));
		projects.put(6, new Project("/04_SharedResources", "/04_SharedResources", null, false, false));
		projects.put(7, new Project("/ServicoRemotoBiblioteca", "/ServicoRemotoBiblioteca", null, false, false));
		Set<String> packagesToTest = new HashSet<String>(1);
		packagesToTest.add("/SIGAA/biblioteca");
		projects.put(8, new Project("/branches/producao/SIGAA", "/SIGAA", null, true, true, packagesToTest));
		return projects;
	}

	private Map<Integer, Project> loadProjectsManually2() throws Exception {
		Map<Integer, Project> projects = new HashMap<Integer, Project>();
		projects.put(1, new Project("/br.ufrn.dimap.ttracker", "/br.ufrn.dimap.ttracker", null, false, false));
		projects.put(2, new Project("/LIBS", "/LIBS", null, false, false));
		projects.put(3, new Project("/trunk/Arquitetura", "/01_Arquitetura", null, false, true));
		projects.put(4, new Project("/trunk/ServicosIntegrados", "/03_ServicosIntegrados", null, false, true));
		projects.put(5, new Project("/trunk/EntidadesComuns", "/02_EntidadesComuns", null, false, true));
		projects.put(6, new Project("/04_SharedResources", "/04_SharedResources", null, false, false));
		projects.put(7, new Project("/ServicoRemotoBiblioteca", "/ServicoRemotoBiblioteca", null, false, false));
		Set<String> packagesToTest = new HashSet<String>(1);
		packagesToTest.add("/SIGAA/biblioteca");
		projects.put(8, new Project("/trunk/SIGAA", "/SIGAA", null, true, true, packagesToTest));
		return projects;
	}

	private List<Task> loadTasks(List<Revision> revisionForCheckout, String location) {
		List<Task> tasks = null;
		Object obj = FileUtil.loadObjectFromFile(location, "Tasks", "obj");
		if (obj != null && obj instanceof ArrayList<?>) {
			tasks = (List<Task>) obj;
			if (tasks != null) {
				for (Task task : tasks) {
					revisionForCheckout.add(task.getOldRevision());
					revisionForCheckout.add(task.getCurrentRevision());
				}
				Collections.sort(revisionForCheckout);
				return tasks;
			}
		}
		String xml = FileUtil.loadTextFromFile(new File(location + "/Tasks.xml"));
		if (xml != null) {
			XStream xstream = new XStream(new DomDriver());
			List<Task> tempList = (List<Task>) xstream.fromXML(xml);
			tasks = new ArrayList<Task>(tempList.size());
			tasks.addAll(tempList);
			Map<Integer, Revision> allRevisionsMap = new HashMap<Integer, Revision>();
			for (Task task : tasks) {
				task.setDoAndUndoDone(false);
				for (Revision revision : task.getRevisions())
					revision.setDoAndUndoDone(false);
				Collections.sort(task.getRevisions());

				if (task.getRevisions().size() > 0) {
					// Definindo a oldRevision da oldTask e as oldTasks da
					// oldRevision
					Set<Task> oldTasks = new HashSet<Task>(1);
					oldTasks.add(task);
					Revision oldRevision = null;
					if (allRevisionsMap.containsKey(task.getRevisions().get(0).getOldRevision().getId())) {
						oldRevision = allRevisionsMap.get(task.getRevisions().get(0).getOldRevision().getId());
						if (oldRevision.getOldTasks() == null)
							oldRevision.setOldTasks(new HashSet<Task>(oldTasks.size()));
						// TODO: Verificar se a lista já está inicializada, se
						// não está ok e pode remover este comentário se sim nao
						// há necessidade deste teste podendo excluir esta e a
						// linha acima
						oldRevision.getOldTasks().addAll(oldTasks);
					} else {
						oldRevision = task.getRevisions().get(0).getOldRevision();
						if (oldRevision.getOldTasks() == null)
							oldRevision.setOldTasks(new HashSet<Task>(oldTasks.size()));
						oldRevision.getOldTasks().addAll(oldTasks);
						allRevisionsMap.put(oldRevision.getId(), oldRevision);
					}
					task.setOldRevision(oldRevision);

					// Definindo a currentRevision da currentTask e as
					// currentTasks da currentRevision
					Set<Task> currentTasks = new HashSet<Task>(1);
					currentTasks.add(task);
					Revision currentRevision = task.getRevisions().get(task.getRevisions().size() - 1);
					if (allRevisionsMap.containsKey(currentRevision.getId())) {
						if (allRevisionsMap.get(currentRevision.getId()).getCurrentTasks() == null)
							allRevisionsMap.get(currentRevision.getId()).setCurrentTasks(new HashSet<Task>(currentTasks.size()));
						allRevisionsMap.get(currentRevision.getId()).getCurrentTasks().addAll(currentTasks);
					} else {
						if (currentRevision.getCurrentTasks() == null)
							currentRevision.setCurrentTasks(new HashSet<Task>(currentTasks.size()));
						// TODO: Verificar se a lista já está inicializada, se
						// não está ok e pode remover este comentário se sim nao
						// há necessidade deste teste podendo excluir esta e a
						// linha acima
						currentRevision.getCurrentTasks().addAll(currentTasks);
						allRevisionsMap.put(currentRevision.getId(), currentRevision);
					}
					task.setCurrentRevision(currentRevision);
				}
			}
			for (Revision revision : allRevisionsMap.values()) {
				if (revision.getOldTasks() == null)
					revision.setOldTasks(new HashSet<Task>(0));
				if (revision.getCurrentTasks() == null)
					revision.setCurrentTasks(new HashSet<Task>(0));
			}
			revisionForCheckout.addAll(allRevisionsMap.values());
			Collections.sort(revisionForCheckout);
			return tasks;
		}
		return null;
	}

	private Set<TestCoverage> getTestCoverageSet(String folder, String name) {
		Object obj = loadTestCoverageGroup(folder, name);
		if (obj != null && obj instanceof Set<?>)
			return (Set<TestCoverage>) obj;
		return new HashSet<TestCoverage>(0);
	}

	private Collection<String> getSourceFolders(IProject iProject) throws JavaModelException {
		IJavaProject iJavaProject = JavaCore.create(iProject);
		Set<String> testSourceFolders = new HashSet<String>();
		for (IPackageFragmentRoot iPackageFragmentRoot : iJavaProject.getAllPackageFragmentRoots()) {
			if (iPackageFragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
				testSourceFolders.add(iPackageFragmentRoot.getPath().toString());
			}
		}
		return testSourceFolders;
	}

	private String getTestDirectory(IProject iProject, String sourceFolder) {
		return iProject.getWorkspace().getRoot().getLocation().toString() + sourceFolder + "/br/ufrn/dimap/testtracker/tests";
	}

	// private TestCoverageMapping getTestCoverageMapping(String
	// fileDirectories, Revision currentRevision) {
	// TestCoverageMapping testCoverageMapping = new TestCoverageMapping();
	// testCoverageMapping.setCurrentRevision(oldRevision);
	// testCoverageMapping.setOldRevision = new Revision();
	// this.methodPool = new HashMap<String, MethodData>();
	// this.testCoverageBuilding = new HashMap<Long,TestCoverage>();
	// this.testCoverage = new TreeSet<TestCoverage>();
	// this.nextId = 1;
	// fileDirectory = "C:/";
	// FileUtil.loadFileToObject(fileDirectory, fileName, extension)
	// }

	private static boolean aRun(String workspace) {
		boolean failure = false;
		try {
			URL classUrl = new URL("file://" + workspace + "/WebContent/WEB-INF/classes/br/ufrn/tests");
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
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}