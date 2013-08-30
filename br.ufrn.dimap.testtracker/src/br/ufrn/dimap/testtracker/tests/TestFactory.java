package br.ufrn.dimap.testtracker.tests;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import br.ufrn.dimap.testtracker.data.TestCoverageMapping;
import br.ufrn.dimap.testtracker.util.FileUtil;

public class TestFactory {
	private static final TestFactory instance = new TestFactory();

	private final String testPackage = "br.ufrn.dimap.testtracker.tests";
	private String testName;
	private String testDirectory;
	private String startContent;
	private String classSignaturesContent;
	private String endContent;
	private Set<String> testClasses;
	
	private TestFactory() {
		testName = "AllTests";
		testDirectory = "";
		testClasses = new HashSet<String>(1);
	}

	private String initializeStartContet(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("package "+testPackage+";"+"\n\n");
		stringBuffer.append("import org.junit.ClassRule;"+"\n");
		stringBuffer.append("import org.junit.rules.ExternalResource;"+"\n");
		stringBuffer.append("import org.junit.runner.RunWith;"+"\n");
		stringBuffer.append("import org.junit.runners.Suite;"+"\n");
		stringBuffer.append("import org.junit.runners.Suite.SuiteClasses;"+"\n\n");
		stringBuffer.append("import br.ufrn.dimap.testtracker.data.TestCoverageMapping;"+"\n\n");
		stringBuffer.append("@RunWith(Suite.class)"+"\n");
		stringBuffer.append("@SuiteClasses({"+"\n");
		return stringBuffer.toString();
	}
	
	private String initializeEndContent(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("})"+"\n");
		stringBuffer.append("public class "+testName+" {"+"\n");
		stringBuffer.append("\t"+"private static String fileDirectory = \""+testDirectory+"\";\n\n");
		stringBuffer.append("\t"+"@ClassRule\n");
		stringBuffer.append("\t"+"public static ExternalResource externalResource = new ExternalResource() {\n");
		stringBuffer.append("\t\t"+"@Override\n");
		stringBuffer.append("\t\t"+"protected void after() {\n");
		stringBuffer.append("\t\t\t"+"TestCoverageMapping.save(fileDirectory);\n");
		stringBuffer.append("\t\t"+"}\n");
		stringBuffer.append("\t"+"};\n\n");
		stringBuffer.append("}"+"\n\n");
		return stringBuffer.toString();
	}
	
	private String initializeTestClassSignaturesContent(){
		StringBuffer stringBuffer = new StringBuffer();
		for (String testClass : testClasses) {
			stringBuffer.append("\t"+testClass+".class,"+"\n");
		}
		return (!stringBuffer.toString().isEmpty() ? stringBuffer.toString().substring(0, stringBuffer.toString().length()-2)+"\n" : "");
	}

	public void updateAllTests() {
		startContent = initializeStartContet();
		classSignaturesContent = initializeTestClassSignaturesContent();
		endContent = initializeEndContent();
		FileUtil.saveTextToFile(getSourceCode(), testDirectory, testName, ".java");
		doCompilation(new DynamicJavaSourceCodeObject(getTestClassFullName(), getSourceCode()));
	}
	
	private String getTestClassFullName() {
		return testPackage+"."+testName;
	}
	
	private String getSourceCode() {
		return startContent+classSignaturesContent+endContent;
	}
	
	/**
     * Does the required object initialization and compilation.
     */
    public void doCompilation(SimpleJavaFileObject fileObject){
        /*Creating dynamic java source code file object*/
        JavaFileObject javaFileObjects[] = new JavaFileObject[]{fileObject} ;
 
        /*Instantiating the java compiler*/
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 
        /**
         * Retrieving the standard file manager from compiler object, which is used to provide
         * basic building block for customizing how a compiler reads and writes to files.
         *
         * The same file manager can be reopened for another compiler task.
         * Thus we reduce the overhead of scanning through file system and jar files each time
         */
        StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
 
        /* Prepare a list of compilation units (java source code file objects) to input to compilation task*/
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);
 
        /*Prepare any compilation options to be used during compilation*/
        //In this example, we are asking the compiler to place the output files under bin folder.
        String[] compileOptions = new String[]{"-d", "D:/UFRN/Scenario-analyzer/runtime-workspace/Calculadora_132/WebContent/WEB-INF/classes", "-classpath", "D:/UFRN/Scenario-analyzer/runtime-workspace/Calculadora_132/WebContent/WEB-INF/classes;D:/UFRN/Scenario-analyzer/runtime-workspace/Calculadora_132/WebContent/WEB-INF/lib/junit.jar;D:/UFRN/Scenario-analyzer/eclipse-new/plugins/org.hamcrest.core_1.1.0.v20090501071000.jar"}; //TODO: Tornar isso dinâmico
        Iterable<String> compilationOptionss = Arrays.asList(compileOptions);
 
        /*Create a diagnostic controller, which holds the compilation problems*/
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
 
        /*Create a compilation task from compiler by passing in the required input objects prepared above*/
        CompilationTask compilerTask = compiler.getTask(null, stdFileManager, diagnostics, compilationOptionss, null, compilationUnits) ;
 
        //Perform the compilation by calling the call method on compilerTask object.
        boolean status = compilerTask.call();
 
        if (!status){//If compilation error occurs
            /*Iterate through each compilation problem and print it*/
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()){
                System.out.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic);
            }
        }
        try {
            stdFileManager.close() ;//Close the file manager
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static TestFactory getInstance() {
		return instance;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestDirectory() {
		return testDirectory;
	}

	public void setTestDirectory(String testDirectory) {
		this.testDirectory = testDirectory;
	}

	public Set<String> getTestClasses() {
		return testClasses;
	}

	public void setTestClasses(Set<String> testClasses) {
		this.testClasses = testClasses;
	}

}
