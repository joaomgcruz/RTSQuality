package br.ufrn.dimap.testtracker.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class RunTests {
	
	public static boolean runAllTests(ClassLoader classLoader){
		boolean failure = false;
		try {
			Class<?> allTestsClass = Class.forName("AllTests");//classLoader.loadClass("br.ufrn.dimap.testtracker.tests.AllTests");
			Runner r = new BlockJUnit4ClassRunner(allTestsClass);
			JUnitCore c = new JUnitCore();
			Result result = c.run(Request.runner(r));
			failure = result.getFailureCount() > 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InitializationError e) {
			e.printStackTrace();
		}
		return failure;
	}
	
}
