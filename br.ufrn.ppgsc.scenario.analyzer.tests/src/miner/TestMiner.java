package miner;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.backhoe.repminer.connector.Connector;
import br.ufrn.backhoe.repminer.connector.SubversionConnector;
import br.ufrn.backhoe.repminer.enums.ConnectorType;
import br.ufrn.backhoe.repminer.factory.connector.SubversionConnectorFactory;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.RevisionOfChangedAssetsMinerNoDB;

public class TestMiner {
	public static void main(String[] args) throws Exception {
		String user = "";
		String password = "";
		String svnhost = "http://scenario-analyzer.googlecode.com/svn";

		SubversionConnectorFactory svnFactory = new SubversionConnectorFactory();
		SubversionConnector svnConnector = svnFactory.createConnector(user, password, svnhost);
		
		Map<ConnectorType, Connector> connectors = new HashMap<ConnectorType, Connector>();
		connectors.put(ConnectorType.SVN, svnConnector);

//		long rold = ChangedAssetsMinerUtil.getCommittedRevisionNumber(
//		"C:/Eclipse/JunoDoutorado/workspace/br.ufrn.ppgsc.scenario.analyzer.tests_oldrevision/src/tests/Main.java");
//				
//		long rnew = ChangedAssetsMinerUtil.getCommittedRevisionNumber(
//		"C:/Eclipse/JunoDoutorado/workspace/br.ufrn.ppgsc.scenario.analyzer.tests/src/tests/Main.java");
		
		Map<String, String> parameters = new HashMap<String,String>();
		parameters.put("target_path", "/trunk/br.ufrn.ppgsc.scenario.analyzer.tests/src/tests/Main.java");
		parameters.put("start_revision", "59");
		parameters.put("end_revision", "74");
//		parameters.put("start_revision", String.valueOf(rold));
//		parameters.put("end_revision", String.valueOf(rnew));

		RevisionOfChangedAssetsMinerNoDB miner = new RevisionOfChangedAssetsMinerNoDB();
		miner.setConnectors(connectors);
		miner.setParameters(parameters);
		
		miner.performSetup();
		miner.performMining();
	}
}
