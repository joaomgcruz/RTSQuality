package miner;

import java.util.Collection;

import br.ufrn.ppgsc.scenario.analyzer.backhoe.AnalyzerMiner;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedLine;
import br.ufrn.ppgsc.scenario.analyzer.backhoe.UpdatedMethod;

public class TestMiner {
	
	public static void main(String[] args) throws Exception {
		
		Collection<UpdatedMethod> result = AnalyzerMiner.getUpdatedMethodsFromRepository(
				"http://scenario-analyzer.googlecode.com/svn", "", "",
				"/trunk/br.ufrn.ppgsc.scenario.analyzer.tests/src/tests/Main.java",
				"C:/Eclipse/EclipseDoutorado/workspace/br.ufrn.ppgsc.scenario.analyzer.tests_oldrevision/src/tests/Main.java",
				"C:/Eclipse/EclipseDoutorado/workspace/br.ufrn.ppgsc.scenario.analyzer.tests/src/tests/Main.java");
		
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
		
//		String text = "Revisão: 4980 por felipe.";
//		
//		String re5 = "(\\s)";
//		String re6 = "(\\d+)";
//		String re7 = "(:)";
//		
//		Pattern p2 = Pattern.compile(re5 + re6 + re5, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
//		Pattern p3 = Pattern.compile(re7 + re6 + re5);
//		Pattern p6 = Pattern.compile(re5 + re6);
//		
//		long revision = -1;
//		
//		for (String parts : text.split("Revisão")) {
//			System.out.println(parts);
//		
//			Matcher revMatcher = p2.matcher(parts);
//			
//			// (begin) get the revision
//			if (revMatcher.find()) {
//				revision = Long.valueOf(revMatcher.group(2));
//			} else {
//				revMatcher = p3.matcher(parts);
//				if (revMatcher.find()) {
//					revision = Long.valueOf(revMatcher.group(2));
//				} else {
//					revMatcher = p6.matcher(parts);
//					if (revMatcher.find()) {
//						revision = Long.valueOf(revMatcher.group(2));
//					}
//				}
//			}
//			// (end)
//		}
//		
//		System.out.println("Revisão: " + revision);

	}
	
}
