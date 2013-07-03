package br.ufrn.taskanalyser.framework.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.tmatesoft.svn.core.SVNException;

import br.ufrn.taskanalyser.framework.Input;
import br.ufrn.taskanalyser.framework.MethodData;
import br.ufrn.taskanalyser.framework.Selection;
import br.ufrn.taskanalyser.framework.TestCoverage;
import br.ufrn.taskanalyser.framework.TestCoverageMapping;
import br.ufrn.taskanalyser.framework.miner.HistoryMiner;

import com.thoughtworks.xstream.XStream;

public class TestTrackerBean {
	
	private String directory;
	private String text;
	private String oldExecution;
	
	public TestTrackerBean() {
		directory = "D:/UFRN/Scenario-analyzer/results/";
		oldExecution = "D:/UFRN/Scenario-analyzer/results/AllTests - "+getProjectName()+".tcm";
	}
	
	public String start() {
		//TODO: Ativar o TestTracker
		return "reports";
	}
	
	public String stop() {
		//TODO: Desativar o TestTracker
		return "reports";
	}
	
	public String save(){
		saveWithName("ManualTests");
		return "reports";
	}
	
	public void saveWithName(String name){
		saveMapping(TestCoverageMapping.getInstance(),name);
	}
	
	public String showAsXML() {
		TestCoverageMapping.getInstance().getTestsCoverage();
		Selection selection = getAllTests();
		text = (new XStream()).toXML(selection);
		return "reports";
	}
	
