package br.ufrn.taskanalyser.framework.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;

import br.ufrn.taskanalyser.framework.Input;
import br.ufrn.taskanalyser.framework.MethodData;
import br.ufrn.taskanalyser.framework.TestCoverage;
import br.ufrn.taskanalyser.framework.TestCoverageMapping;

public aspect TestTracker {
	private pointcut exclusion() : !within(br.ufrn.taskanalyser.framework..*);
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
	
	before() : beforeExecutions() {
		Long threadId = Thread.currentThread().getId();
		Signature signature = thisJoinPoint.getSignature();
		Member member = getMember(signature);
		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
		if(testCoverage == null){ //Se a thread é nova
			if(isManagedBeanOrTestMethod(member)){ //E o método é inicial
				List<Input> inputs = new ArrayList<Input>(1);
				if(TestCoverageMapping.getInstance().getOpenedTestCoverageInputs(threadId) != null)
					inputs = TestCoverageMapping.getInstance().removeOpenedTestCoverageInputs(threadId);
				testCoverage = new TestCoverage(inputs, isTestMethod(member), signature.toString());
				testCoverage.addCoveredMethod(signature.toString());
				TestCoverageMapping.getInstance().getTestsCoverageBuilding().put(threadId, testCoverage);
			}else if(member instanceof Method && Modifier.isPublic(member.getModifiers()) && member.getName().startsWith("set") && thisJoinPoint.getArgs().length == 1){
				Object parameters[] = thisJoinPoint.getArgs();
				String parameterName = getParameterName(member);
				Method method = (Method) member;
				Class<?> classes[] = method.getParameterTypes();
				Input input = new Input(classes[0], parameterName, parameters[0].toString());
				if(TestCoverageMapping.getInstance().getOpenedTestCoverageInputs(threadId) == null)
					TestCoverageMapping.getInstance().getTestsCoverageInputs().put(threadId,new ArrayList<Input>());
				TestCoverageMapping.getInstance().getOpenedTestCoverageInputs(threadId).add(input);
			}
		}else{ //Se a thread não é nova
			testCoverage.addCoveredMethod(signature.toString());
		}
	}
	
	after() : afterExecutions() { //TODO: verificar se os pointcuts estão de acordo com o que deveria ser (certo) ou se está como o AJDT tá dizendo (errado)
		Long threadId = Thread.currentThread().getId();
		TestCoverage testCoverage = TestCoverageMapping.getInstance().getOpenedTestCoverage(threadId);
		if(testCoverage != null){
			Iterator<MethodData> iterator = testCoverage.getCoveredMethods().iterator(); 
			if(iterator.hasNext() && iterator.next().getSignature().equals(thisJoinPoint.getSignature().toString()))
				TestCoverageMapping.getInstance().finishTestCoverage(threadId);
		}
//		if(TestCoverageMapping.getTestsCoverageByCalledMethod("Float br.ufrn.framework.OperacaoDivisao.resultado()").size() > 0){
//			System.out.println();
//			System.out.println("Tests that cover the modified method: Float br.ufrn.framework.OperacaoDivisao.resultado()");
//			for(TestCoverage testCoverage2 : TestCoverageMapping.getTestsCoverageByCalledMethod("Float br.ufrn.framework.OperacaoDivisao.resultado()")){
//				System.out.println("\t\t"+(testCoverage2.isTestMethod() ? "JUnit" : "")+"Test Start Method: "+testCoverage2.getCoveredMethods().iterator().next().getSignature());
//				System.out.println("\t\t\tInputs:");
//				for(Input input : testCoverage2.getInputs()){
//					System.out.println("\t\t\t\t"+input.getType().getName()+" "+input.getName()+": "+input.getValue());
//				}
//			}
//		}
//		System.out.println();
//		System.out.println("---------------------------------------------------------------");
//		System.out.println();
	}
	
	private String getParameterName(Member member) {
		String parameterName = member.getName().substring(3);
		parameterName = parameterName.substring(0, 1).toLowerCase()+parameterName.substring(1);
		return parameterName;
	}
	
	private Member getMember(Signature sig) {
		if (sig instanceof MethodSignature)
			return ((MethodSignature) sig).getMethod();
		else if (sig instanceof ConstructorSignature)
			return ((ConstructorSignature) sig).getConstructor();
		
		return null;
	}
	
	private boolean isManagedBeanOrTestMethod(Member member) {
		return isManagedBeam(member) || isTestMethod(member);
	}

	private boolean isManagedBeam(Member member) {
		if(member instanceof Method){
			Method method = (Method) member;
			Annotation anotations[] = method.getDeclaringClass().getAnnotations();
			boolean managedBeam = Modifier.isPublic(method.getModifiers()) && (!method.getName().startsWith("set") && !method.getName().startsWith("get") &&
								  !method.getName().startsWith("is"));
			for(Annotation annotation : anotations){
				String packageClass = annotation.annotationType().getPackage().getName() + annotation.annotationType().getName();
				if(packageClass.equals("javax.context.SessionScoped") || packageClass.equals("javax.context.ApplicationScoped") || packageClass.equals("javax.context.ConversationScoped") ||
				   packageClass.equals("javax.context.RequestScoped") || packageClass.equals("javax.annotation.ManagedBean")){
					managedBeam = managedBeam && true;
					break;
				}
			}
			return managedBeam;
		}
		return false;
	}
	
	private boolean isTestMethod(Member member) {
		if(member instanceof Method)
			return ((Method) member).getAnnotation(Test.class) != null;
		return false;
	}
	
}
