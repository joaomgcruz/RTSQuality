package br.ufrn.framework;

public class OperacaoAdicao extends Operacao {
	public String teste = "a";

	public Float resultado() {
		return getTermoUm() + getTermoDois();
	}

	public Float resultado2() {
		voidMethod();
		return new Float(1000000000);
	}
	
	public String toString(){
		return teste;
	}
}
