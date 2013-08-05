package br.ufrn.ppgsc.scenario.analyzer.backhoe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.ISVNAnnotateHandler;

import br.ufrn.ppgsc.scenario.analyzer.backhoe.dao.IProjectDAO;

public class UpdatedLinesHandlerIProject implements ISVNAnnotateHandler {

	private final Logger logger = Logger.getLogger(RevisionOfChangedAssetsMinerNoDB.class);
	
	private Map<Long, List<Long>> revisionList;
	private List<UpdatedLine> changedLines;
	private StringBuilder sourceCode;
	private IProjectDAO ipdao;
	
	public UpdatedLinesHandlerIProject() {
		revisionList = new HashMap<Long, List<Long>>();
		changedLines = new ArrayList<UpdatedLine>();
		sourceCode = new StringBuilder();
		ipdao = new IProjectDAO();
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
		
		if (revision != -1) {
			List<Long> tasks = revisionList.get(revision);
			
			if (tasks == null) {
				logger.info("getting tasks to revision " + revision);
				
				tasks = ipdao.getTaskNumberByRevision(revision);
				revisionList.put(revision, tasks);
			}
			
			changedLines.add(new UpdatedLine(date, revision, tasks, author, line, lineNumber));
		}
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
