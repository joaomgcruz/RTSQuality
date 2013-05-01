package br.com.ecommerce.arq.jsf.components;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

/**
 * TagHandler de Mensagens
 * @author rodrigodutra
 */
public class MensagensTagHandler extends UIComponentELTag {

	/**
	 * Tipo do componente
	 */
    public static final String ECOMMERCE_MENSAGENS_COMP_TYPE = "ECOMMERCE_MENSAGENS_COMPONENT";
    
    /**
     * Tipo do Renderer
     */
    public static final String ECOMMERCE_MENSAGENS_RENDERER_TYPE = "ECOMMERCE_MENSAGENS_RENDERER";

    public void setProperties(UIComponent component){
    	super.setProperties(component);
    	
    }

    @Override
    public String getComponentType() {
        return ECOMMERCE_MENSAGENS_COMP_TYPE;
    }

    @Override
    public String getRendererType() {
        return ECOMMERCE_MENSAGENS_RENDERER_TYPE;
    }
}