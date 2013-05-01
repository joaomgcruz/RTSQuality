package br.com.ecommerce.jsf.administration.fulfilment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.dominio.ArquivoPersistente;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.com.ecommerce.arq.util.FIleUtil;

@Component
@Scope("session")
public class FileRepositoryMBean extends CadastroAbstractController<ArquivoPersistente>{

	private static final String CADASTRO_FORM = "/administration/fulfilment/FileRepository/arquivo_form.jsp";
	
	private List<UploadItem> arquivos;
	
	@Autowired
	private SBeanCadastro sBeanCadastro;
	
	private boolean disabled;
	
	public FileRepositoryMBean(){
		reset();
	}
	
	public void upload(UploadEvent event){
		getArquivos().add(event.getUploadItem());
	}
	
	public void atualizar(ActionEvent event){
		disabled = false;
	}
	
	public String cadastrar() throws DAOException, IOException{
		Iterator<UploadItem> it = arquivos.iterator();
		while(it.hasNext()){
			obj.setArquivo(FIleUtil.getBytesFromFile(it.next().getFile()));
			
			sBeanCadastro.cadastrar(obj);
			obj.setId(0);
			obj.setArquivo(null);
		}
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Cadastro de Arquivo");
		
		return cancelar();
	}
	
	private void reset(){
		obj = new ArquivoPersistente();
		arquivos = new ArrayList<UploadItem>();
		setDisabled(true);
	}
	
	public String iniciarCadastroArquivo(){
		reset();
		return forward(CADASTRO_FORM);
	}

	public void setArquivos(List<UploadItem> arquivos) {
		this.arquivos = arquivos;
	}

	public List<UploadItem> getArquivos() {
		return arquivos;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}
}
