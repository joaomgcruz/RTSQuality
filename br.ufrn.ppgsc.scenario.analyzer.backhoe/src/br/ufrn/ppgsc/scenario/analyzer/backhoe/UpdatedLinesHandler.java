package br.ufrn.ppgsc.scenario.analyzer.backhoe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNAnnotateHandler;

public class UpdatedLinesHandler implements ISVNAnnotateHandler {

	private List<UpdatedLine> changedLines;
	private StringBuilder sourceCode;
	
	public UpdatedLinesHandler() {
		changedLines = new ArrayList<UpdatedLine>();
		sourceCode = new StringBuilder();
	}
	
	public List<UpdatedLine> getChangedLines() {
		return Collections.unmodifiableList(changedLines);
	}
	
	public String getSourceCode() {
		return sourceCode.toString();
	}
	
	public void handleEOF() throws SVNException {
		System.out.println("EOF has just been found!");
	}

	public void handleLine(Date date, long revision, String author, String line) throws SVNException {
		handleLine(date, revision, author, line, null, -1, null, null, -1);
	}

	public void handleLine(Date date, long revision, String author, String line, Date mergedDate,
			long mergedRevision, String mergedAuthor, String mergedPath, int lineNumber) throws SVNException {
		sourceCode.append(line + "\n");
		if (revision != -1)
			changedLines.add(new UpdatedLine(date, revision, author, line, lineNumber));
	}

	public boolean handleRevision(Date date, long revision, String author, File contents) throws SVNException {
//		System.out.println("date: " + date);
//		System.out.println("revision: " + revision);
//		System.out.println("author: " + author);
//		System.out.println("contents: " + contents.getName());
//		System.out.println("************************");
		return false;
	}

}
