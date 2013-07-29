package br.ufrn.dimap.taskanalyzer.history;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
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

import br.ufrn.dimap.taskanalyzer.plugin.Activator;
import br.ufrn.dimap.taskanalyzer.plugin.actions.SysOutProgressMonitor;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedMethod;

import com.thoughtworks.xstream.XStream;

public class History {
	private SVNConfig sVNConfig;
	private SVNRepository repository;
	private IWorkspace iWorkspace;
	
	private static final String LIBRARY = "1";
	private static final String CONTAINER = "2";
	
	public History(SVNConfig sVNConfig, IWorkspace iWorkspace) throws SVNException{
		this.sVNConfig = sVNConfig;
		this.iWorkspace = iWorkspace;
		setupLibrary();
		createRepository(sVNConfig.getSvnUrl() + sVNConfig.getProjectPath());
	}

	private void createRepository(String svnUrl) throws SVNException {
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(sVNConfig.getUserName(), sVNConfig.getPassword());
		repository.setAuthenticationManager(authManager);
	}
	
    public Set<String> getChangedMethodsSignatures(Integer startRevision, Integer endRevision) {
    	Collection<UpdatedMethod> updatedMethods = getChangedMethods(startRevision,endRevision);
//    	for (UpdatedMethod updatedMethod : updatedMethods) {
//    		System.out.println(updatedMethod.getMethodLimit().getSignature()+" (Revision: "+updatedMethod.getUpdatedLines().get(0).getRevision()+")");
//    		for(UpdatedLine updatedLine : updatedMethod.getUpdatedLines()){
//    			System.out.println("\t"+(updatedLine.getLineNumber()<0 ? "" : "+")+updatedLine.getLineNumber());
//    		}
//		}
    	Set<String> changedMethodsSignatures = new HashSet<String>(); 
    	for (UpdatedMethod m : updatedMethods) {
			changedMethodsSignatures.add(m.getMethodLimit().getSignature());
		}
        return changedMethodsSignatures;
    }
    
