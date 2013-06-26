package br.ufrn.framework;

public class OperacaoSubtracao extends Operacao {

	public Float resultado() {
		return getTermoUm() - getTermoDois();
	}

}
