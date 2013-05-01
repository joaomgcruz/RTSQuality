package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.Method;
import java.util.Stack;

import org.aspectj.lang.reflect.MethodSignature;

import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.d.data.ExecutionPaths;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph;
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
	
	public static final Stack<Node> nodes_stack = new Stack<Node>();
	
	@ScenarioIgnore
	Object around() : execution(* *.*(..)) && !@annotation(br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore) {
		long begin, end;
		
		Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
		Scenario ann_scenario = method.getAnnotation(Scenario.class);
		
		Node node = new Node(method);
		
		if (ann_scenario != null) {
			RuntimeCallGraph cg = new RuntimeCallGraph(ann_scenario.name(), node);
			ExecutionPaths.getInstance().addRuntimeCallGraph(cg);
		}
		else if (nodes_stack.empty()) {
			/*
			 * se a pilha estiver vazia neste ponto é porque estamos executando
			 * um método que não faz parte de cenário algum (ou um cenário não anotado).
			 * Como não está anotado, simplesmente liberamos a execução do método
			 * e não fazemos nada.
			 */
			return proceed();
		}
		
		if (!nodes_stack.empty())
			nodes_stack.peek().addChild(node);
		
		nodes_stack.push(node);
		
		begin = System.currentTimeMillis();
		Object o = proceed();
		end = System.currentTimeMillis();
		
		nodes_stack.pop().setLastTime(end - begin);
		
		return o;
	}
	
}