    public IProject checkoutProject(long revision) throws SVNException, CoreException, IOException {
    	SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		SVNUpdateClient sVNUpdateClient = client.getUpdateClient();
		String tempDir = iWorkspace.getRoot().getLocation().toString()+sVNConfig.getProjectPath()+"_"+revision;
		File file = new File(tempDir);
		if(!file.exists()){
			file.mkdir();
			sVNUpdateClient.doCheckout(SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+sVNConfig.getProjectPath()),
					file, SVNRevision.create(revision-1), SVNRevision.create(revision), SVNDepth.INFINITY, true);
		}
		IProject iProject = iWorkspace.getRoot().getProject(sVNConfig.getProjectPath()+"_"+revision);
		importProject(iProject);
		configureProject(iProject);
		return iProject;
    }
    
    private void importProject(final IProject iProject) throws CoreException, IOException {
    	SysOutProgressMonitor.out.println("Importing eclipse project: " + iProject.getName());
    	InputStream inputStream = new FileInputStream(iWorkspace.getRoot().getLocation().toString()+"/"+iProject.getName()+"/.project");
    	final IProjectDescription iProjectDescription = iWorkspace.loadProjectDescription(inputStream);
    	
    	iWorkspace.run(new IWorkspaceRunnable() {
    		@Override
    		public void run(IProgressMonitor monitor) throws CoreException {
    			// create project as java project
    			if ( !iProject.exists()) {
    				iProjectDescription.setLocation(null);
    				iProject.create(iProjectDescription, monitor);
    				iProject.open(IResource.NONE, monitor);
    			} else {
    				iProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);
    			}
    		}
    	}, iWorkspace.getRoot(), IWorkspace.AVOID_UPDATE, new SysOutProgressMonitor());
    	
    	copyFile(iProject, "/lib/aspectjweaver.jar");
    	copyFile(iProject, "/lib/testtracker.jar");
    	
    	SysOutProgressMonitor.out.println("Eclipse project imported");

    }

	/**
	 * @throws IOException
	 */
	private String findResolveURL(String relativePath) throws IOException {
		URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(relativePath), null);
    	url = FileLocator.resolve(url);
    	return url.getPath();
	}
    
    private void configureProject(IProject iProject) throws CoreException, IOException {
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
    	
		IClasspathEntry aspectjContainer = JavaCore.newContainerEntry(new Path("org.eclipse.ajdt.core.ASPECTJRT_CONTAINER"));
//		IClasspathEntry aspectjweaverJar = JavaCore.newLibraryEntry(new Path(iProject.getFullPath().toString()+"/lib/aspectjweaver.jar"), null, null);
		IClasspathEntry testtrackerJar = JavaCore.newLibraryEntry(new Path(iProject.getFullPath().toString()+"/lib/testtracker.jar"), null, null, iAccessRule, ajdtInpaths, false);
//		IClasspathEntry requiredPlugins = JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"),iAccessRule,restrictions,false);
		
		List<IClasspathEntry> rawClasspath = Arrays.asList(iJavaProject.getRawClasspath());
		List<IClasspathEntry> classpathEntriesList = new ArrayList<IClasspathEntry>(rawClasspath.size()+3);
		classpathEntriesList.addAll(rawClasspath);
		
		if(!classpathEntriesList.contains(aspectjContainer)){
			SysOutProgressMonitor.out.println("Adding AspectJ container");
			classpathEntriesList.add(aspectjContainer);
		}
		
		//TODO: Verificar se este if-else são necessários ou se o AspectJ roda sem isso
//		addClasspathEntryWithAttribute(restriction, requiredPlugins, classpathEntriesList, CONTAINER);
		addClasspathEntryWithAttribute(ajdtInpath, testtrackerJar, classpathEntriesList, LIBRARY);
		
//		if(!classpathEntriesList.contains(aspectjweaverJar)){
//			SysOutProgressMonitor.out.println("Adding AspectJ Weaver jar");
//			classpathEntriesList.add(aspectjweaverJar);
//		}
		iJavaProject.setRawClasspath(classpathEntriesList.toArray(new IClasspathEntry[classpathEntriesList.size()]), new SysOutProgressMonitor());
		SysOutProgressMonitor.out.println("Classpath configured");
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
		try{
    	    File f1 = new File(findResolveURL(relativePath));
    	    File f2 = new File(iProject.getLocation().toString()+"/lib");
    	    File f3 = new File(iProject.getLocation().toString()+relativePath);
    		if(!f2.exists())
    			f2.mkdir();
    		InputStream in = new FileInputStream(f1);
    		OutputStream out = new FileOutputStream(f3);
    		byte[] buf = new byte[1024];
    		int len;
    		while ((len = in.read(buf)) > 0){
    			out.write(buf, 0, len);
    		}
    		in.close();
    		out.close();
    	}
    	catch(FileNotFoundException ex){
    		ex.printStackTrace();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
	}

    public File checkoutFile(String fileName, long revision) throws SVNException{
    	SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		SVNUpdateClient sVNUpdateClient = client.getUpdateClient();
		File file = new File(fileName);
		sVNUpdateClient.doCheckout(SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+sVNConfig.getProjectPath()+fileName),
				file, SVNRevision.create(0), SVNRevision.create(revision), SVNDepth.INFINITY, true);
    	return file;
    }

	private Collection<UpdatedMethod> getChangedMethods(Integer startRevision, Integer endRevision) {
		Collection<UpdatedMethod> updatedMethods = new ArrayList<UpdatedMethod>();
		SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		SVNDiffClient diffClient = client.getDiffClient();
		TestTrackerSVNDiffGenerator testTrackerSVNDiffGenerator = new TestTrackerSVNDiffGenerator();
		diffClient.setDiffGenerator(testTrackerSVNDiffGenerator);
		try {
			File xmlFile = new File("ProjectUpdates.xml");
			FileOutputStream fOS = new FileOutputStream(xmlFile);
			startProjectUpdatesXML(testTrackerSVNDiffGenerator, fOS);
			diffClient.doDiff(SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+sVNConfig.getProjectPath()),
					SVNRevision.create(startRevision),
					SVNURL.parseURIEncoded(sVNConfig.getSvnUrl()+sVNConfig.getProjectPath()),
			        SVNRevision.create(endRevision),
			        SVNDepth.INFINITY,
			        true,
			        fOS);
			finishProjectUpdatesXML(testTrackerSVNDiffGenerator, fOS);
			fOS.close();
			ProjectUpdates projectUpdates = (ProjectUpdates) getObjectFromXML(xmlFile);
			xmlFile.delete();
			updatedMethods = projectUpdates.getUpdatedMethods(startRevision, endRevision);
		} catch (SVNException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2){
			e2.printStackTrace();
		}
		return updatedMethods;
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
		fOS.write("</br.ufrn.taskanalyser.framework.miner.ProjectUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
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
		fOS.write("<br.ufrn.taskanalyser.framework.miner.ProjectUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
		fOS.write(testTrackerSVNDiffGenerator.getEOL());
		fOS.write("  <classUpdates>".getBytes(testTrackerSVNDiffGenerator.getEncoding()));
		fOS.write(testTrackerSVNDiffGenerator.getEOL());
	}
	
	private String readFile(File file){
		String content = null;
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	private Object getObjectFromXML(File xmlFile){
		String xml = readFile(xmlFile);
		XStream xstream = new XStream();
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

	public IWorkspace getiWorkspace() {
		return iWorkspace;
	}

	public void setiWorkspace(IWorkspace iWorkspace) {
		this.iWorkspace = iWorkspace;
	}

}