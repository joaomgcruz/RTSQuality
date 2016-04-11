package br.ufrn.dimap.rtquality.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;

import br.ufrn.dimap.ttracker.data.MethodData;
import br.ufrn.dimap.ttracker.data.MethodState;
import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.TestCoverage;
import br.ufrn.dimap.ttracker.data.TestCoverageGroup;
import br.ufrn.dimap.ttracker.util.FileUtil;

public class TestUtil {

	public static void executeTests(ClassLoader classLoader, Set<String> testClasses) {
		Iterator<String> iterator = testClasses.iterator();
		if(iterator.hasNext()) {
			for(String testClassName : testClasses) {
				try{
					Class<?> testClass = classLoader.loadClass(testClassName);
					Runner r = new WicketJUnitRunner(testClass); //TODO: Renomear e organizar esta implementação
					JUnitCore c = new JUnitCore();
					c.run(Request.runner(r));
				} catch(InitializationError ie) {
					System.out.println("Nome da classe: "+testClassName);
					ie.printStackTrace();
				} catch(ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
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
	
	public static void metricMeansurement(Map<MethodState,Map<String,MethodData>> techniqueMetricsTable,
			Map<MethodState,Map<String,MethodData>> idealMetricsTable, Revision revision) {
		//MC
		Set<String> tMC = techniqueMetricsTable.get(new MethodState(true,true)).keySet();
		Set<String> iMC = idealMetricsTable.get(new MethodState(true,true)).keySet();
		revision.setMC(new Float(MathUtil.intersection(tMC, iMC).size())/new Float(iMC.size())*100);
		//MnC
		Set<String> tMnC = techniqueMetricsTable.get(new MethodState(true,false)).keySet();
		Set<String> iMnC = idealMetricsTable.get(new MethodState(true,false)).keySet();
		revision.setMnC(new Float(MathUtil.intersection(tMnC, iMnC).size())/new Float(iMnC.size())*100);
		//nMC
		Set<String> tnMC = techniqueMetricsTable.get(new MethodState(false,true)).keySet();
		Set<String> inMC = idealMetricsTable.get(new MethodState(false,true)).keySet();
		revision.setnMC(new Float(MathUtil.intersection(tnMC, inMC).size())/new Float(inMC.size())*100);
		//nMnC
		Set<String> tnMnC = techniqueMetricsTable.get(new MethodState(false,false)).keySet();
		Set<String> inMnC = idealMetricsTable.get(new MethodState(false,false)).keySet();
		revision.setnMnC(new Float(MathUtil.intersection(tnMnC, inMnC).size())/new Float(iMC.size())*100);
	}

	public static void metricMeansurement2(Set<TestCoverage> techniqueTestSelection, Set<TestCoverage> idealTestSelection, Set<TestCoverage> allValidTests, Revision revision){
		revision.setInclusion(getInclusionMeasure(techniqueTestSelection, idealTestSelection)*100);
//		revision.setPrecision(getPrecisionMeasure(techniqueTestSelection, idealTestSelection, allValidTests)*100);
	}
	
	public static void printRevision(Revision revision) {
		System.out.println("Revision: "+revision.getId());
		System.out.println("\tInclusion: "+revision.getInclusion());
		System.out.println("\tPrecision: "+revision.getPrecision());
		System.out.println("\tMC: "+revision.getMC());
		System.out.println("\tMnC: "+revision.getMnC());
		System.out.println("\tnMC: "+revision.getnMC());
		System.out.println("\tnMnC: "+revision.getnMnC());
	}
	
	public static Float getInclusionMeasure(Set<TestCoverage> techniqueTestSelection, Set<TestCoverage> idealTestSelection) {
		if(idealTestSelection.size() == 0)
			return new Float(1);
		Set<TestCoverage> intersection = TestCoverage.intersection(techniqueTestSelection, idealTestSelection);
		return (new Float(intersection.size()))/(new Float(idealTestSelection.size()));
	}
	
	public static Float getInclusionMeasureGroup(Set<TestCoverageGroup> techniqueTestSelection, Set<TestCoverageGroup> idealTestSelection) {
		if(idealTestSelection.size() == 0)
			return new Float(1);
		Set<TestCoverageGroup> intersection = TestCoverage.intersectionGroup(techniqueTestSelection, idealTestSelection);
		return (new Float(intersection.size()))/(new Float(idealTestSelection.size()));
	}

	public static Float getPrecisionMeasure(Set<TestCoverage> techniqueTestExclusion, Set<TestCoverage> idealTestExclusion){
		if(idealTestExclusion != null && idealTestExclusion.size() == 0)
			return new Float(1);
		Set<TestCoverage> intersection = TestCoverage.intersection(techniqueTestExclusion, idealTestExclusion);
		return (new Float(intersection.size()))/(new Float(idealTestExclusion.size()));
	}

	public static Float getPrecisionMeasureGroup(Set<TestCoverageGroup> techniqueTestExclusion, Set<TestCoverageGroup> idealTestExclusion){
		if(idealTestExclusion != null && idealTestExclusion.size() == 0)
			return new Float(1);
		Set<TestCoverageGroup> intersection = TestCoverage.intersectionGroup(techniqueTestExclusion, idealTestExclusion);
		return (new Float(intersection.size()))/(new Float(idealTestExclusion.size()));
	}
	
}
