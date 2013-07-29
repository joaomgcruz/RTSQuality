package br.ufrn.testtracker.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;

import br.ufrn.testtracker.data.CoveredMethod;
import br.ufrn.testtracker.data.Input;
import br.ufrn.testtracker.data.TestCoverage;
import br.ufrn.testtracker.data.TestCoverageMapping;

public aspect TestTracker {
	private pointcut exclusion() : !within(br.ufrn.testtracker..*);
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
			if(isTestMethod(member) || isManagedBeanMethod(member)){
				testCoverage = new TestCoverage();
				if(isTestMethod(member) || isActionMethod(member))
					testCoverage.setSignature(signature.toString());
				testCoverage.setManual(isManagedBeanMethod(member));
				testCoverage.addCoveredMethod(signature.toString(), getInputs(member, thisJoinPoint.getArgs()));
				TestCoverageMapping.getInstance().getTestsCoverageBuilding().put(threadId, testCoverage);
			}
		}
		else{
			if(testCoverage.isManual() && testCoverage.getSignature().isEmpty() && isActionMethod(member))
				testCoverage.setSignature(signature.toString());
			testCoverage.addCoveredMethod(signature.toString(), getInputs(member, thisJoinPoint.getArgs()));
		}
	}
	
	after() : afterExecutions() {
		Long threadId = Thread.currentThread().getId();
		Signature signature = thisJoinPoint.getSignature();
		Member member = getMember(signature);
		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
		if(testCoverage != null){
			if(((!testCoverage.isManual() && isTestMethod(member)) 
				|| (testCoverage.isManual() && isManagedBeanMethod(member) && isActionMethod(member)))
				&& testCoverage.getSignature().equals(signature.toString())){
				TestCoverageMapping.getInstance().finishTestCoverage(threadId);
				printTestCoverage(testCoverage);
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
//	after() : afterOthers() {
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
				inputs.add(new Input(types[i],name+i,args[i]));
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
		System.out.println("TestCoverage "+testCoverage.getIdTest()+": "+testCoverage.getSignature());
		System.out.println("Inputs:");
		for (Input input : testCoverage.getInputs()) {
			System.out.println("\t"+input.getType().getName() + " " + input.getName() + " = " + input.getValue());
		}
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
	
	private boolean isManagedBeanMethod(Member member) {
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
	
	private boolean isTestMethod(Member member) {
		if(member instanceof Method)
			return ((Method) member).getAnnotation(Test.class) != null;
		return false;
	}

	private boolean canCreateNewTestCoverage(TestCoverage testCoverage, Member member, int argsLength){
		return (testCoverage == null) && (isTestMethod(member) || (isManagedBeanMethod(member) && (isActionMethod(member) || (isPublicSetMethod(member) && argsLength == 1))));
	}
	
}
