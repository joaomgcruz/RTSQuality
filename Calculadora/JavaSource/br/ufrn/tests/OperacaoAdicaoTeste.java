package br.ufrn.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufrn.framework.Operacao;
import br.ufrn.framework.OperacaoAdicao;

public class OperacaoAdicaoTeste {
		
	public OperacaoAdicaoTeste() {
	}
	
	@Test
	public void testResultado() {
		Operacao op = new OperacaoAdicao();
		op.setTermoUm(new Float(1));
		op.setTermoDois(new Float(2));
		assertEquals(new Float(4.0f),op.resultado());
	}
	
	@Test
	public void resultado2() {
		Operacao op = new OperacaoAdicao();
		op.setTermoUm(new Float(1));
		op.setTermoDois(new Float(2));
		assertEquals(new Float(3.0f),op.resultado());
	}

}
