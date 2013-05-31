package miner;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.backhoe.repminer.connector.Connector;
import br.ufrn.backhoe.repminer.connector.SubversionConnector;
import br.ufrn.backhoe.repminer.enums.ConnectorType;
import br.ufrn.backhoe.repminer.factory.connector.SubversionConnectorFactory;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.ChangedAssetsMinerNoDB;

public class TestMiner {
	public static void main(String[] args) throws Exception {
		String user = "";
		String password = "";
		String svnhost = "http://scenario-analyzer.googlecode.com/svn";

		SubversionConnectorFactory svnFactory = new SubversionConnectorFactory();
		SubversionConnector svnConnector = svnFactory.createConnector(user, password, svnhost);
		
		Map<ConnectorType, Connector> connectors = new HashMap<ConnectorType, Connector>();
		connectors.put(ConnectorType.SVN, svnConnector);

		Map<String, String> parameters = new HashMap<String,String>();
		parameters.put("target_path", "/trunk/br.ufrn.ppgsc.scenario.analyzer.tests");
//		parameters.put("target_path", "/trunk");
		parameters.put("start_revision", "0");
		parameters.put("end_revision", "-1");

		ChangedAssetsMinerNoDB miner = new ChangedAssetsMinerNoDB();
		miner.setConnectors(connectors);
		miner.setParameters(parameters);
		
		miner.performSetup();
		miner.performMining();
	}
}
