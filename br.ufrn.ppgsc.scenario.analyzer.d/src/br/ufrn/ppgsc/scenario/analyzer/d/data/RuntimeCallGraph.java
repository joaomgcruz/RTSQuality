package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import br.ufrn.ppgsc.scenario.analyzer.d.gui.CGConsole;

public class RuntimeCallGraph {

	// TODO: Ver como retirar este código daqui depois
	static {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CGConsole().setVisible(true);
			}
		});
	}

	private Node root;
	private String scenario_name;

	public RuntimeCallGraph(String scenario_name, Node root) {
		this.root = root;
		this.scenario_name = scenario_name;
	}

	public String getScenarioName() {
		return scenario_name;
	}

	public Node getRoot() {
		return root;
	}

	// TODO: extrair
	public static class Node {
		private Method method;
		private long time;
		private Throwable exception;
		private List<Node> children;

		public Node(Method method) {
			this.method = method;
			this.children = new ArrayList<Node>();
			this.time = 0;
			this.exception = null;
		}

		public void addChild(Node node) {
			children.add(node);
		}

		public void removeChild(Node node) {
			children.remove(node);
		}

		public Node[] getChildren() {
			return children.toArray(new Node[0]);
		}

		public Method getMethod() {
			return method;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public Throwable getException() {
			return exception;
		}

		public void setException(Throwable exception) {
			this.exception = exception;
		}

	}

}
