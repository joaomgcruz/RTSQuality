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
		long startRevision = (long) parameters.get("start_revision");
		long endRevision = (long) parameters.get("end_revision");
		
		SubversionConnector svnConnector = (SubversionConnector) connectors.get(ConnectorType.SVN);
		SVNRepository repository = svnConnector.getEncapsulation();
		
		SVNClientManager client = SVNClientManager.newInstance();
		client.setAuthenticationManager(repository.getAuthenticationManager());
		
		try {
			/* Acha quem foi a última pessoa que alterou cada linha.
			 * Para isso doAnnotate olha um conjunto de revisões anteriores e diz quem
			 * foi a última pessoa que alterou a linha antes de chegar na revisão final.
			 * Se a revisão que alterou aquela linha não está no intervalo de revisões considerado,
			 * ela é considerada como não alterada, retornando -1 para o número da revisão
			 * e null para os outros elementos.
			 * 
			 * O número da linha retornada é referente ao arquivo da versão final (arquivo de referência)
			 * 
			 * Ainda não entendi o que é a pegRevision, não vi diferença mudando o valor passado,
			 * então estou passando null e funciona!
			 */
			
			handler = new UpdatedLinesHandler();
			
			client.getLogClient().doAnnotate(
					SVNURL.parseURIEncoded(svnConnector.getUrl() + targetPath),
					null,
					SVNRevision.create(startRevision),
					SVNRevision.create(endRevision),
					true, true, handler, null);
		} catch (SVNException ex) {
			ex.printStackTrace();
		}
	}

	public void performMining() {
		logger.info("performMining...");
		
		// Pega as linhas modificadas
		List<UpdatedLine> lines = handler.getChangedLines();
		
		// Pega o limite dos limites (linha inicial e final)
		List<MethodLimit> limits = new MethodLimitBuilder(handler.getSourceCode()).getMethodLimits();
		
		// Pega os métodos mudados verificando as linhas mudadas e os limites dos métodos
		changedMethods = ChangedAssetsMinerUtil.filterChangedMethods(limits, lines);
		
		// Usei este artifício para desenvolver o resultado da mineração
		parameters.put("result", changedMethods);
		
		// Mostra o resultado
//		for (UpdatedMethod m : changedMethods) {
//			System.out.println("******************************************");
//			System.out.println(m.getMethodLimit().getSignature());
//			
//			for (UpdatedLine l : m.getUpdatedLines()) {
//				System.out.println("\tAuthor: " + l.getAuthor());
//				System.out.println("\tLine: " + l.getLine());
//				System.out.println("\tLineNumber: " + l.getLineNumber());
//				System.out.println("\tRevision: " + l.getRevision());
//				System.out.println("\tDate: " + l.getDate());
//				System.out.println("\t-------------------------------------");
//			}
//		}
	}

}