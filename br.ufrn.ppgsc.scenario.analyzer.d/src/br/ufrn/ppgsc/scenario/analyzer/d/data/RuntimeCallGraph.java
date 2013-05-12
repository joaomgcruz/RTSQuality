package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;
import br.ufrn.ppgsc.scenario.analyzer.d.gui.CGConsole;

public class RuntimeCallGraph {
	
	// TODO:
	// Ver como retirar este código daqui depois
	static {
		SwingUtilities.invokeLater(new Runnable() {
			@ScenarioIgnore
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
	
	@ScenarioIgnore
	public String getScenarioName() {
		return scenario_name;
	}
	
	@ScenarioIgnore
	public Node getRoot() {
		return root;
	}

	public static class Node {
		private Method method;
		private long lastTime;
		private boolean fail;
		private List<Node> children;
		
		public Node(Method method) {
			this.method = method;
			this.children = new ArrayList<Node>();
			this.lastTime = 0;
			this.fail = false;
		}
		
		@ScenarioIgnore
		public void addChild(Node node) {
			children.add(node);
		}
		
		@ScenarioIgnore
		public void removeChild(Node node) {
			children.remove(node);
		}
		
		@ScenarioIgnore
		public Node[] getChildren() {
			return children.toArray(new Node[0]);
		}
		
		@ScenarioIgnore
		public Method getMethod() {
			return method;
		}
		
		@ScenarioIgnore
		public long getLastTime() {
			return lastTime;
		}

		@ScenarioIgnore
		public void setLastTime(long lastTime) {
			this.lastTime = lastTime;
		}

		@ScenarioIgnore
		public boolean isFail() {
			return fail;
		}

		@ScenarioIgnore
		public void setFail(boolean fail) {
			this.fail = fail;
		}
		
	}
	
}
