package br.ufrn.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufrn.framework.OperacaoDivisao;

public class OperacaoDivisaoTeste {
	
	public OperacaoDivisaoTeste() {
		
	}
	
	@Test
	public void testResultado() {
		OperacaoDivisao op = new OperacaoDivisao();
		op.setTermoUm(new Float(1));
		op.setTermoDois(new Float(2));
		assertEquals(new Float(0.5f),op.resultado());
	}

}
