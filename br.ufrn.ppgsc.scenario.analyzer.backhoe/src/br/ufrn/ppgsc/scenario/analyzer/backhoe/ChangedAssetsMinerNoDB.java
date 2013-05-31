package br.ufrn.ppgsc.scenario.analyzer.backhoe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCClient;

import br.ufrn.backhoe.repminer.connector.SubversionConnector;
import br.ufrn.backhoe.repminer.enums.ConnectorType;
import br.ufrn.backhoe.repminer.miner.Miner;
import br.ufrn.backhoe.repminer.model.BackhoeClassCL;
import br.ufrn.backhoe.repminer.model.BackhoeFieldCL;
import br.ufrn.backhoe.repminer.model.BackhoeMethodCL;

public class ChangedAssetsMinerNoDB extends Miner {

	private final Logger logger = Logger.getLogger(ChangedAssetsMinerNoDB.class);
	
	private Map<String, List<BackhoeClassCL>> updated_cls;
	
	private String targetPath;
	private long startRevision;
	private long endRevision;
	
	private SubversionConnector svnConnector;
	
	private SVNRepository repository;
	private SVNWCClient wcclient;

	public ChangedAssetsMinerNoDB() {
		updated_cls = new HashMap<String, List<BackhoeClassCL>>();
	}
	
	public void performSetup() {
		logger.info("performSetup...");
		
		targetPath = (String) parameters.get("target_path");
		startRevision = Long.parseLong((String) parameters.get("start_revision"));
		endRevision = Long.parseLong((String) parameters.get("end_revision"));
		
		svnConnector = (SubversionConnector) connectors.get(ConnectorType.SVN);
		repository = svnConnector.getEncapsulation();
		
		SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		wcclient = client.getWCClient();
	}

	public void performMining() {
		logger.info("performMining...");
		
		Collection<?> svnEntries = null;
		
		try {
			svnEntries = repository.log(new String[]{targetPath}, null, startRevision, endRevision, true, true);
		} catch (SVNException e) {
			logger.error(e.getMessage());
		}

		logger.info("Total of " + svnEntries.size() + " entries");
		
		long count = 0;
		for (Object objEntry : svnEntries) {
			SVNLogEntry logEntry = (SVNLogEntry) objEntry;

			if (logEntry.getChangedPaths().size() > 0) {
				for (Object objPath : logEntry.getChangedPaths().values()) {
					SVNLogEntryPath entryPath = (SVNLogEntryPath) objPath;
					
					if (entryPath.getPath().endsWith(".java"))
						addClassToMap(buildBClass(logEntry, entryPath));
				}
			}
			
			logger.info("[" + ++count + ", " + svnEntries.size() + "]" + " logs were processed");
		}
		
		logger.info("retrieving changes...");
		
		/* 
		 * TODO: Faço a comparação com versões de revisões imediatas,
		 * poderia adaptar para verificar com a última revisão passada,
		 * se pensarmos que a tarefa começa na revisão inicial (startRevision)
		 * e termina na revisão final (endRevision)
		 */
		count = 0;
		for (String path : updated_cls.keySet()) {
			List<BackhoeClassCL> list = updated_cls.get(path);
			
			if (list.size() > 1)
				for (int i = 0; i < list.size() - 1; i++)
					ChangedAssetsMinerUtil.retrieveChangedElements(wcclient, svnConnector.getUrl() + path, list.get(i), list.get(i + 1));
			
			logger.info("Processed path [" + ++count + ", " + updated_cls.size() + "]: " + path);
		}
		
		printChanges();
	}
	
	private void printChanges() {
		for (String path : updated_cls.keySet()) {
			List<BackhoeClassCL> list = updated_cls.get(path);
			
			System.out.println("\n" + path);
			
			for (BackhoeClassCL bcl : list) {
				System.out.println("\t" + bcl.getRevision() + ", " + bcl.toString());
				
				for (BackhoeFieldCL fcl : bcl.getFields()) {
					System.out.println("\t\t" + fcl.toString());
				}
				
				for (BackhoeMethodCL mcl : bcl.getMethods()) {
					System.out.println("\t\t" + mcl.toString());
				}
			}
		}
	}
	
	private void addClassToMap(BackhoeClassCL bcl) {
		List<BackhoeClassCL> list = updated_cls.get(bcl.getPath());
		
		if (list == null) {
			list = new ArrayList<BackhoeClassCL>();
			updated_cls.put(bcl.getPath(), list);
		}
		
		list.add(bcl);
	}

	private BackhoeClassCL buildBClass(SVNLogEntry logEntry, SVNLogEntryPath entryPath) {
		String path = entryPath.getPath();
		BackhoeClassCL bcl = new BackhoeClassCL();
		
		bcl.setChangeType(String.valueOf(entryPath.getType()));
		bcl.setName(path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.')));
		bcl.setPath(path);
		bcl.setRevision(logEntry.getRevision());
		
		return bcl;
	}

}