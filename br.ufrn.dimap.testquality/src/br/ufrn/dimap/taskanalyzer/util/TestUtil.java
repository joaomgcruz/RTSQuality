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
import br.ufrn.dimap.testtracker.data.TestCoverage;
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

	public static void metricMeansurement(Set<TestCoverage> selectedTests, Set<TestCoverage> allTests, Set<TestCoverage> idealTests, Revision revision){
		revision.setSecurity(getSafeMeasure(selectedTests, idealTests));
		revision.setPrecision(getPrecisionMeasure(selectedTests, idealTests, allTests));
	}
	
	public static Float getSafeMeasure(Set<TestCoverage> selectedTests, Set<TestCoverage> idealTests) {
		if(idealTests.size() == 0)
			return new Float(1);
		Set<TestCoverage> intersection = getIntersection(selectedTests, idealTests);
		return (new Float(intersection.size()))/(new Float(idealTests.size()));
	}

	private static Set<TestCoverage> getIntersection(Set<TestCoverage> selectedTests, Set<TestCoverage> idealTests) {
		Set<TestCoverage> intersection = new HashSet<TestCoverage>(selectedTests.size()+idealTests.size());
		intersection.addAll(selectedTests);
		intersection.addAll(idealTests);
		
		Set<TestCoverage> set2 = new HashSet<TestCoverage>(selectedTests);
		set2.removeAll(idealTests);
		
		Set<TestCoverage> set3 = new HashSet<TestCoverage>(idealTests);
		set3.removeAll(selectedTests);
		
		set2.addAll(set3);
		intersection.removeAll(set2);
		return intersection;
	}
	
	public static Float getPrecisionMeasure(Set<TestCoverage> selectedTests, Set<TestCoverage> idealTests, Set<TestCoverage> allTests){
		Set<TestCoverage> inverseSelectedTests = new HashSet<TestCoverage>(allTests);
		inverseSelectedTests.removeAll(selectedTests);
		
		Set<TestCoverage> inverseIdealTests = new HashSet<TestCoverage>(allTests);
		inverseIdealTests.removeAll(idealTests);
		if(inverseIdealTests.size() == 0)
			return new Float(1);
		
		Set<TestCoverage> intersection = getIntersection(inverseSelectedTests, inverseIdealTests);
		return (new Float(intersection.size()))/(new Float(inverseIdealTests.size()));
	}
	
}
