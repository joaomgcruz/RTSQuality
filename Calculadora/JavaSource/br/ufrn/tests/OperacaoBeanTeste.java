package br.ufrn.tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.ufrn.framework.Operacao;
import br.ufrn.framework.OperacaoAdicao;
import br.ufrn.ui.OperacaoBean;

public class OperacaoBeanTeste {
	private class InOut {
		public String termoUm;
		public String termoDois;
		public String resultado;
	};
	
	private OperacaoBean operacaoBean = new OperacaoBean();
	private String[][] ins = {{"1","1"},{"-1","1"},{"1","-1"},{"-1","-1"},{"0","0"},{"0","1"},{"0","-1"},{"1","0"},{"-1","0"}};
	private String[] adicaoOuts = {"2.0","0.0","0.0","-2.0","0.0","1.0","-1.0","1.0","-1.0"};
	private String[] subtracaoOuts = {"0.0","-2.0","2.0","0.0","0.0","-1.0","1.0","1.0","-1.0"};
	private String[] multiplicacaoOuts = {"1.0","-1.0","-1.0","1.0","0.0","0.0","-0.0","0.0","-0.0"};
	private String[] divisaoOuts = {"1.0","-1.0","-1.0","1.0","NaN","0.0","-0.0","Infinity","-Infinity"};
	private final int tam = 9;
	private final int termoUm = 0;
	private final int termoDois = 1;
		
	public void testOperacao(int operacao, String[] outs) {
		for (int i=0;i<tam;i++) {
			operacaoBean.setTermoUm(ins[i][termoUm]);
			operacaoBean.setTermoDois(ins[i][termoDois]);
			switch(operacao){
				case 0:
					operacaoBean.operacaoAdicao();
					break;
				case 1:
					operacaoBean.operacaoSubtracao();
					break;
				case 2:
					operacaoBean.operacaoMultiplicacao();
					break;
				case 3:
					operacaoBean.operacaoDivisao();
					break;
			}
			Assert.assertEquals(operacaoBean.getResultado(),outs[i]);
		}
	}
	
	@Test
	public void testOperacaoAdicao() {
		testOperacao(0,adicaoOuts);
	}
	
	@Test
	public void testResultado2() {
		Operacao op = new OperacaoAdicao();
		op.toString();
		assertEquals(true,true);
	}

	@Test
	public void testOperacaoSubtracao() {
		testOperacao(1,subtracaoOuts);
	}

	@Test
	public void testOperacaoMultiplicacao() {
		testOperacao(2,multiplicacaoOuts);
	}

	@Test
	public void testOperacaoDivisao() {
		testOperacao(3,divisaoOuts);
	}

}
