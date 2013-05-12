package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;
import java.util.Stack;

import org.aspectj.lang.reflect.MethodSignature;

import br.ufrn.ppgsc.scenario.analyzer.annotations.Robustness;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.d.data.ExecutionPaths;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeRobustnessData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph.Node;

public aspect AspectScenario {
	
	/*
	 * ter uma anotção de scenario é caso base para iniciar a construção da estrutura
	 * cada vez que uma anotação de cenário é encontrada ela entra na lista de caminhos,
	 * pois todos as invocações são subchamadas desse cenário.
	 * 
	 * eu preciso interceptar tudo para pegar invocações dentro do cenário que nao estão
	 * anotadas, já que anotamos apenas o método root, então criei uma anotação para ignorar
	 * métodos usados pelo aspect que não deviam ser interceptados, pois não são métodos da
	 * aplicação
	 * 
	 * o quanto as interceptações de performance, security e reliability influenciam na medição
	 * do tempo para executar um nó ou um cenário inteiro?
	 * 
	 */
	
	private static final Stack<Node> nodes_stack = new Stack<Node>();
	
	pointcut scenarioIgnored(): within(AspectScenario) || @annotation(br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore);
	pointcut allExecution(): execution(* *.*(..));
	
	Object around() : allExecution() && !scenarioIgnored() {
		long begin, end;
		
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Scenario ann_scenario = method.getAnnotation(Scenario.class);
		
		Node node = new Node(method);
		
		/*
		 * Se achou a anotação de cenário, começa a criar as estruturas para ele
		 */
		if (ann_scenario != null) {
			RuntimeCallGraph cg = new RuntimeCallGraph(ann_scenario.name(), node);
			ExecutionPaths.getInstance().addRuntimeCallGraph(cg);
		}
		else if (nodes_stack.empty()) {
			/*
			 * se a pilha estiver vazia e a anotação não existe neste ponto é porque estamos executando
			 * um método que não faz parte de cenário algum (ou um cenário não anotado).
			 * Como não está anotado, simplesmente liberamos a execução do método
			 * e não fazemos nada.
			 */
			return proceed();
		}
		
		/*
		 * Se já existe alguma coisa na pilha, então o método atual
		 * foi invocado pelo último método que está na pilha
		 */
		if (!nodes_stack.empty())
			nodes_stack.peek().addChild(node);
		
		nodes_stack.push(node);
		
		begin = System.currentTimeMillis();
		Object o = proceed();
		end = System.currentTimeMillis();
		
		nodes_stack.pop().setLastTime(end - begin);
		
		return o;
	}
	
	after() throwing(Throwable t) : allExecution() && !scenarioIgnored()  {
		setRobustness((MethodSignature) thisJoinPoint.getSignature());
	}
	
	before(Throwable t) : handler(Throwable+) && args(t) && !within(AspectScenario) {
		setRobustness((MethodSignature) thisEnclosingJoinPointStaticPart.getSignature());
	}
	
	/* TODO
	 * Existem situações que a exceção aborda a execução do programa.
	 * Nestes casos, o método abaixo deve sempre já gravar a falha,
	 * enquanto o sistema ainda está em execução, pois quando o aspecto
	 * desenvolver o sistema pode cair e perder as estruturas em memória
	 * */
	private static void setRobustness(MethodSignature ms) {
		// se estiver vazia é porque o método não faz parte de cenário
		if (!nodes_stack.empty()) {
			Node node = nodes_stack.peek();
		
			if (node.getMethod().equals(ms.getMethod()))
				node.setFail(true);
		}
	}
	
}
