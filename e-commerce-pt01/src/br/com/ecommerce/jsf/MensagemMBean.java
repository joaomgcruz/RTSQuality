package br.com.ecommerce.jsf;

import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.SBeanAlteracao;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.com.ecommerce.arq.sbeans.SBeanInativacao;
import br.com.ecommerce.dominio.Mensagem;

/**
 * Controlador para tratar das mensagens da home page.
 * @author Thiago Viana Dantas
 *
 */
@Component
@Scope("session")
@KeepAlive
public class MensagemMBean extends CadastroAbstractController<Mensagem> {
	
	private static final String CADASTRO_MENSAGEM = "/mensagem.jsf";
	
	/**
	 * Processador Cadastro.
	 */
	@Autowired
	private SBeanCadastro sBeanCadastro;
	
	/**
	 * Processador responsável pela inativação de PersistDBs
	 */
	@Autowired
	private SBeanInativacao sBeanInativacao;
	
	/**
	 * Processador responsável pela inativação de PersistDBs
	 */
	@Autowired
	private SBeanAlteracao sBeanAlteracao;
	
	public MensagemMBean(){
		reset();
	}
	
	private void reset(){
		obj = new Mensagem();
	}
	
	/**
	 * Permite o cadastro de uma nova mensagem.
	 * 
	 * @return
	 */
	public String iniciarCadastro(){
		reset();
		return forward(CADASTRO_MENSAGEM);
	}
	
	public String cadastrar() throws DAOException{
		if(isEmpty(obj.getDenominacao()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Mensagem");
		if(hasMensagens())
			return null;
		obj.setAtivado(true);
		sBeanCadastro.cadastrar(obj);
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO,
		"Cadastro de mensagens");
		return forward(CADASTRO_MENSAGEM);
		
	} 
	
	/**
	 * Busca todas as mensagens ativas.
	 * 
	 * @return
	 * @throws DAOException 
	 */
	public List<Mensagem> getAllMensagem() throws DAOException{
		return (List<Mensagem>) getGenericDAO().findAllAtivos(Mensagem.class);
	}
	
	
	/**
	 * Permite a remoção de uma mensagem.
	 * 
	 * @return
	 * @throws DAOException 
	 * @throws NumberFormatException 
	 */
	public String removerMensagem() throws NumberFormatException, DAOException{
		Mensagem mensagem = getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idMensagem")), Mensagem.class);
		
	
		if(mensagem.isInativo())
			addMensagem(MensagensArquitetura.SOLICITACAO_JA_PROCESSADA, "Remoção da mensagem");
		
		if(hasMensagens())
			return null;
		
		sBeanInativacao.inativar(mensagem);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Remoção da mensagem");
		
		return redirect(CADASTRO_MENSAGEM);
	}
	
	/**
	 * Permite a ativação de uma mensagem.
	 * 
	 * @return
	 * @throws DAOException 
	 * @throws NumberFormatException 
	 */
	public String ativarMensagem() throws NumberFormatException, DAOException{
		Mensagem mensagem = getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idMensagem")), Mensagem.class);
		
	
		if(mensagem.isAtivado())
			addMensagemInformation("Mensagem já está ativa.");
		
		if(hasMensagens())
			return null;
		
		mensagem.setAtivado(true);
		sBeanAlteracao.atualizar(mensagem);
		desativarMensagem(mensagem);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Ativação da mensagem");
		
		return redirect(CADASTRO_MENSAGEM);
	}
	
	/**
	 * Desativa todas as mensagens diferente da passada como parâmetro.
	 * @param mens
	 * @return
	 * @throws NumberFormatException
	 * @throws DAOException
	 */
	public String desativarMensagem(Mensagem mens) throws NumberFormatException, DAOException{
		List<Mensagem> mensagem = getAllMensagem();
		for (int i = 0;i< mensagem.size();i++){
			if (mensagem.get(i).getId() != mens.getId()){
				mensagem.get(i).setAtivado(false);
				sBeanAlteracao.atualizar(mensagem.get(i));
			}
		}
		
		return null;
	}
}
