package br.ufrn.ui;

import javax.context.RequestScoped;

import br.ufrn.framework.Operacao;
import br.ufrn.framework.OperacaoAdicao;
import br.ufrn.framework.OperacaoDivisao;
import br.ufrn.framework.OperacaoMultiplicacao;
import br.ufrn.framework.OperacaoSubtracao;

@RequestScoped
public class OperacaoBean {
	
	private String termoUm;
	private String termoDois;
	private String resultado;
	private String alerta;
	private boolean track;
	
	public OperacaoBean() {
		termoUm = new String("0");
		termoDois = new String("0");
		resultado = new String("0");
		alerta = new String("");
		track = false;
	}

	public String operacaoAdicao(){
		setAlerta("");
		OperacaoAdicao o = new OperacaoAdicao();
		try{
			o.setTermoUm(Float.valueOf(termoUm));
			o.setTermoDois(Float.valueOf(termoDois));
			resultado = o.resultado().toString();
			o.toString();
		}catch(NumberFormatException nfe){
			alerta = "Os valores dos termos só podem receber números.";
		}
		return "index";
	}
	
	public String operacaoSubtracao(){
		setAlerta("");
		Operate(new OperacaoSubtracao());
		return "index";
	}
	
	public String operacaoMultiplicacao(){
		alerta = "";
		Operate(new OperacaoMultiplicacao());
		return "index";
	}
	
	public String operacaoDivisao(){
		alerta = "";
		OperacaoDivisao o = new OperacaoDivisao();
		try{
			o.setTermoUm(Float.valueOf(termoUm));
			o.setTermoDois(Float.valueOf(termoDois));
			resultado = o.resultado().toString();
		}catch(NumberFormatException nfe){
			alerta = "Os valores dos termos só podem receber números.";
		}
		return "index";
	}
	
	/**
	 * Realiza a operação.
	 * @param o
	 */
	private void Operate(Operacao o) {
		try{
			o.setTermoUm(Float.valueOf(termoUm));
			o.setTermoDois(Float.valueOf(termoDois));
			resultado = o.resultado().toString();
		}catch(NumberFormatException nfe){
			alerta = "Os valores dos termos só podem receber números.";
		}
	}

	public String getTermoUm() {
		return termoUm;
	}

	public void setTermoUm(String termoUm) {
		this.termoUm = termoUm;
	}

	public String getTermoDois() {
		return termoDois;
	}

	public void setTermoDois(String termoDois) {
		this.termoDois = termoDois;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public boolean isTrack() {
		return track;
	}

	public void setTrack(boolean track) {
		this.track = track;
	}

}
