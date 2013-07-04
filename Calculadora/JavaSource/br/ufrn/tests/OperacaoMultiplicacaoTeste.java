package br.ufrn.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufrn.framework.OperacaoMultiplicacao;

public class OperacaoMultiplicacaoTeste {
	
	public OperacaoMultiplicacaoTeste() {
		
	}
	
	@Test
	public void testResultado() {
		OperacaoMultiplicacao op = new OperacaoMultiplicacao();
		op.setTermoUm(new Float(2));
		op.setTermoDois(new Float(2));
		assertEquals(new Float(4.0f),op.resultado());
	}

}
