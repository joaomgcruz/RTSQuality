package br.com.ecommerce.arq.jsf.components;

import javax.faces.component.UIOutput;

/**
 * Componente UIOutput da tag de mensagens.
 * @author Rodrigo Dutra de Oliveira
 */
public class MensagensComponent extends UIOutput{
    
	/**
	 * Familía do componente
	 */
    public static final String ECOMMERCE_MENSAGENS_FAMILY = "ECOMMERCE_MENSAGENS_FAMILY";
    
    @Override
    public String getFamily(){
        return  ECOMMERCE_MENSAGENS_FAMILY;
    }
}
