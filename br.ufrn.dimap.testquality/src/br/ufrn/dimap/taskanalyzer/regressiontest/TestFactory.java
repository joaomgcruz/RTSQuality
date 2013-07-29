package br.ufrn.dimap.taskanalyzer.regressiontest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class TestFactory {
	private static final TestFactory instance = new TestFactory();

	private final String testPackage = "br.ufrn.testtracker.tests.";
	private String testName;
	private final String startContent;
	private String classSignaturesContent;
	private final String endContent;
	private Set<String> testClassSignatures;
	
	private TestFactory() {
		testClassSignatures = new HashSet<String>();
		testName = "AllTests";
		startContent = initializeStartContet();
		endContent = initializeEndContent();
	}

	public void updateAllTests() {
		classSignaturesContent = initializeTestClassSignaturesContent();
		CompilerAPITest.doCompilation(new DynamicJavaSourceCodeObject(getTestClassFullName(), getSourceCode()));
	}
	
	public boolean runTests(){
		boolean failure = false;
		try {
			Runner r = new BlockJUnit4ClassRunner(Class.forName(getTestClassFullName()));
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
	
	public boolean runAllTests(String path){
		boolean failure = false;
		try {
			URL classUrl = new URL("file://"+path+"/JavaSource/br/ufrn/testtracker/tests/");
	    	URL[] classUrls = { classUrl };
	    	URLClassLoader ucl = new URLClassLoader(classUrls);
	    	Class<?> allTestsClass = ucl.loadClass("AllTests");
			Runner r = new BlockJUnit4ClassRunner(allTestsClass);
			JUnitCore c = new JUnitCore();
			Result result = c.run(Request.runner(r));
			failure = result.getFailureCount() > 0;
			ucl.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InitializationError e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return failure;
	}

	/**
	 * @return
	 */
	private String getSourceCode() {
		return startContent+classSignaturesContent+endContent;
	}

	private String getTestClassFullName() {
		return testPackage+getTestClassName();
	}

	private String getAllTestsClassFullName() {
		return testPackage+"AllTests";
	}

	private String getTestClassName() {
		return testName.isEmpty() ? "UnknownTests" : testName;
	}
	
	public String getTestPackage() {
		return testPackage;
	}

	private String initializeStartContet(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("package br.ufrn.testtracker.tests;"+"\n\n");
		stringBuffer.append("import org.junit.ClassRule;"+"\n");
		stringBuffer.append("import org.junit.rules.ExternalResource;"+"\n");
		stringBuffer.append("import org.junit.runner.RunWith;"+"\n");
		stringBuffer.append("import org.junit.runners.Suite;"+"\n");
		stringBuffer.append("import org.junit.runners.Suite.SuiteClasses;"+"\n\n");
		stringBuffer.append("import br.ufrn.taskanalyser.framework.ui.TestTrackerBean;"+"\n\n");
		stringBuffer.append("@RunWith(Suite.class)"+"\n");
		stringBuffer.append("@SuiteClasses({"+"\n");
		return stringBuffer.toString();
	}
	
	private String initializeTestClassSignaturesContent(){
		StringBuffer stringBuffer = new StringBuffer();
		for (String testClassSignature : testClassSignatures) {
			stringBuffer.append("\t"+testClassSignature+".class"+"\n");
		}
		return stringBuffer.toString();
	}
	
	private String initializeEndContent(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("})"+"\n");
		stringBuffer.append("public class "+getTestClassName()+" {"+"\n\n");
		stringBuffer.append("\t"+"@ClassRule"+"\n");
		stringBuffer.append("\t"+"public static ExternalResource externalResource = new ExternalResource() {"+"\n");
		stringBuffer.append("\t\t"+"@Override"+"\n");
		stringBuffer.append("\t\t"+"protected void after(){"+"\n");
		stringBuffer.append("\t\t\t"+"TestTrackerBean testTrackerBean = new TestTrackerBean();"+"\n");
		stringBuffer.append("\t\t\t"+"String className = getClass().getName();"+"\n");
		stringBuffer.append("\t\t\t"+"testTrackerBean.saveWithName(className.lastIndexOf('$') != -1 ? className.substring(0, className.lastIndexOf('$')) : className);"+"\n");
		stringBuffer.append("\t\t"+"}"+"\n");
		stringBuffer.append("\t"+"};"+"\n");
		stringBuffer.append("}"+"\n");
		return stringBuffer.toString();
	}
	
	public static TestFactory getInstance() {
		return instance;
	}

	public void setTestClassSignatures(Set<String> testClassSignatures) {
		this.testClassSignatures = testClassSignatures;
	}
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}
