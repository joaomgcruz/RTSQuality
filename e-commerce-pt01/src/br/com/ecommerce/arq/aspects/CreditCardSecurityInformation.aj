package br.com.ecommerce.arq.aspects;

import javax.persistence.Column;

import org.aspectj.lang.annotation.Aspect;

import br.com.ecommerce.dominio.usuario.CreditCard;

public aspect CreditCardSecurityInformation {
	
    @Column(name="dv",columnDefinition="dv") 
	private int CreditCard.dv;
	
    
	public void CreditCard.setDv(int dv) {
		this.dv = dv;
	}
    
	public int CreditCard.getDv() {
		return this.dv;
	}
    
}