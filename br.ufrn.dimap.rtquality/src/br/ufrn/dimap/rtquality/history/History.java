package br.ufrn.dimap.rtquality.history;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import br.ufrn.dimap.rtquality.plugin.Activator;
import br.ufrn.dimap.rtquality.plugin.SysOutProgressMonitor;
import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.Task;
import br.ufrn.dimap.ttracker.data.TaskType;
import br.ufrn.dimap.ttracker.util.FileUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class History {
	private SVNConfig sVNConfig;
	private SVNRepository repository;
	private IWorkspace iWorkspace;
	
	private static final String LIBRARY = "1";
	private static final String CONTAINER = "2";
	private static final String PROJECT = "3";
	
	private static final String FINALIZADA = "FINALIZADA";
	
	public History(SVNConfig sVNConfig, IWorkspace iWorkspace) throws SVNException{
		this.sVNConfig = sVNConfig;
		this.iWorkspace = iWorkspace;
		setupLibrary();
		createRepository(sVNConfig.getSvnUrl());
	}

	private void createRepository(String svnUrl) throws SVNException {
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(sVNConfig.getUserName(), sVNConfig.getPassword());
		repository.setAuthenticationManager(authManager);
	}
	
    public Set<String> getChangedMethodsSignatures(String projectPath, Integer oldRevision, Integer currentRevision) throws SVNException, IOException {
    	Collection<UpdatedMethod> updatedMethods = getChangedMethods(projectPath,oldRevision,currentRevision);
    	Set<String> changedMethodsSignatures = new HashSet<String>(); 
    	for (UpdatedMethod m : updatedMethods)
    		changedMethodsSignatures.add(m.getMethodLimit().getSignature());
    	return changedMethodsSignatures;
    }
	
    public Set<String> getChangedMethodsSignaturesFromProjects(Set<Project> projects, Integer oldRevision, Integer currentRevision) throws SVNException, IOException {
    	Set<String> changedMethodsSignatures = new HashSet<String>(); 
    	for(Project project : projects) {
    		for(String changedMethodSignature : getChangedMethodsSignatures(project.getPath(), oldRevision, currentRevision))
    			changedMethodsSignatures.add(changedMethodSignature);
    	}
    	return changedMethodsSignatures;
    }
	
    public void generateTasksXML() {
    	File xmlFile = new File(iWorkspace.getRoot().getLocation().toString()+"/config/Tasks.xml");
    	if(!xmlFile.exists()) {
			try {
				Revision headRevision = getHeadRevision("/tags/SIGAA 3.11.24");
				Revision startRevision = getNextRevision("/branches/producao/SIGAA", headRevision);
//				Revision endRevision = getPreviousRevision("/branches/producao/SIGAA", getHeadRevision("/tags/SIGAA 3.12.18"));
				Revision endRevision = getPreviousRevision("/branches/producao/SIGAA", getHeadRevision("/tags/SIGAA 3.12.36"));
				LinkedList<SVNLogEntry> entries = getSVNLogEntries("/branches/producao/SIGAA", startRevision, endRevision);
				List<Task> tasks = new ArrayList<Task>();
				for(Iterator<SVNLogEntry> iterator = entries.iterator(); iterator.hasNext(); ) {
					SVNLogEntry svnLogEntry = iterator.next();
					Integer taskId = Integer.valueOf(String.valueOf(getTaskNumberFromLogMessage(svnLogEntry.getMessage())));
					if(taskId > 0) {
						Task task = new Task(taskId);
						if(!tasks.contains(task))
							tasks.add(task);
					}
				}
				populateTasksTypeById(tasks);
				groupEliminateSortSaveTasks(tasks);
			} catch (SVNException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void getStartAndEndRevisions(Revision startRevision, Revision endRevision) {
		try {
			Revision infoStartRevision = getPreviousRevision("/branches/producao/SIGAA", getHeadRevision("/tags/SIGAA 3.11.24"));
//			Revision infoEndRevision = getPreviousRevision("/branches/producao/SIGAA", getHeadRevision("/tags/SIGAA 3.12.18"));
			Revision infoEndRevision = getPreviousRevision("/branches/producao/SIGAA", getHeadRevision("/tags/SIGAA 3.12.36"));
			startRevision.setId(infoStartRevision.getId());
			endRevision.setId(infoEndRevision.getId());
		} catch (SVNException e) {
			e.printStackTrace();
		}
    }

	private Revision getHeadRevision(String url) throws SVNException {
		return new Revision(Integer.valueOf(String.valueOf(repository.info(url, -1).getRevision())));
	}
	
	private Revision getPreviousRevision(String path, Revision revision) throws SVNException {
		LinkedList<SVNLogEntry> entries = getSVNLogEntries(path, new Revision(revision.getId()-1000), revision);
		Revision previousValidRevision = new Revision(revision.getId()-1000);
		Iterator<SVNLogEntry> iterator = entries.descendingIterator();
		while(iterator.hasNext()) {
			SVNLogEntry svnLogEntry = iterator.next();
			previousValidRevision = new Revision(Integer.valueOf(String.valueOf(svnLogEntry.getRevision())));
			if(previousValidRevision.getId().compareTo(revision.getId()) < 0)
				break;
		}
		return previousValidRevision;
	}
	
	private Revision getNextRevision(String path, Revision revision) throws SVNException {
		LinkedList<SVNLogEntry> entries = getSVNLogEntries(path, revision, new Revision(revision.getId()+1000));
		Revision nextValidRevision = new Revision(revision.getId()+1000);
		Iterator<SVNLogEntry> iterator = entries.iterator();
		while(iterator.hasNext()) {
			SVNLogEntry svnLogEntry = iterator.next();
			nextValidRevision = new Revision(Integer.valueOf(String.valueOf(svnLogEntry.getRevision())));
			if(nextValidRevision.getId().compareTo(revision.getId()) > 0)
				break;
		}
		return nextValidRevision;
	}

	public void groupEliminateSortSaveTasks(List<Task> tasks) {
		Map<String, List<Task>> taskTypeGroups = groupByType(tasks);
		Map<Integer, List<String>> sizeTaskTypeGroups = eliminateByMinSize(taskTypeGroups);
		List<Integer> ordenada = sortByAmount(sizeTaskTypeGroups);
		saveRemainingTasks(taskTypeGroups, sizeTaskTypeGroups, ordenada);
	}

	private Map<String, List<Task>> groupByType(List<Task> tasks) {
		Map<String,List<Task>> taskTypeGroups = new HashMap<String,List<Task>>();
		for(Task task : tasks) {
			if(task.getType().equals(TaskType.OTHER)) {
				if(taskTypeGroups.containsKey(task.getOtherType()))
					taskTypeGroups.get(task.getOtherType()).add(task);
				else {
					List<Task> newTasks = new ArrayList<Task>(1);
					newTasks.add(task);
					taskTypeGroups.put(task.getOtherType(),newTasks);
				}
			}
			else {
				if(taskTypeGroups.containsKey(task.getType().getName()))
					taskTypeGroups.get(task.getType().getName()).add(task);
				else {
					List<Task> newTasks = new ArrayList<Task>(1);
					newTasks.add(task);
					taskTypeGroups.put(task.getType().getName(),newTasks);
				}
			}
		}
		return taskTypeGroups;
	}

	private Map<Integer, List<String>> eliminateByMinSize(
			Map<String, List<Task>> taskTypeGroups) {
		Map<Integer,List<String>> sizeTaskTypeGroups = new HashMap<Integer,List<String>>();
		for(String taskType : taskTypeGroups.keySet()) {
			if(taskTypeGroups.get(taskType).size() >= 7) {
				if(sizeTaskTypeGroups.containsKey(taskTypeGroups.get(taskType).size()))
					sizeTaskTypeGroups.get(taskTypeGroups.get(taskType).size()).add(taskType);
				else {
					List<String> keys = new ArrayList<String>(1);
					keys.add(taskType);
					sizeTaskTypeGroups.put(taskTypeGroups.get(taskType).size(), keys);
				}
			}
		}
		return sizeTaskTypeGroups;
	}

	private List<Integer> sortByAmount(
			Map<Integer, List<String>> sizeTaskTypeGroups) {
		List<Integer> ordenada = new ArrayList<Integer>(sizeTaskTypeGroups.keySet());
		Collections.sort(ordenada);
		return ordenada;
	}

	private void saveRemainingTasks(Map<String, List<Task>> taskTypeGroups,
			Map<Integer, List<String>> sizeTaskTypeGroups,
			List<Integer> ordenada) {
		List<Task> finalTasksSelection = new ArrayList<Task>();
		String print = "Tipos das Tarefas x Quantidade de Tarefas\n";
		for(int i=ordenada.size()-1; i>=0; i--) {
			Integer qtd = ordenada.get(i);
			for(String taskType : sizeTaskTypeGroups.get(qtd)) {
				finalTasksSelection.addAll(taskTypeGroups.get(taskType)); //Popula lista final ordenada pela quantidade
				print += "\t"+taskType+" x "+qtd+"\n";
			}
		}
		FileUtil.saveTextToFile(print, iWorkspace.getRoot().getLocation().toString()+"/config", "TaskPrint", "txt");
		XStream xstream = new XStream(new DomDriver());
		String xmlText = xstream.toXML(finalTasksSelection);
		FileUtil.saveTextToFile(xmlText, iWorkspace.getRoot().getLocation().toString()+"/config", "Tasks", "xml");
	}
    
	public void populateTasksTypeById(List<Task> tasks) {
		if(tasks != null) {
			Set<Task> tasksToRemove = new HashSet<Task>();
			Connection connection = null;
			ResultSet rs1 = null;
			PreparedStatement stmt1 = null;
			ResultSet rs2 = null;
			PreparedStatement stmt2 = null;
			try {
				if (connection == null) {
					connection = DriverManager.getConnection(
//							"jdbc:postgresql://localhost:5432/sistemas_comum_3_11_24",
							"jdbc:postgresql://bddesenv1.info.ufrn.br:5432/sistemas_comum_20140204",
//							"postgres", "1234");
							"comum_user", "comum_user");
				}
				for(Task task : tasks) {
					if(tasksToRemove.contains(task))
						continue;
					stmt1 = connection.prepareStatement(
							"SELECT tipo_tarefa.denominacao tipo "+
							"FROM iproject.tarefa "+
							"INNER JOIN iproject.tipo_tarefa ON tarefa.id_tipo_tarefa = tipo_tarefa.id_tipo_tarefa "+
							"INNER JOIN iproject.status_tarefa ON tarefa.id_status = status_tarefa.id "+
							"WHERE tarefa.numtarefa = ? AND status_tarefa.denominacao = 'FINALIZADA'");
					stmt1.setLong(1, task.getId());
					rs1 = stmt1.executeQuery();
					if(rs1.next()) {
						task.setType(TaskType.getTaskTypeByName(rs1.getString("tipo")));
						if(task.getType().equals(TaskType.OTHER))
							task.setOtherType(rs1.getString("tipo"));
						
						stmt2 = connection.prepareStatement(
								"SELECT substring(log_tarefa.log, '([Revis„o|revis„o|Revisao|revisao]+[:| ]*[0-9]+)') revisao "+
								"FROM iproject.log_tarefa "+
								"INNER JOIN iproject.tarefa ON log_tarefa.id_tarefa = tarefa.id_tarefa "+
								"INNER JOIN iproject.tipo_tarefa ON tarefa.id_tipo_tarefa = tipo_tarefa.id_tipo_tarefa "+
								"WHERE tarefa.numtarefa = ? AND log_tarefa.log ~ '([Revis„o|revis„o|Revisao|revisao]+[:| ]*[0-9]+)'");
						stmt2.setLong(1, task.getId());
						rs2 = stmt2.executeQuery();
						while (rs2.next()) {
							String revisionText = rs2.getString("revisao");
							Integer revisionId = null;
							if(revisionText.startsWith("Revisao") || revisionText.startsWith("Revis„o") ||
									revisionText.startsWith("revisao") || revisionText.startsWith("revis„o")){
								revisionText = revisionText.substring(7);
								while(revisionText.length() > 0) {
									try{
										revisionId = Integer.valueOf(revisionText);
										break;
									} catch (Exception e) {
										revisionText = revisionText.substring(1, revisionText.length());
									}
								}
								if(revisionId == null)
									continue;
							}
							else
								continue;
							Revision revision = new Revision(revisionId);
							try {
								Revision previousRevision = getPreviousRevision("/trunk/SIGAA", revision);
								revision.setOldRevision(previousRevision);
								Set<Revision> revisionsWithOutCopies = new HashSet<Revision>(task.getRevisions());
								revisionsWithOutCopies.add(revision);
								task.setRevisions(new ArrayList<Revision>(revisionsWithOutCopies));
							} catch (SVNException e) {
								System.out.println("A revis„o "+revision+" n„o possui revis„o anterior.");
							}
						}
						if(task.getRevisions().isEmpty())
							tasksToRemove.add(task);
					}
					else
						tasksToRemove.add(task);
				}
				tasks.removeAll(tasksToRemove);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(rs1 != null) {
					try {
						rs1.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(stmt1 != null) {
					try {
						stmt1.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(rs2 != null) {
					try {
						rs2.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(stmt2 != null) {
					try {
						stmt2.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
    
    public static long getTaskNumberFromLogMessage(String logMessage) {
		Scanner in = new Scanner(logMessage);
		
		String task_word = in.next();
		String task_value = in.next().replaceAll("[^0-9]", "");

		in.close();
		
		long task_number;
		
		if (task_word.equalsIgnoreCase("commit"))
			task_number = -2;
		else if (task_word.equalsIgnoreCase("tarefa") || task_word.equals("#")) 
			task_number = Long.parseLong(task_value);
		else if (task_word.matches("#[0-9]+")) {
			task_value = task_word.replaceAll("#", "");
			task_number = Long.parseLong(task_value);
		}
		else
			task_number = -1;
		
		return task_number;
	}
    
    public Integer checkouOrUpdateProjects(Revision revision) throws SVNException, CoreException, IOException { //TODO: utilizar Long para a revis„o e n„o Integer
    	SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		SVNUpdateClient sVNUpdateClient = client.getUpdateClient();
//		Map<Project,File> projectFile = new HashMap<Project,File>(sVNConfig.getProjects().size());
    	for(int i=1;i<=sVNConfig.getProjects().size();i++) {
    		Project project = sVNConfig.getProjects().get(i);
    		project.setIProject(iWorkspace.getRoot().getProject(project.getName())); //O projeto n„o precisa existir no workspace para setar esta informaÁ„o.
			IProject iProject = project.getIProject();
			if(iProject.exists()) {
				if(iProject.isOpen()) {
					iProject.build(IncrementalProjectBuilder.CLEAN_BUILD, new SysOutProgressMonitor());
					iProject.close(new SysOutProgressMonitor());
				}
				iProject.delete(true, new SysOutProgressMonitor());
			}
			if(project.isForCheckout()) {
				File file = new File(iWorkspace.getRoot().getLocation().toString()+project.getName());
				if(file.exists()) {
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
				SVNNodeKind node = repository.checkPath(project.getPath(), revision.getId());
				Revision newRevision = new Revision(0);
		    	if(node.equals(SVNNodeKind.NONE)) {
		    		LinkedList<SVNLogEntry> entries = getSVNLogEntries(project.getPath(), new Revision(0), revision);
		    		if(!entries.isEmpty())
		    			newRevision = new Revision(Integer.valueOf(String.valueOf(entries.peekLast().getRevision())));
		    		if(!newRevision.getId().equals(0))
		    			revision = newRevision;
		    	}
		    	if(!node.equals(SVNNodeKind.NONE) || !newRevision.getId().equals(0)) {
		    		file.mkdir();
		    		System.out.println("\t\tBaixando o projeto: "+project.getName());
		    		sVNUpdateClient.doCheckout(SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+project.getPath()),
		    				file, SVNRevision.create(revision.getId()-1), SVNRevision.create(revision.getId()), SVNDepth.INFINITY, true);
		    		client.dispose();
		    		FileUtil.saveTextToFile(revision.getId().toString(), iWorkspace.getRoot().getLocation().toString()+"/config", "currentRevision", "txt");
		    		project.getProjectRevisionInformations().setRevision(revision.getId());
		    		//			else if(!project.getProjectRevisionInformations().getRevision().equals(revision)) {
		    		//				projectFile.put(project,file);
		    		////				sVNWCClient.doCleanup(file);
		    		//			}
		    	}
			}
//			else
//				copyFile(project.getPath(), project.getName());
    	}
//    	if(!projectFile.isEmpty()){
//    		sVNUpdateClient.doUpdate(projectFile.values().toArray(new File[0]), SVNRevision.create(revision), SVNDepth.INFINITY, true, true);
//    		for(Project project : projectFile.keySet())
//    			project.getProjectRevisionInformations().setRevision(revision);
//    	}
		return importConfigureRefreshBuild();
    }

	@SuppressWarnings("unchecked")
	private LinkedList<SVNLogEntry> getSVNLogEntries(String path, Revision startRevision, Revision endRevision) {
		try {
			String paths[] = {path};
			return (LinkedList<SVNLogEntry>) repository.log(paths, null, startRevision.getId(), endRevision.getId(), false, true);
		} catch (SVNException e) {
			return new LinkedList<SVNLogEntry>();
		}
	}
    
	private Integer importConfigureRefreshBuild() throws CoreException, IOException {
//		Map<String,ProjectRevisionInformations> projectRevisionInformations = new HashMap<String,ProjectRevisionInformations>();
		for(int i=1;i<=sVNConfig.getProjects().size();i++) {
    		Project project = sVNConfig.getProjects().get(i);
//			if(project.getProjectRevisionInformations().getRevisionBuilded().equals(project.getProjectRevisionInformations().getRevision()))
//				continue;
			importProject(project);
			if(project.isAspectJNature())
				configureAspectJ(project.getIProject());
			else if(project.getName().equals("/LIBS")) { //TODO: CÛdigo especÌfico para o SIGAA
				changeLib(project, "aspectjrt*.jar", "aspectjrt.jar");
				changeLib(project, "aspectjweaver*.jar", "aspectjweaver.jar");
			}
			project.getIProject().refreshLocal(IResource.DEPTH_INFINITE, new SysOutProgressMonitor());
//			Integer retorno = buildingProject(project.getIProject());
			project.getProjectRevisionInformations().setRevisionBuilded(project.getProjectRevisionInformations().getRevision());
//			if(retorno.equals(-1))
//				return -1;
//			projectRevisionInformations.put(project.getPath(), project.getProjectRevisionInformations());
		}
		return 0;
//		FileUtil.saveObjectToFile(projectRevisionInformations, iWorkspace.getRoot().getLocation().toString()+"/config", "Projects", "obj");
	}

	private void changeLib(Project project, String oldLib, String newLib) { //TODO: CÛdigo especÌfico para o SIGAA, torn·-lo mais genÈrico
		File destino = new File(project.getIProject().getLocation().toString()+"/app/libs.jar");
		FileFilter fileFilter = new WildcardFileFilter(oldLib);
		File[] filesDestino = destino.listFiles(fileFilter);
		for (File fileDestino : filesDestino) {
			String fileNameDestino = fileDestino.getName();
			fileDestino.delete();
			copyFile(project.getIProject(), "/lib/"+newLib, "/app/libs.jar/"+fileNameDestino);
		}
	}

	private Integer buildingProject(IProject iProject) throws CoreException {
		SysOutProgressMonitor.out.println("Building eclipse project: " + iProject.getName());
		iProject.build(IncrementalProjectBuilder.FULL_BUILD, new SysOutProgressMonitor());
		IMarker[] problems = iProject.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		for(IMarker iMarker : problems){
			if(iMarker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO) == IMarker.SEVERITY_ERROR) {
				System.out.println(iMarker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO));
				return -1;
			}
		}
		SysOutProgressMonitor.out.println("Eclipse project builded");
		return 0;
	}
    
    private void importProject(Project project) throws CoreException {
    	final IProject iProject = project.getIProject();
    	SysOutProgressMonitor.out.println("Importing eclipse project: " + iProject.getName());
    	InputStream inputStream = null;
    	try {
			inputStream = new FileInputStream(iWorkspace.getRoot().getLocation().toString()+"/"+iProject.getName()+"/.project");
			final IProjectDescription iProjectDescription = iWorkspace.loadProjectDescription(inputStream);
			iWorkspace.run(new IWorkspaceRunnable() {
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					// create project as java project
					if ( !iProject.exists()) {
						iProjectDescription.setLocation(null);
						iProject.create(iProjectDescription, monitor);
						iProject.open(IResource.NONE, monitor);
					}
				}
			}, iWorkspace.getRoot(), IWorkspace.AVOID_UPDATE, new SysOutProgressMonitor());
			SysOutProgressMonitor.out.println("Eclipse project imported");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
    		if(inputStream != null) {
    			try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    	
    }
    
    private void configureAspectJ(IProject iProject) throws CoreException, IOException {
//    	copyFile(iProject, "/lib/aspectjweaver.jar");
//    	copyFile(iProject, "/lib/ttracker.jar");
//    	iProject.refreshLocal(IResource.DEPTH_INFINITE, new SysOutProgressMonitor()); //TODO: Verificar se comentar essa linha deu certo
    	SysOutProgressMonitor.out.println("Adding AspectJ nature");
    	IProjectDescription iProjectDescription = iProject.getDescription();
		String ajNature = "org.eclipse.ajdt.ui.ajnature";
		if(!iProjectDescription.hasNature(ajNature)){
			List<String> natureIds = Arrays.asList(iProjectDescription.getNatureIds());
			List<String> natureIdsList = new ArrayList<String>(natureIds.size()+1);
			natureIdsList.addAll(natureIds);
			natureIdsList.add(ajNature);
			iProjectDescription.setNatureIds(natureIdsList.toArray(new String[natureIdsList.size()]));
		}
		iProject.setDescription(iProjectDescription, IProject.FORCE, new SysOutProgressMonitor());
		SysOutProgressMonitor.out.println("AspectJ nature added");
		
    	SysOutProgressMonitor.out.println("Configuring classpath to support AspectJ");
    	IJavaProject iJavaProject = JavaCore.create(iProject);
    	
    	IAccessRule iAccessRule[] = {};
//    	IClasspathAttribute restriction = JavaCore.newClasspathAttribute("org.eclipse.ajdt.inpath.restriction", "aspectjweaver.jar");
    	IClasspathAttribute ajdtInpath = JavaCore.newClasspathAttribute("org.eclipse.ajdt.inpath", "org.eclipse.ajdt.inpath");
//    	IClasspathAttribute restrictions[] = {restriction};
    	IClasspathAttribute ajdtInpaths[] = {ajdtInpath};
    	IProject tTrackerIProject = iProject.getWorkspace().getRoot().getProject("/br.ufrn.dimap.ttracker");
//		IClasspathEntry aspectjContainer = JavaCore.newContainerEntry(new Path("org.eclipse.ajdt.core.ASPECTJRT_CONTAINER")); //TODO: Quando o projeto n„o possuir AspectJ, nem a natureza nem o .jar descomentar esta linha
//		IClasspathEntry aspectjJar = JavaCore.newLibraryEntry(new Path(iWorkspace.getRoot().getLocation().toString()+"/LIBS/app/libs.jar/aspectjrt-1.2.1.jar"), null, null, iAccessRule, ajdtInpaths, false); //TODO: Quando o projeto j· tiver o AspectJ, seja a natureza ou o .jar, n„o adicionar nada
//		IClasspathEntry aspectjweaverJar = JavaCore.newLibraryEntry(new Path(iProject.getFullPath().toString()+"/lib/aspectjweaver.jar"), null, null);
		IClasspathEntry testtrackerJar = JavaCore.newProjectEntry(tTrackerIProject.getFullPath(), iAccessRule, false, ajdtInpaths, false);
//		IClasspathEntry requiredPlugins = JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"),iAccessRule,restrictions,false);
		
		List<IClasspathEntry> rawClasspath = Arrays.asList(iJavaProject.getRawClasspath());
		List<IClasspathEntry> classpathEntriesList = new ArrayList<IClasspathEntry>(rawClasspath.size()+3); //TODO: precisa ser 3?
		classpathEntriesList.addAll(rawClasspath);
		
//		if(!classpathEntriesList.contains(aspectjContainer)){
//			SysOutProgressMonitor.out.println("Adding AspectJ container");
//			classpathEntriesList.add(aspectjContainer);
//		}
		
		//TODO: Verificar se este if-else s√£o necess√°rios ou se o AspectJ roda sem isso
//		addClasspathEntryWithAttribute(restriction, requiredPlugins, classpathEntriesList, CONTAINER);
		addClasspathEntryWithAttribute(ajdtInpath, testtrackerJar, classpathEntriesList, PROJECT);
		
//		if(!classpathEntriesList.contains(aspectjweaverJar)){
//			SysOutProgressMonitor.out.println("Adding AspectJ Weaver jar");
//			classpathEntriesList.add(aspectjweaverJar);
//		}
		iJavaProject.setRawClasspath(classpathEntriesList.toArray(new IClasspathEntry[classpathEntriesList.size()]), new SysOutProgressMonitor());
		iJavaProject.save(new SysOutProgressMonitor(), true);
		SysOutProgressMonitor.out.println("Classpath configured");
    }

	/**
	 * @throws IOException
	 */
	public static String findResolveURL(String relativePath) throws IOException {
		URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(relativePath), null);
    	url = FileLocator.resolve(url);
    	return url.getPath();
	}

	/**
	 * @param attribute
	 * @param classpathEntry
	 * @param classpathEntriesList
	 * @param type
	 */
	private void addClasspathEntryWithAttribute(IClasspathAttribute attribute, IClasspathEntry classpathEntry,
			List<IClasspathEntry> classpathEntriesList, final String type) {
		IClasspathEntry classpathEntryTemp = null;
		for (IClasspathEntry iClasspathEntry : classpathEntriesList) {
			if(iClasspathEntry.getPath().equals(classpathEntry.getPath())){
				classpathEntryTemp = iClasspathEntry;
				break;
			}
		}
		if(classpathEntryTemp != null){
			classpathEntriesList.remove(classpathEntryTemp);
			List<IClasspathAttribute> iClasspathAttributes = Arrays.asList(classpathEntryTemp.getExtraAttributes());
			List<IClasspathAttribute> iClasspathAttributesList = new ArrayList<IClasspathAttribute>(iClasspathAttributes.size()+1);
			iClasspathAttributesList.addAll(iClasspathAttributes);
			if(!iClasspathAttributesList.contains(attribute)){
				SysOutProgressMonitor.out.println("Adding attribute to the classpath entry");
				iClasspathAttributesList.add(attribute);
				switch(type){
				case PROJECT:
					classpathEntry = JavaCore.newProjectEntry(classpathEntryTemp.getPath());
					break;
				case LIBRARY:
					classpathEntry = JavaCore.newLibraryEntry(classpathEntryTemp.getPath(), null, null, classpathEntryTemp.getAccessRules(), iClasspathAttributesList.toArray(new IClasspathAttribute[iClasspathAttributesList.size()]), classpathEntryTemp.isExported());
					break;
				case CONTAINER:
					classpathEntry = JavaCore.newContainerEntry(classpathEntryTemp.getPath(),classpathEntryTemp.getAccessRules(),iClasspathAttributesList.toArray(new IClasspathAttribute[iClasspathAttributesList.size()]),classpathEntryTemp.isExported());
					break;
				}
			}
			classpathEntriesList.add(classpathEntry);
		}
		else{
			SysOutProgressMonitor.out.println("Adding classpath entry with the attribute");
			classpathEntriesList.add(classpathEntry);
		}
	}

	/**
	 * @param iProject
	 * @param aspectjweaver
	 */
	private void copyFile(IProject iProject, String relativePath) {
		InputStream in = null;
		OutputStream out = null;
		try{
    	    File f1 = new File(findResolveURL(relativePath));
    	    File f2 = new File(iProject.getLocation().toString()+"/lib");
    	    File f3 = new File(iProject.getLocation().toString()+relativePath);
    		if(!f2.exists())
    			f2.mkdir();
    		in = new FileInputStream(f1);
    		out = new FileOutputStream(f3);
    		byte[] buf = new byte[1024];
    		int len;
    		while ((len = in.read(buf)) > 0){
    			out.write(buf, 0, len);
    		}
    	} catch(FileNotFoundException ex){
    		ex.printStackTrace();
    	} catch(IOException e){
    		e.printStackTrace();
    	} finally {
    		if(in != null) {
    			try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
	}
	
	private void copyFile(IProject iProject, String origem, String destino) {
		InputStream in = null;
		OutputStream out = null;
		try{
    	    File f1 = new File(findResolveURL(origem));
    	    File f2 = new File(iProject.getLocation().toString()+destino);
    		in = new FileInputStream(f1);
    		out = new FileOutputStream(f2);
    		byte[] buf = new byte[1024];
    		int len;
    		while ((len = in.read(buf)) > 0){
    			out.write(buf, 0, len);
    		}
    	}
    	catch(FileNotFoundException ex){
    		ex.printStackTrace();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	} finally {
    		if(in != null) {
    			try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
	}
	
	private void copyFile(String origem, String destino) {
		InputStream in = null;
		OutputStream out = null;
		try{
    	    File f1 = new File(origem);
    	    File f2 = new File(iWorkspace.getRoot().getLocation().toString()+destino);
    		in = new FileInputStream(f1);
    		out = new FileOutputStream(f2);
    		byte[] buf = new byte[1024];
    		int len;
    		while ((len = in.read(buf)) > 0){
    			out.write(buf, 0, len);
    		}
    	}
    	catch(FileNotFoundException ex){
    		ex.printStackTrace();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	} finally {
    		if(in != null) {
    			try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
	}

    public File checkoutFile(String projectPath, String fileName, long revision) throws SVNException{
    	SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		SVNUpdateClient sVNUpdateClient = client.getUpdateClient();
		File file = new File(fileName);
		sVNUpdateClient.doCheckout(SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+projectPath+fileName),
				file, SVNRevision.create(0), SVNRevision.create(revision), SVNDepth.INFINITY, true);
    	return file;
    }

	private Collection<UpdatedMethod> getChangedMethods(String projectPath, Integer startRevision, Integer endRevision) throws SVNException, IOException {
		Collection<UpdatedMethod> updatedMethods = new ArrayList<UpdatedMethod>();
		SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		SVNDiffClient diffClient = client.getDiffClient();
		TestTrackerSVNDiffGenerator testTrackerSVNDiffGenerator = new TestTrackerSVNDiffGenerator();
		diffClient.setDiffGenerator(testTrackerSVNDiffGenerator);
		FileOutputStream fOS = null;
		try {
			String paths[] = {projectPath};
			SVNNodeKind endNode = repository.checkPath(projectPath, endRevision);
			SVNNodeKind startNode = repository.checkPath(projectPath, startRevision);
	    	if(!endNode.equals(SVNNodeKind.NONE)) {
	    		if(startNode.equals(SVNNodeKind.NONE)) {
	    			Integer newRevision = 0;
	    			LinkedList<SVNLogEntry> entries = getSVNLogEntries(projectPath, new Revision(0), new Revision(endRevision-1)); //TODO: este mÈtodo funciona com uma endRevision Inv·lida?
	    			if(!entries.isEmpty())
	    				newRevision = Integer.valueOf(String.valueOf(entries.peekLast().getRevision()));
	    			if(newRevision != 0) {
	    				startRevision = newRevision;
	    				startNode = repository.checkPath(projectPath, startRevision);
	    			}
	    		}
	    		if(!startNode.equals(SVNNodeKind.NONE)) {
					File xmlFile = new File("ProjectUpdates.xml");
					fOS = new FileOutputStream(xmlFile);
					startProjectUpdatesXML(testTrackerSVNDiffGenerator, fOS);
					diffClient.doDiff(SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+projectPath),
							SVNRevision.create(startRevision),
							SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+projectPath),
					        SVNRevision.create(endRevision),
					        SVNDepth.INFINITY,
					        true,
					        fOS);
					finishProjectUpdatesXML(testTrackerSVNDiffGenerator, fOS);
					ProjectUpdates projectUpdates = (ProjectUpdates) getObjectFromXML(xmlFile);
					xmlFile.delete();
					updatedMethods = projectUpdates.getUpdatedMethods(startRevision, endRevision);
	    		}
	    	}
			return updatedMethods;
		} finally {
			try {
				File tempFolder = new File("temp");
				if(tempFolder != null && tempFolder.isDirectory())
					FileUtils.deleteDirectory(tempFolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(fOS != null) {
				try {
					fOS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param testTrackerSVNDiffGenerator
	 * @param fOS
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void startProjectUpdatesXML(TestTrackerSVNDiffGenerator testTrackerSVNDiffGenerator,
			FileOutputStream fOS) throws IOException,
			UnsupportedEncodingException {
		fOS.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
		fOS.write(testTrackerSVNDiffGenerator.getEOL());
		fOS.write("<br.ufrn.dimap.rtquality.history.ProjectUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
		fOS.write(testTrackerSVNDiffGenerator.getEOL());
		fOS.write("  <classUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
		fOS.write(testTrackerSVNDiffGenerator.getEOL());
	}

	/**
	 * @param testTrackerSVNDiffGenerator
	 * @param fOS
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void finishProjectUpdatesXML(TestTrackerSVNDiffGenerator testTrackerSVNDiffGenerator,
			FileOutputStream fOS) throws IOException,
			UnsupportedEncodingException {
		fOS.write("  </classUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
		fOS.write(testTrackerSVNDiffGenerator.getEOL());
		fOS.write("</br.ufrn.dimap.rtquality.history.ProjectUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
	}
	
	private Object getObjectFromXML(File xmlFile){
		xmlFile.getAbsolutePath();
		String xml = FileUtil.loadTextFromFile(xmlFile);
		XStream xstream = new XStream(new DomDriver());
		Object object = (Object) xstream.fromXML(xml);
		return object;
	}

    /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        DAVRepositoryFactory.setup(); //For using over http:// and https://
        SVNRepositoryFactoryImpl.setup(); //For using over svn:// and svn+xxx://
        FSRepositoryFactory.setup();//For using over file:///
    }

	public SVNConfig getsVNConfig() {
		return sVNConfig;
	}

	public void setsVNConfig(SVNConfig sVNConfig) {
		this.sVNConfig = sVNConfig;
	}

	public SVNRepository getRepository() {
		return repository;
	}

	public IWorkspace getiWorkspace() {
		return iWorkspace;
	}

	public void setiWorkspace(IWorkspace iWorkspace) {
		this.iWorkspace = iWorkspace;
	}

}