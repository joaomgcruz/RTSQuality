package br.test.source;

import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

public class Root {
	
	public static void main(String[] args) {
		Root r = new Root(12.2, new int[]{1,2}, new float[]{1,2});
		r.methodD();
		
		methodA();
		methodB();
		methodA();
	}
	
	public Root(Double d, int a[], float b[]) {
		methodA();
	}

	@Scenario(name = "root")
	private static Other methodA() {
		return new Other(2);
	}

	private static void methodB() {
		methodB(2);
		Other.methodC(null);
	}
	
	private static void methodB(int i) {
		methodB(2);
		Other.methodC(null);
	}
	
	public void methodD() {
		Other.methodC(null);
	}
	
	
	
}
