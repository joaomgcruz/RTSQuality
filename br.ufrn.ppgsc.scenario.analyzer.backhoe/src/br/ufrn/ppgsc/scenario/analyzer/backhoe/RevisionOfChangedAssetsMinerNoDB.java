package br.ufrn.ppgsc.scenario.analyzer.backhoe;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;

import br.ufrn.backhoe.repminer.connector.SubversionConnector;
import br.ufrn.backhoe.repminer.enums.ConnectorType;
import br.ufrn.backhoe.repminer.miner.Miner;

public class RevisionOfChangedAssetsMinerNoDB extends Miner {

	private final Logger logger = Logger.getLogger(RevisionOfChangedAssetsMinerNoDB.class);
	
	private Collection<UpdatedMethod> changedMethods;
	private UpdatedLinesHandler handler;
	
	public void performSetup() {
		logger.info("performSetup...");
		
		String targetPath = (String) parameters.get("target_path");
		long startRevision = Long.parseLong((String) parameters.get("start_revision"));
		long endRevision = Long.parseLong((String) parameters.get("end_revision"));
		
		SubversionConnector svnConnector = (SubversionConnector) connectors.get(ConnectorType.SVN);
		SVNRepository repository = svnConnector.getEncapsulation();
		
		SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		
		try {
			handler = new UpdatedLinesHandler();
			
			client.getLogClient().doAnnotate(
					SVNURL.parseURIEncoded(svnConnector.getUrl() + targetPath),
					SVNRevision.HEAD,
					SVNRevision.create(startRevision),
					SVNRevision.create(endRevision),
					true, true, handler, null);
		} catch (SVNException ex) {
			ex.printStackTrace();
		}
	}

	public void performMining() {
		logger.info("performMining...");
		
		List<UpdatedLine> lines = handler.getChangedLines();
		List<MethodLimit> limits = new MethodLimitBuilder(handler.getSourceCode()).getMethodLimits();
		changedMethods = ChangedAssetsMinerUtil.filterChangedMethods(limits, lines);
		
		for (UpdatedMethod m : changedMethods) {
			System.out.println("******************************************");
			System.out.println(m.getMethodLimit().getSignature());
			
			for (UpdatedLine l : m.getUpdatedLines()) {
				System.out.println("\tAuthor: " + l.getAuthor());
				System.out.println("\tLine: " + l.getLine());
				System.out.println("\tLineNumber: " + l.getLineNumber());
				System.out.println("\tRevision: " + l.getRevision());
				System.out.println("\tDate: " + l.getDate());
				System.out.println("\t-------------------------------------");
			}
		}
	}

}