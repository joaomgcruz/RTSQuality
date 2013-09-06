package br.ufrn.dimap.testtracker.aspects;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;

import br.ufrn.dimap.testtracker.data.CoveredMethod;
import br.ufrn.dimap.testtracker.data.Input;
import br.ufrn.dimap.testtracker.data.TestCoverage;
import br.ufrn.dimap.testtracker.data.TestCoverageMapping;
import br.ufrn.dimap.testtracker.data.TestData;
import br.ufrn.dimap.testtracker.util.FileUtil;

public aspect TestTracker {
	private static final Integer NOTFOUND = -1;
	
	private pointcut exclusion() : !within(br.ufrn.dimap.testtracker..*) && !within(br.ufrn.dimap.taskanalyzer..*);
	private pointcut beforeExecutions() :
		cflow(
			(
				within(
					@javax.context.RequestScoped* * ||
					@javax.context.ApplicationScoped* * ||
					@javax.context.ConversationScoped* * ||
					@javax.context.SessionScoped* * ||
					@javax.annotation.ManagedBean* *
				) ||
				@annotation(Test)
			)&&	
			(
				execution(* *(..)) ||
				execution(*.new(..))
			)
		) &&
		(
			execution(* *(..)) ||
			execution(*.new(..))
		) &&
		exclusion();
	
	private pointcut afterExecutions() :
		(
			within(
				@javax.context.SessionScoped* * ||
				@javax.context.RequestScoped* * ||
				@javax.context.ApplicationScoped* * ||
				@javax.context.ConversationScoped* * ||
				@javax.annotation.ManagedBean* *
			) ||
			@annotation(Test)
		) &&
		execution(public * *(..)) &&
		!(
			execution(*.new(..)) ||
			execution(* set*(..)) ||
			execution(* get*(..)) ||
			execution(* is*(..))
		) &&
		exclusion();
	
	private pointcut afterOthers() : //Este after é capaz de capturar tudo que o before é capaz, só que após a execução (excluindo construtures, sets, gets e iss)
		cflow(
			(
				within(
					@javax.context.RequestScoped* * ||
					@javax.context.ApplicationScoped* * ||
					@javax.context.ConversationScoped* * ||
					@javax.context.SessionScoped* * ||
					@javax.annotation.ManagedBean* *
				) ||
				@annotation(Test)
			)&&	
			(
				execution(* *(..)) ||
				execution(*.new(..))
			)
		) &&
		(
			execution(* *(..)) ||
			execution(*.new(..))
		) &&
		!(
			execution(*.new(..)) ||
			execution(* set*(..)) ||
			execution(* get*(..)) ||
			execution(* is*(..))
		) &&
		exclusion();
		
	before() : beforeExecutions() {
		Long threadId = Thread.currentThread().getId();
		Signature signature = thisJoinPoint.getSignature();
		Member member = getMember(signature);
		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
		if(testCoverage == null){
			if(isTestClassMember(member) || isManagedBeanMember(member)){
				testCoverage = new TestCoverage();
				if(isTestMethod(member) || isActionMethod(member)) {
					TestData testData = testCoverage.getTestData();
					testData.setSignature(signature.toString());
					testData.setClassFullName(member.getDeclaringClass().getCanonicalName()); //TODO: Verificar se é realmente esta String que procuro (deve ser o nome da classe com o pacote)
					testData.setManual(!isTestClassMember(member) && isManagedBeanMember(member));
					testCoverage.setTestData(testData); //TODO: é realmente necessário setar o testData ou o objeto já está lá?
				}
				testCoverage.addCoveredMethod(signature.toString(), getInputs(member, thisJoinPoint.getArgs()));
				TestCoverageMapping.getInstance().getTestsCoverageBuilding().put(threadId, testCoverage);
			}
		}
		else{
			TestData testData = testCoverage.getTestData();
			if(testData.getSignature().isEmpty() && ((!testData.isManual() && isTestMethod(member)) || (testData.isManual() && isActionMethod(member)))) {
				testData.setSignature(signature.toString());
				testData.setClassFullName(member.getDeclaringClass().getCanonicalName());
				testData.setManual(!isTestClassMember(member) && isManagedBeanMember(member));
				testCoverage.setTestData(testData); //TODO: é realmente necessário setar o testData ou o objeto já está lá?
			}
			testCoverage.addCoveredMethod(signature.toString(), getInputs(member, thisJoinPoint.getArgs()));
		}
	}
	
	after() : afterExecutions() {
		Long threadId = Thread.currentThread().getId();
		Signature signature = thisJoinPoint.getSignature();
		Member member = getMember(signature);
		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
		if(testCoverage != null){
			TestData testData = testCoverage.getTestData();
			if(((!testData.isManual() && isTestClassMember(member)) ||
			(testData.isManual() && isManagedBeanMember(member) && isActionMethod(member))) &&
			testData.getSignature().equals(signature.toString())){
				TestCoverageMapping.getInstance().finishTestCoverage(threadId);
				Integer testCount = TestCoverageMapping.getInstance().getTestCount();
				Integer testClassesSize = FileUtil.getTestClassesSizeByResource(member.getDeclaringClass());
				if(testClassesSize.equals(NOTFOUND) || testClassesSize.equals(testCount)){
					String fileDirectory = FileUtil.getBuildFolderByResource(member.getDeclaringClass());
					TestCoverageMapping.getInstance().setFileDirectory(fileDirectory);
					String testCoverageMappingName = FileUtil.getTestCoverageMappingNameByResource(member.getDeclaringClass());
					TestCoverageMapping.getInstance().setName(testCoverageMappingName);  
					TestCoverageMapping.getInstance().save(); //TODO: Após executar todos os testes e os depois os testes selecionados verificar se ambos não serão acumulados no mesmo TestCoverageMapping, se sim, desenvolver uma função clear para o TestCoverageMapping.  
					printTestCoverage(testCoverage);
				}
			}
		}
	}
	
//	before() : beforeExecutions() {
//		Long threadId = Thread.currentThread().getId();
//		Signature signature = thisJoinPoint.getSignature();
//		Member member = getMember(signature);
//		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
//		if(testCoverage == null || !testCoverage.isStart()){
//			if(canCreateNewTestCoverage(testCoverage,member,thisJoinPoint.getArgs().length))
//				testCoverage = new TestCoverage();
//			if(isTestMethod(member)){
//				testCoverage.setTestMethod(isTestMethod(member));
//				testCoverage.setSignature(signature.toString());
//			}
//			else if(isManagedBeanMethod(member)){
//				if(isActionMethod(member)){
//					testCoverage.addCoveredMethod(signature.toString());
//					testCoverage.setStart(true);
//					if(thisJoinPoint.getArgs().length > 0){
//						Method method = (Method) member;
//						Class<?> classes[] = method.getParameterTypes();
//						Object parameters[] = thisJoinPoint.getArgs();
//						for(int i=0;i<thisJoinPoint.getArgs().length;i++)
//							testCoverage.getInputs().add(new Input(classes[i], getParameterName(method,i), parameters[i]));
//					}
//				}
//				else if(isPublicSetMethod(member) && thisJoinPoint.getArgs().length == 1){
//					Object parameters[] = thisJoinPoint.getArgs();
//					Method method = (Method) member;
//					Class<?> classes[] = method.getParameterTypes();
//					testCoverage.getInputs().add(new Input(classes[0], getParameterName(member), parameters[0]));
//				}
//			}
//			if(testCoverage != null)
//				TestCoverageMapping.getInstance().getTestsCoverageBuilding().put(threadId, testCoverage);
//		}
//		else{
//			testCoverage.addCoveredMethod(signature.toString());
//		}
//	}
//	
//	after() : beforeExecutions() {
//		Long threadId = Thread.currentThread().getId();
//		Signature signature = thisJoinPoint.getSignature();
//		Member member = getMember(signature);
//		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
//	}
//	
//	after() : afterExecutions() {
//		Long threadId = Thread.currentThread().getId();
//		Signature signature = thisJoinPoint.getSignature();
//		Member member = getMember(signature);
//		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
//		if(testCoverage != null){
//			if(testCoverage.isTestMethod()){
//				if(isTestMethod(member) && testCoverage.getSignature().equals(signature.toString())){
//					TestCoverageMapping.getInstance().finishTestCoverage(threadId);
//					printTestCoverage(testCoverage);
//				}
//			}
//			else if(isManagedBeanMethod(member) && isActionMethod(member)) {
//				Iterator<MethodData> iterator = testCoverage.getCoveredMethods().iterator();
//				if(iterator.hasNext() && iterator.next().getSignature().equals(signature.toString())){
//					TestCoverageMapping.getInstance().finishTestCoverage(threadId);
//					printTestCoverage(testCoverage);
//				}
//			}
//		}
//	}
	
	private LinkedHashSet<Input> getInputs(Member member, Object[] args){
		Class<?>[] types = getParameterTypes(member);
		String name = member.getDeclaringClass().getName()+"."+member.getName()+"."+"arg";
		LinkedHashSet<Input> inputs = new LinkedHashSet<Input>();
		if(types.length == args.length){
			for(int i=0;i<args.length;i++)
				inputs.add(new Input(types[i].getName(),name+i,args[i]));
		}
		return inputs;
	}
	
	/**
	 * @param member
	 * @return
	 */
	private Class<?>[] getParameterTypes(Member member) {
		Class<?>[] types = new Class<?>[1];
		if(member instanceof Method)
			types = ((Method) member).getParameterTypes();
		else if(member instanceof Constructor)
			types = ((Constructor) member).getParameterTypes();
		return types;
	}

	private void printTestCoverage(TestCoverage testCoverage) {
		System.out.println("TestCoverage "+testCoverage.getIdTest()+": "+testCoverage.getTestData().getSignature());
		System.out.println("MethodDatas:");
		for (CoveredMethod coveredMethod : testCoverage.getCoveredMethods()) {
			System.out.print("\t"+coveredMethod.getMethodData().getSignature());
			String inputString = " <- (";
			for(Input input : coveredMethod.getInputs()){
				inputString += input.getValue().toString()+", ";
			}
			inputString = coveredMethod.getInputs().size() == 0 ? "" : inputString.substring(0,inputString.length()-2)+")";
			System.out.println(inputString);
		}
		System.out.println("\n---------------------------------------------------------------\n");
	}
	
	private Member getMember(Signature sig) {
		if (sig instanceof MethodSignature)
			return ((MethodSignature) sig).getMethod();
		else if (sig instanceof ConstructorSignature)
			return ((ConstructorSignature) sig).getConstructor();
		
		return null;
	}
	
	private boolean isManagedBeanMember(Member member) {
		Annotation anotations[] = member.getDeclaringClass().getAnnotations();
		boolean managedBean = false;
		for(Annotation annotation : anotations){
			String packageClass = annotation.annotationType().getName();
			if(packageClass.equals("javax.context.SessionScoped") || packageClass.equals("javax.context.ApplicationScoped") ||
			packageClass.equals("javax.context.ConversationScoped") || packageClass.equals("javax.context.RequestScoped") ||
			packageClass.equals("javax.annotation.ManagedBean")){
				managedBean = true;
				break;
			}
		}
		return managedBean;
	}
	
	private boolean isActionMethod(Member member){
		return isPublic(member) && !isSetOrGetOrIs(member) && !(member instanceof Constructor);
	}
	
	private boolean isPublic(Member member){
		return Modifier.isPublic(member.getModifiers());
	}
	
	private boolean isSetOrGetOrIs(Member member){
		return member.getName().startsWith("set") || member.getName().startsWith("get") || member.getName().startsWith("is");
	}
	
	private boolean isPublicSetMethod(Member member){
		return Modifier.isPublic(member.getModifiers()) && member.getName().startsWith("set");
	}
	
	private boolean isTestClassMember(Member member) {
		for(Method method : member.getDeclaringClass().getDeclaredMethods()) {
			for(Annotation annotation : method.getAnnotations()) {
				String packageClass = annotation.annotationType().getName();
				if(packageClass.equals("org.junit.Test"))
					return true;
			}
		}
		return false;
	}
	
	private boolean isTestMethod(Member member) {
		if(member instanceof Method)
			return ((Method) member).getAnnotation(Test.class) != null;
		return false;
	}

	private boolean canCreateNewTestCoverage(TestCoverage testCoverage, Member member, int argsLength){
		return (testCoverage == null) && (isTestClassMember(member) || (isManagedBeanMember(member) && (isActionMethod(member) || (isPublicSetMethod(member) && argsLength == 1))));
	}
	
}
