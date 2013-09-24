package br.ufrn.dimap.taskanalyzer.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import br.ufrn.dimap.testtracker.data.Revision;
import br.ufrn.dimap.testtracker.util.FileUtil;

public class TestUtil {

	public static void executeTests(ClassLoader classLoader, Set<String> testClasses, String resultName) {
		Iterator<String> iterator = testClasses.iterator();
		if(iterator.hasNext()) {
			String saveFileDirectory = getSaveFileDirectory(classLoader, iterator.next());
			FileUtil.saveTextToFile(String.valueOf(testClasses.size()),saveFileDirectory, "testClassesSize", ".txt");
			FileUtil.saveTextToFile(resultName, saveFileDirectory, "testCoverageMappingName", ".txt");
			for(String testClassName : testClasses){
				try {
					Class<?> testClass = classLoader.loadClass(testClassName);
					Runner r = new BlockJUnit4ClassRunner(testClass);
					JUnitCore c = new JUnitCore();
					c.run(Request.runner(r));
				} catch (InitializationError e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getSaveFileDirectory(ClassLoader classLoader, String testClass) {
		try {
			return FileUtil.getBuildFolderByResource(classLoader.loadClass(testClass));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "C:/";
	}

	public static void metricMeansurement(Set<String> selectionCoveredModifications, Set<String> idealCoveredModifications, Set<String> allCoveredModifications, Revision revision){
		revision.setSecurity(getSafeMeasure(selectionCoveredModifications, idealCoveredModifications));
		revision.setPrecision(getPrecisionMeasure(selectionCoveredModifications, idealCoveredModifications, allCoveredModifications));
		printRevision(revision);
	}
	
	private static void printRevision(Revision revision) {
		System.out.println("Revision: "+revision.getId());
		System.out.println("\tSecurity: "+revision.getSecurity());
		System.out.println("\tPrecision: "+revision.getPrecision());
	}
	
	public static Float getSafeMeasure(Set<String> selectionCoveredModifications, Set<String> idealCoveredModifications) {
		if(idealCoveredModifications.size() == 0)
			return new Float(1);
		Set<String> intersection = getIntersection(selectionCoveredModifications, idealCoveredModifications);
		return (new Float(intersection.size()))/(new Float(idealCoveredModifications.size()));
	}

	private static Set<String> getIntersection(Set<String> selectionCoveredModifications, Set<String> idealCoveredModifications) {
		Set<String> intersection = new HashSet<String>(selectionCoveredModifications.size()+idealCoveredModifications.size());
		intersection.addAll(selectionCoveredModifications);
		intersection.addAll(idealCoveredModifications);
		
		Set<String> set2 = new HashSet<String>(selectionCoveredModifications);
		set2.removeAll(idealCoveredModifications);
		
		Set<String> set3 = new HashSet<String>(idealCoveredModifications);
		set3.removeAll(selectionCoveredModifications);
		
		set2.addAll(set3);
		intersection.removeAll(set2);
		return intersection;
	}
	
	public static Float getPrecisionMeasure(Set<String> selectionCoveredModifications, Set<String> idealCoveredModifications, Set<String> allCoveredModifications){
		Set<String> inverseSelectedTests = new HashSet<String>(allCoveredModifications);
		inverseSelectedTests.removeAll(selectionCoveredModifications);
		
		Set<String> inverseIdealTests = new HashSet<String>(allCoveredModifications);
		inverseIdealTests.removeAll(idealCoveredModifications);
		if(inverseIdealTests.size() == 0)
			return new Float(1);
		
		Set<String> intersection = getIntersection(inverseSelectedTests, inverseIdealTests);
		return (new Float(intersection.size()))/(new Float(inverseIdealTests.size()));
	}
	
}
