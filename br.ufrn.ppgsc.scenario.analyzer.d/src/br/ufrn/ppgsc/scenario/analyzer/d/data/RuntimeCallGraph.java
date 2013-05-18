package br.ufrn.ppgsc.scenario.analyzer.d.data;

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
	private RuntimeNode root;
	private String scenario_name;

	public RuntimeCallGraph(String scenario_name, RuntimeNode root) {
		this.thread = Thread.currentThread();
		this.root = root;
		this.scenario_name = scenario_name;
		
		System.out.println("xxx: " + thread.getStackTrace()[thread.getStackTrace().length - 1]);
	}

	public String getScenarioName() {
		return scenario_name;
	}

	public RuntimeNode getRoot() {
		return root;
	}
	
	public long getThreadId() {
		return thread.getId();
	}

}
