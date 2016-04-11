package br.ufrn.dimap.rtquality.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.ufrn.dimap.rtquality.history.History;
import br.ufrn.dimap.rtquality.history.Project;
import br.ufrn.dimap.rtquality.history.SVNConfig;
import br.ufrn.dimap.rtquality.history.Study;
import br.ufrn.dimap.rtquality.util.ProjectUtil;
import br.ufrn.dimap.ttracker.data.MethodData;
import br.ufrn.dimap.ttracker.data.MethodState;
import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.Task;
import br.ufrn.dimap.ttracker.data.TestCoverage;
import br.ufrn.dimap.ttracker.data.TestCoverageMapping;
import br.ufrn.dimap.ttracker.util.FileUtil;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class JumpTask implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public JumpTask() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(window.getShell(), "TestTracker", "(Função desativada) Pulando a tarefa atual.");
//		String taskId = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString()+"/config/currentTask.txt"));
//		File file = new File(iWorkspace.getRoot().getLocation().toString()+"/result");
//		if(!file.exists())
//			file.mkdir();
//		file = new File(iWorkspace.getRoot().getLocation().toString()+"/result/RMV_"+taskId+".tcm");
//
//		try {
//			file.createNewFile();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		IWorkspace iWorkspace = ResourcesPlugin.getWorkspace();
		String iWorkspaceFolder = iWorkspace.getRoot().getLocation().toString();
		String resultPath = iWorkspaceFolder + "/result";
		//TODO: Recuperar o descobrir o problema do keySet do MethodStatePool
		String start = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString()+"/result/startDone.txt"));
		String end = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString()+"/result/endDone.txt"));
		boolean startDone = (start != null && start.equals("1")) ? true : false;
		boolean endDone = (end != null && end.equals("1")) ? true : false;
		if(!startDone) {
			TestCoverageMapping tcm = ProjectUtil.getTestCoverageMapping(resultPath, "TCM_153085");
			System.out.println(tcm.seeNextGroupId());
			tcm.getNextGroupId();
			tcm.setBuilding(false);
			FileUtil.saveTextToFile("0", resultPath, "building", "txt");
			tcm.save();
		}
		else if(!endDone) {
			TestCoverageMapping tcm = ProjectUtil.getTestCoverageMapping(resultPath, "TCM_160277");
			System.out.println(tcm.seeNextGroupId());
			tcm.getNextGroupId();
			tcm.setBuilding(false);
			FileUtil.saveTextToFile("0", resultPath, "building", "txt");
			tcm.save();
		}
//		FileUtil.saveObjectToFile(tcm, resultPath, "TCM_157172", "tcm");
//		Iterator<TestCoverage> i = tcm.getTestCoverages().iterator();
//		while(i.hasNext()) {
//			TestCoverage tc = i.next();
//			String signature = tc.getTestData().getSignature();
//			if(signature.contains("/")) {
//				signature = signature.substring(signature.indexOf(".")+1);
//				tc.getTestData().setSignature(signature);
//			}
//			if(signature.contains("<"))
//				System.out.println("Aqui!");
//		}
//		
//		TestCoverageMapping tcm2 = (TestCoverageMapping) FileUtil.loadObjectFromFile(resultPath, "TCM_153085", "tcm");
//		Iterator<TestCoverage> i2 = tcm2.getTestCoverages().iterator();
//		while(i2.hasNext()) {
//			TestCoverage tc = i2.next();
//			String signature = tc.getTestData().getSignature();
//			if(signature.contains("/")) {
//				signature = signature.substring(signature.indexOf(".")+1);
//				tc.getTestData().setSignature(signature);
//			}
//			if(signature.contains("<"))
//				System.out.println("Aqui");
//		}
		
//		FileUtil.saveObjectToFile(tcm2, resultPath, "TCM_157172", "tcm");
		
//		for(Map<String, MethodData> map : tcm.getMethodStatePool().values()) {
//			map.get(map.keySet().iterator().next());
//		}
		
//		String covered = FileUtil.loadTextFromFile(new File(resultPath+"/allCoveredMethods.txt"));
//		String modified = FileUtil.loadTextFromFile(new File(resultPath+"/allModifiedMethods.txt"));
//		String modBib = "";
//		for(String mod : modified.split("\n")) {
//			if(mod.contains("biblioteca"))
//				modBib += mod+"\n";
//		}
//		FileUtil.saveTextToFile(modBib, resultPath, "allBibModified", "txt");
//		int cont = 0;
//		String out = "";
//		for(String mod : modified.split("\n")) {
//			if(mod.contains("biblioteca")) {
//				cont++;
//				out += mod+"\n";
//			}
//		}
//		for(String cov : covered.split("\n")) {
//			for(String mod : modified.split("\n")) {
//				if(cov.contains(mod)) {
//					cont++;
//					out += cov+" = "+mod+"\n";
//				}
//			}
//		}
//		out += "Quantidade de métodos correspondentes: "+cont+".";
//		FileUtil.saveTextToFile(out, resultPath, "saida", "txt");
		
