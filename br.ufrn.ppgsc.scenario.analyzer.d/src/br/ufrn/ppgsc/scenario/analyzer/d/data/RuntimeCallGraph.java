package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.util.Collections;
import java.util.Map;

import javax.swing.SwingUtilities;

import br.ufrn.ppgsc.scenario.analyzer.d.gui.CGConsole;

public class RuntimeCallGraph {

	/* TODO: Ver como retirar este código daqui depois
	 * Posso criar um arquivo jsp para visualizar as informações do grafo
	 */
	static {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CGConsole().setVisible(true);
			}
		});
	}

	private Thread thread;
	private Map<String, String> context;
	private RuntimeNode root;
	private String scenario_name;

	public RuntimeCallGraph(String scenario_name, RuntimeNode root, Map<String, String> context) {
		this.thread = Thread.currentThread();
		this.root = root;
		this.scenario_name = scenario_name;
		this.context = context == null ? null : Collections.unmodifiableMap(context);
	}

	public String getScenarioName() {
		return scenario_name;
	}
	
	public Map<String, String> getContext() {
		return context;
	}

	public RuntimeNode getRoot() {
		return root;
	}
	
	public long getThreadId() {
		return thread.getId();
	}

}
