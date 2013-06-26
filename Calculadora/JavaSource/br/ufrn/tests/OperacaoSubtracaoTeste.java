package br.ufrn.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufrn.framework.OperacaoSubtracao;

public class OperacaoSubtracaoTeste {
	
	public OperacaoSubtracaoTeste() {
		
	}
	
	@Test
	public void testResultado() {
		OperacaoSubtracao op = new OperacaoSubtracao();
		op.setTermoUm(new Float(2));
		op.setTermoDois(new Float(1));
		assertEquals(new Float(1.0f),op.resultado());
	}

}
