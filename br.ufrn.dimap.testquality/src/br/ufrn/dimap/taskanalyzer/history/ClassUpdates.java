package br.ufrn.dimap.taskanalyzer.history;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine;

public class ClassUpdates {
	private String classPath;
	private String classSourceCode1;
	private String classSourceCode2;
	private List<UpdatedLine> changedLines;
	
	public ClassUpdates() {
		changedLines = new ArrayList<UpdatedLine>();
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getClassSourceCode1() {
		return classSourceCode1;
	}

	public void setClassSourceCode1(String classSourceCode1) {
		this.classSourceCode1 = classSourceCode1;
	}

	public String getClassSourceCode2() {
		return classSourceCode2;
	}

	public void setClassSourceCode2(String classSourceCode2) {
		this.classSourceCode2 = classSourceCode2;
	}

	public List<UpdatedLine> getChangedLines() {
		return changedLines;
	}

	public void setChangedLines(List<UpdatedLine> changedLines) {
		this.changedLines = changedLines;
	}

}
