package br.ufrn.taskanalyser.framework.miner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedMethod;

import com.thoughtworks.xstream.XStream;

public class History {
	private String svnUrl;
	private String projectPath;
	private String name;
	private String password;
	private long startRevision;
	private long endRevision;
	private SVNRepository repository;
	
	public History(String svnUrl, String projectPath, String name, String password, long startRevision, long endRevision) throws SVNException{
		this.svnUrl = svnUrl;
		this.projectPath = projectPath;
		this.name = name;
		this.password = password;
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		
		setupLibrary();
		
		createRepository(svnUrl + projectPath);
	}

	private void createRepository(String svnUrl) throws SVNException {
		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password);
		repository.setAuthenticationManager(authManager);
	}
	
    public Set<String> getChangedMethodsSignatures() {
    	Collection<UpdatedMethod> updatedMethods = getChangedMethods();
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

	private Collection<UpdatedMethod> getChangedMethods() {
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
			diffClient.doDiff(SVNURL.parseURIEncoded(svnUrl+projectPath),
					SVNRevision.create(startRevision),
					SVNURL.parseURIEncoded(svnUrl+projectPath),
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

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getStartRevision() {
		return startRevision;
	}

	public void setStartRevision(long startRevision) {
		this.startRevision = startRevision;
	}

	public long getEndRevision() {
		return endRevision;
	}

	public void setEndRevision(long endRevision) {
		this.endRevision = endRevision;
	}
}