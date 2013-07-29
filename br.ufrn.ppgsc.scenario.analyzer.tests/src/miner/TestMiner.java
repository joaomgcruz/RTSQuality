package miner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.ufrn.backhoe.repminer.connector.Connector;
import br.ufrn.backhoe.repminer.connector.SubversionConnector;
import br.ufrn.backhoe.repminer.enums.ConnectorType;
import br.ufrn.backhoe.repminer.factory.connector.SubversionConnectorFactory;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.ChangedAssetsMinerUtil;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.RevisionOfChangedAssetsMinerNoDB;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedMethod;

public class TestMiner {
	
	public static void main(String[] args) throws Exception {
		
		String user = "";
		String password = "";
		String svnhost = "http://scenario-analyzer.googlecode.com/svn";

		SubversionConnectorFactory svnFactory = new SubversionConnectorFactory();
		SubversionConnector svnConnector = svnFactory.createConnector(user, password, svnhost);

		Map<ConnectorType, Connector> connectors = new HashMap<ConnectorType, Connector>();
		connectors.put(ConnectorType.SVN, svnConnector);

		long rold = ChangedAssetsMinerUtil.getCommittedRevisionNumber(
				"C:/Eclipse/JunoDoutorado/workspace/br.ufrn.ppgsc.scenario.analyzer.tests_oldrevision/src/tests/Main.java");

		long rnew = ChangedAssetsMinerUtil.getCommittedRevisionNumber(
				"C:/Eclipse/JunoDoutorado/workspace/br.ufrn.ppgsc.scenario.analyzer.tests/src/tests/Main.java");

		Map parameters = new HashMap<String, String>();
		parameters.put("target_path", "/trunk/br.ufrn.ppgsc.scenario.analyzer.tests/src/tests/Main.java");
		parameters.put("start_revision", rold);
		parameters.put("end_revision", rnew);

		RevisionOfChangedAssetsMinerNoDB miner = new RevisionOfChangedAssetsMinerNoDB();
		miner.setConnectors(connectors);
		miner.setParameters(parameters);

		miner.performSetup();
		miner.performMining();

		Collection<UpdatedMethod> result = (Collection<UpdatedMethod>) parameters.get("result");

		for (UpdatedMethod m : result) {
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
