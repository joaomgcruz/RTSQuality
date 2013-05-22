package br.ufrn.ppgsc.scenario.analyzer.d.aspects;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.d.data.ExecutionPaths;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeNode;

/*
 * Ter uma anotção de scenario é caso base para iniciar a construção da estrutura.
 * Cada vez que uma anotação de cenário é encontrada ela entra na lista de caminhos,
 * e todas as próximas invocações são subchamadas desse cenário.
 * 
 * Eu preciso interceptar tudo para pegar invocações dentro do cenário que nao estão
 * anotadas, já que anotamos apenas o método root. O pointcut executionIgnored é usado
 * para ignorar elementos definidos dentro da minha implementação que eventualmente são
 * chamados pelo próprio aspecto e que não deviam ser interceptados, pois não são métodos
 * da aplicação.
 * 
 * TODO: verificar o quanto as interceptações e operações feitas dentro dos aspectos estão
 * influenciando negativamente na medição do tempo para executar um nó ou um cenário inteiro.
 * 
 * TODO: adaptar este aspecto para considerar também construtores, pois eles também podem
 * invocar métodos e devem ser considerados na construção do grafo dinâmico.
 * 
 * TODO: limitação para quando o cenário já iniciou e o mesmo se divide em threads.
 * Testar isso http://dev.eclipse.org/mhonarc/lists/aspectj-users/msg12554.html
 * 
 */
public aspect AspectScenario {
	
	// Cada thread pode ter uma pilha de execução diferente
	private final Map<Long, Stack<RuntimeNode>> thread_map = new Hashtable<Long, Stack<RuntimeNode>>();
	
	private pointcut executionIgnored() : within(br.ufrn.ppgsc.scenario.analyzer..*);
	
	private pointcut scenarioExecution() :
		cflow(execution(* *(..)) && @annotation(br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario)) &&
		(execution(* *(..)) || execution(*.new(..)));
	
	Object around() : scenarioExecution() && !executionIgnored() {
		long begin, end;
		
		Stack<RuntimeNode> nodes_stack = getOrCreateRuntimeNodeStack();
		
		Member member = getMember(thisJoinPoint.getSignature());
		
		Scenario ann_scenario = ((AnnotatedElement) member).getAnnotation(Scenario.class);
		
		RuntimeNode node = new RuntimeNode(member);
		
		// Se achou a anotação de cenário, começa a criar as estruturas para ela
		if (ann_scenario != null) {
			RuntimeCallGraph cg = new RuntimeCallGraph(ann_scenario.name(), node);
			ExecutionPaths.getInstance().addRuntimeCallGraph(cg);
		}
		else if (nodes_stack.empty()) {
			/* TODO: decidir o que fazer nesta situação?
			 * Se a pilha estiver vazia e a anotação não existe neste ponto é porque
			 * estamos em uma das sitações abaixo:
			 * - Temos um método que não faz parte de cenário anotado
			 * - O cenário atual está dividindo sua execução em threads
			 */
			return proceed();
		}
		
		/*
		 * Se já existe alguma coisa na pilha, então o método atual
		 * foi invocado pelo último método que está na pilha
		 */
		if (!nodes_stack.empty()) {
			nodes_stack.peek().addChild(node);
		}
		
		nodes_stack.push(node);
		
		begin = System.currentTimeMillis();
		Object o = proceed();
		end = System.currentTimeMillis();
		
		nodes_stack.pop().setTime(end - begin);
		
		return o;
	}
	
	after() throwing(Throwable t) : scenarioExecution() && !executionIgnored()  {
		setRobustness(t, getMember(thisJoinPoint.getSignature()));
		getOrCreateRuntimeNodeStack().pop().setTime(-1);
	}
	
	before(Throwable t) : handler(Throwable+) && args(t) && !executionIgnored() {
		setRobustness(t, getMember(thisEnclosingJoinPointStaticPart.getSignature()));
	}
	
	private Member getMember(Signature sig) {
		if (sig instanceof MethodSignature)
			return ((MethodSignature) sig).getMethod();
		else if (sig instanceof ConstructorSignature)
			return ((ConstructorSignature) sig).getConstructor();
		
		return null;
	}
	
	/* TODO: Existem situações que a exceção aborta a execução do programa.
	 * Nestes casos, o método abaixo deve sempre já gravar a falha,
	 * enquanto o sistema ainda está em execução, pois quando o aspecto
	 * devolver a execução o sistema pode cair e perder as estruturas em memória
	 */
	private void setRobustness(Throwable t, Member m) {
		Stack<RuntimeNode> nodes_stack = getOrCreateRuntimeNodeStack();
		
		// Se estiver vazia é porque o método não faz parte de cenário
		if (!nodes_stack.empty()) {
			RuntimeNode node = nodes_stack.peek();
		
			// Testa se foi o método que capturou ou lançou a exceção
			if (node.getMember().equals(m))
				node.setException(t);
		}
	}
	
	private Stack<RuntimeNode> getOrCreateRuntimeNodeStack() {
		long thread_id = Thread.currentThread().getId();
		Stack<RuntimeNode> nodes_stack = thread_map.get(thread_id);
		
		if (nodes_stack == null) {
			nodes_stack = new Stack<RuntimeNode>();
			thread_map.put(thread_id, nodes_stack);
		}
		
		return nodes_stack;
	}
	
}
