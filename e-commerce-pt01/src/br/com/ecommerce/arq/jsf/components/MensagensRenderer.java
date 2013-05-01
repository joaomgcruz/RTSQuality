package br.com.ecommerce.arq.jsf.components;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;

import br.com.ecommerce.arq.dominio.Mensagem;
import br.com.ecommerce.arq.dominio.TipoMensagem;

/**
 * Renderer do componente de mensagens.
 * @author Rodrigo Dutra de Oliveira
 */
public class MensagensRenderer extends Renderer{
    
	@SuppressWarnings("unchecked")
	@Override
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException{
        
		Writer writer = context.getResponseWriter();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		List<Mensagem> mensagens = (List<Mensagem>) request.getSession().getAttribute("mensagens");
		List<Mensagem> mensagensErro = new ArrayList<Mensagem>();
		List<Mensagem> mensagensInformation = new ArrayList<Mensagem>();
		List<Mensagem> mensagensWarning = new ArrayList<Mensagem>();
    	
		for(Mensagem mensagem : mensagens){
			if(mensagem.getTipoMensagem().equals(TipoMensagem.ERRO))
				mensagensErro.add(mensagem);
			else if(mensagem.getTipoMensagem().equals(TipoMensagem.INFORMATION))
				mensagensInformation.add(mensagem);
			else if(mensagem.getTipoMensagem().equals(TipoMensagem.WARNING))
				mensagensWarning.add(mensagem);
		}
		
		for(int i = 0; i < mensagensErro.size(); i++){
			if(i==0)
				writer.write("<center><div class=\"_mensagemErro\">");
			writer.write(mensagensErro.get(i) + "<br />");
			if(i==mensagensErro.size()-1)
				writer.write("</div></center>");
		}
		
		for(int i = 0; i < mensagensInformation.size(); i++){
			if(i==0)
				writer.write("<center><div class=\"_mensagemInformation\">");
			writer.write(mensagensInformation.get(i) + "<br />");
			if(i==mensagensInformation.size()-1)
				writer.write("</div></center>");
		}
		
		for(int i = 0; i < mensagensWarning.size(); i++){
			if(i==0)
				writer.write("<center><div class=\"_mensagemWarning\">");
			writer.write(mensagensWarning.get(i) + "<br />");
			if(i==mensagensWarning.size()-1)
				writer.write("</div></center>");
		}
		
		request.getSession().setAttribute("mensagens", new ArrayList<Mensagem>());
		
    }
}