//		sizesOfSets(resultPath);
		
//		removeProjectName(tcm1);
//		removeProjectName(tcm2);
		
//		System.out.println("Aqui!");
//		Set<String> allModified = new HashSet<String>(tcm1.getMethodStatePool().get(new MethodState(true,true)).keySet());
//		allModified.addAll(tcm1.getMethodStatePool().get(new MethodState(false,true)).keySet());
//		String all = "";
//		for(String signature : allModified) {
//			all += signature+"\n";
//		}
//		FileUtil.saveTextToFile(all, resultPath, "allCoveredMethods", "txt");
		
//		List<Revision> revisionForCheckout = new ArrayList<Revision>();
//		String mM1 = "/SIGAA.List<SolicitacaoOrientacao> br.ufrn.sigaa.biblioteca.informacao_referencia.dao.SolicitacaoOrientacaoDAO.findSolicitacoesAtivas(Pessoa, Discente, Servidor, Integer, String, Biblioteca, Date, Date, boolean, TipoSituacao[])";
//		String mM2 = "/SIGAA.List<SolicitacaoCatalogacao> br.ufrn.sigaa.biblioteca.informacao_referencia.dao.SolicitacaoServicoDocumentoDAO.findSolicitacoesCatalogacaoAtivas(Pessoa, Discente, Servidor, Integer, String, TipoDocumentoNormalizacaoCatalogacao, Biblioteca, Date, Date, boolean, TipoSituacao[])";
//		String mM3 = "/SIGAA.String br.ufrn.sigaa.biblioteca.informacao_referencia.jsf.SolicitacaoServicoDocumentoMBean.buscarSolicitacoesSolicitadas()";
//		String mM4 = "/SIGAA.String br.ufrn.sigaa.biblioteca.informacao_referencia.jsf.SolicitacaoOrientacaoMBean.buscarSolicitacoesSolicitadas()";
//		tcm1.getMethodStatePool().get(new MethodState(true,true));
//		tcm1.getMethodPool().get(mM1);
//		tcm1.getMethodPool().get(mM2);
//		tcm1.getMethodPool().get(mM3);
//		tcm1.getMethodPool().get(mM4);
//		tcm2.getMethodPool().get(mM1);
//		tcm2.getMethodPool().get(mM2);
//		tcm2.getMethodPool().get(mM3);
//		tcm2.getMethodPool().get(mM4);
//		for(String mS : tcm1.getMethodPool().keySet()) {
//			if(mS.startsWith(mM1) || mS.startsWith(mM2) || mS.startsWith(mM3) || mS.startsWith(mM4))
//				System.out.println(mS);
//		}
//		try {
//			List<Task> tasks = loadTasks(revisionForCheckout, iWorkspace.getRoot().getLocation().toString() + "/config");
//			Map<Integer, Project> projects = loadProjectsManually();
//			Revision startRevision = new Revision();
//			Revision endRevision = new Revision();
//			SVNConfig sVNConfig = loadSVNConfig(iWorkspace, projects);
//			History history = new History(sVNConfig, iWorkspace);
//			history.getStartAndEndRevisions(startRevision, endRevision);
//			Study study = new Study(startRevision, endRevision, tasks);
//			Set<String> modifiedMethods = study.getTasksModifiedMethods();
//			System.out.println("\nModifiedMethods:");
//			for(String method : modifiedMethods)
//				System.out.println(method);
//			System.out.println("");
//			System.out.println("");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}

	private void removeProjectName(TestCoverageMapping tcm) {
		Map<String, MethodData> methodPoolCopy = new HashMap<String, MethodData>(tcm.getMethodPool());
		for(String methodSignature : tcm.getMethodPool().keySet()) {
			MethodData methodData = methodPoolCopy.remove(methodSignature);
			String oldSignature = methodData.getSignature();
			methodData.setSignature(oldSignature.substring(oldSignature.indexOf(".")+1));
			methodPoolCopy.put(methodData.getSignature(), methodData);
			tcm.getMethodStatePool().get(methodData.getMethodState()).remove(oldSignature);
			tcm.getMethodStatePool().get(methodData.getMethodState()).put(methodData.getSignature(), methodData);
		}
		tcm.setMethodPool(methodPoolCopy);
		tcm.save();
	}

	private SVNConfig loadSVNConfig(IWorkspace iWorkspace, Map<Integer, Project> projects) throws Exception {
		String URL = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString() + "/config/URL.txt"));
		String usuario = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString() + "/config/usuario.txt"));
		String senha = FileUtil.loadTextFromFile(new File(iWorkspace.getRoot().getLocation().toString() + "/config/senha.txt"));
		return new SVNConfig(URL, projects, usuario, senha);
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