package br.ufrn.framework;

public class OperacaoAdicao extends Operacao {
	public String teste = "a";
	
	public Float resultado() {
		return getTermoUm() + getTermoDois();
	}
	
	public String toString(){
		return teste;
	}
}
