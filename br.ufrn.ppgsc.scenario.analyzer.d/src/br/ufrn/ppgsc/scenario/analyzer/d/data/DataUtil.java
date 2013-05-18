package br.ufrn.ppgsc.scenario.analyzer.d.data;

import java.io.IOException;
import java.lang.annotation.Annotation;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Reliability;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph.Node;

public abstract class DataUtil {
	
	public static void printScenarioTree(RuntimeCallGraph tree, Appendable buffer) throws IOException {
		buffer.append("Scenario: " + tree.getScenarioName() + "\n   ");
		printInOrder(tree.getRoot(), buffer);
		buffer.append(System.lineSeparator());
		printTreeNode(tree.getRoot(), "   ", buffer);
	}
	
	private static void printInOrder(Node root, Appendable buffer) throws IOException {
		buffer.append(root.getMethod().getName());
		
		for (Node node : root.getChildren()) {
			buffer.append(" > ");
			printInOrder(node, buffer);
		}
	}
	
	private static void printTreeNode(Node root, String tabs, Appendable buffer) throws IOException {
		buffer.append(tabs + "(" + root.getTime() + "ms, " +
				(root.getException() == null ? false : true) + ") " + root.getMethod().getName());
		
		Annotation annotation = root.getMethod().getAnnotation(Performance.class);
		if (annotation != null)
			buffer.append("[Performance: " + ((Performance) annotation).name() + "]");
		
		annotation = root.getMethod().getAnnotation(Security.class);
		if (annotation != null)
			buffer.append("[Security: " + ((Security) annotation).name() + "]");
		
		annotation = root.getMethod().getAnnotation(Reliability.class);
		if (annotation != null)
			buffer.append("[Reliability: " + ((Reliability) annotation).name() + "]");
		
		annotation = root.getMethod().getAnnotation(Robustness.class);
		if (annotation != null)
			buffer.append("[Robustness: " + ((Robustness) annotation).name() + "]");
		
		buffer.append(System.lineSeparator());
		
		for (Node node : root.getChildren()) {
			printTreeNode(node, tabs + "   ", buffer);
		}
	}

}