	public String saveAsXML(){
		Selection selection = getAllTests();
		text = (new XStream()).toXML(selection);
		File dir = new File(directory);
		if(!dir.exists())
			dir.mkdir();
		File XML = new File(directory+"AllTests - "+selection.getRevision()+".xml");
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(XML));
	        out.write(text);
	        out.close();
	    }catch(Throwable e){
	    	e.printStackTrace();
	    }
		return "reports";
	}
	
	public String differencingTextTestSelectionMeasurement(){
		Set<String> changedMethodsSignatures = getChangedMethodsSignature();
		
		Set<TestCoverage> techniqueSelection = differencingTextTestSelection(changedMethodsSignatures);
		
		Set<TestCoverage> perfectSelection = TestCoverageMapping.getInstance().getTestsCoverageByChangedMethodsSignatures(changedMethodsSignatures);
		
		Set<TestCoverage> allTests = TestCoverageMapping.getInstance().getTestsCoverage();
		
		Float safeMeasure = getSafeMeasure(techniqueSelection, perfectSelection)*100;
		Float precisionMeasure = getPrecisionMeasure(techniqueSelection, perfectSelection, allTests)*100;
		text = "Safe Measure: "+safeMeasure+"%\nPrecision Measure: "+precisionMeasure+"%\n";
		return "reports";
	}

	private Set<TestCoverage> differencingTextTestSelection(Set<String> changedMethodsSignatures) {
		TestCoverageMapping oldTestCoverageMapping = getOldTestCoverageMapping();
		if(oldTestCoverageMapping != null)
			return oldTestCoverageMapping.getTestsCoverageByChangedMethodsSignatures(changedMethodsSignatures);
		else
			return new HashSet<TestCoverage>(1);
	}
	
	private TestCoverageMapping getOldTestCoverageMapping(){
		File oldExecutionFile = new File(oldExecution);
		if(oldExecutionFile.exists())
			return loadMapping(oldExecution);
		return null;
	}
	
	public String compare(){
		File otherExecutionXML = new File(oldExecution);
		if(otherExecutionXML.exists()){
			Selection otherSelection = (Selection) (new XStream()).fromXML(otherExecutionXML);
			
		}
		else{
			
		}
		return "reports";
	}
	
	private Set<String> getChangedMethodsSignature(){
		Set<String> changedMethodsSignatures = new HashSet<String>(1);
		String svnUrl = null;
		String projectPath = null;
		try {
			svnUrl = "http://scenario-analyzer.googlecode.com/svn";
			projectPath = "/trunk/"+getProjectName();
			changedMethodsSignatures = HistoryMiner.minerModifications(svnUrl,projectPath,"","","93","97");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SVNException svne) {
			System.err.println("error while creating an SVNRepository for the location '" + svnUrl + "': " + svne.getMessage());
		}
		return changedMethodsSignatures;
	}
	
	private void saveMapping(TestCoverageMapping testCoverageMapping, String name){
		File dir = new File(directory);
		if(!dir.exists())
			dir.mkdir();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(directory+(getProjectName() != null ? getProjectName() : name)+".tcm");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(testCoverageMapping);
			objectOutputStream.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	private TestCoverageMapping loadMapping(String oldExecution){
		try {
			FileInputStream fileInputStream = new FileInputStream(oldExecution);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			TestCoverageMapping oldTestCoverageMapping = (TestCoverageMapping) objectInputStream.readObject();
			objectInputStream.close();
			return oldTestCoverageMapping;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (ClassCastException cce) {
			cce.printStackTrace();
		}
		return null;
	}

	private Float getSafeMeasure(Set<TestCoverage> techniqueSelection, Set<TestCoverage> perfectSelection) {
		Set<TestCoverage> u1 = new HashSet<TestCoverage>(techniqueSelection.size()+perfectSelection.size());
		u1.addAll(techniqueSelection);
		u1.addAll(perfectSelection);
		
		Set<TestCoverage> u2 = new HashSet<TestCoverage>(techniqueSelection);
		u2.removeAll(perfectSelection);
		
		Set<TestCoverage> u3 = new HashSet<TestCoverage>(perfectSelection);
		u2.removeAll(techniqueSelection);
		
		u2.addAll(u3);
		u1.removeAll(u2);
		
		return (new Float(u1.size()))/(new Float(perfectSelection.size()));
	}
	
	private Float getPrecisionMeasure(Set<TestCoverage> techniqueSelection, Set<TestCoverage> perfectSelection, Set<TestCoverage> allTests){
		Set<TestCoverage> inverseTechniqueSelection = new HashSet<TestCoverage>(allTests);
		inverseTechniqueSelection.removeAll(techniqueSelection);
		
		Set<TestCoverage> inversePerfectSelection = new HashSet<TestCoverage>(allTests);
		inversePerfectSelection.removeAll(perfectSelection);
		
		return getSafeMeasure(inverseTechniqueSelection, inversePerfectSelection);
	}
	
	private String getProjectName() {
		try{
			String path = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
			return path.substring(path.substring(0, path.length()-1).lastIndexOf("\\")+1,path.length()-5);
		} catch (Exception e){
			return null;
		}
	}

	private Selection getAllTests() {
		Selection allTests = new Selection(TestCoverageMapping.getInstance().getTestsCoverage().size(),getProjectName());
		for(TestCoverage testCoverage : TestCoverageMapping.getInstance().getTestsCoverage()){
			allTests.addTestCoverage(testCoverage);
		}
		return allTests;
	}
	
	/**
	 * Obtém as informações do Test Coverage em forma de String 
	 * @param testCoverage
	 * @return
	 */
	private String testToString(TestCoverage testCoverage) {
		StringBuffer testTrack = new StringBuffer();
		testTrack.append("Test "+testCoverage.getIdTest()+":\n");
		testTrack.append("        CoveredMethods:"+"\n");
		for(MethodData methodData : testCoverage.getCoveredMethods()){
			testTrack.append("                "+methodData.getSignature()+"\n");
		}
		testTrack.append("        Inputs:"+"\n");
		for(Input input : testCoverage.getInputs()){
			testTrack.append("                "+input.getType().getName()+" "+input.getName()+": "+input.getValue()+"\n");
		}
		testTrack.append("        Date:\n                "+testCoverage.getDate()+"\n");
		return testTrack.toString();
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getOldExecution() {
		return oldExecution;
	}

	public void setOldExecution(String oldExecution) {
		this.oldExecution = oldExecution;
	}

}
