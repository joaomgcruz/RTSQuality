package br.ufrn.framework;

public abstract class Operacao {
	
	private float termoUm = 0.0f;
	private float termoDois = 0.0f;

	public void setTermoUm(Float valor) {
		termoUm = valor.floatValue();
	}
	
	public void setTermoDois(Float valor) {
		termoDois = valor.floatValue();
	}
			
	public float getTermoUm() {
		return termoUm;
	}

	public float getTermoDois() {
		return termoDois;
	}

	public abstract Float resultado();
	
	public void voidMethod() {
		System.out.println("voidMethod");
	}
	
}
