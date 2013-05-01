package br.com.ecommerce.jsf.administration.productManagement;

import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.com.ecommerce.arq.sbeans.SBeanInativacao;
import br.com.ecommerce.dominio.produto.SubTipoProduto;
import br.com.ecommerce.dominio.produto.TipoProduto;
import br.com.ecommerce.jsf.ConstantesNavegacao;

/**
 * Controlador responsável por operações relacionadas a sub-tipos de produtos.
 * Trabalha em cima do cadastro, remoção, listagem.
 * Oferece métodos para suggestion-box, e listagem de items.
 * 
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Component
@Scope("request")
public class SubTipoProdutoMBean extends CadastroAbstractController<SubTipoProduto>{
	
	/**
	 * Página reponsável pelo crud do tipo de produto.
	 */
	private static final String CRUD_FORM = "/administration/content_management/product_database_management/cruds/subtipo_produto.jsf";
	
	/**
	 * Página reponsável pela visualização detalhada do tipo de produto.
	 */
	private static final String VISUALIZAR_DETALHADAMENTE = "";
	
	/**
	 * Tipo de produto que será visualizado.
	 */
	private SubTipoProduto SubTipoProdutoVisualizado;
	
	/**
	 * Processador Cadastro.
	 */
	@Autowired
	private SBeanCadastro sBeanCadastro;
	
	/**
	 * Processador responsável pela inativação de PersistDBs
	 */
	@Autowired
	private SBeanInativacao SBeanInativacao;
	
	public SubTipoProdutoMBean(){
		reset();
	}
	
	/**
	 * Reinicia as variáveis do mbean.
	 */
	private void reset(){
		obj = new SubTipoProduto();
		obj.setTipo(new TipoProduto());
	}
	
	/**
	 * Método chamado para se enviar para a página de crud.
	 * 
	 * @return forward(CRUD_FORM)
	 */
	public String iniciarCadastro(){
		reset();
		return forward(CRUD_FORM);
	}
	
	/**
	 * Método chamado para se validar e cadastrar um novo subtipo de produtos.
	 * 
	 * @return null
	 * @throws DAOException 
	 */
	public String cadastrar() throws DAOException{
		
		if(isEmpty(obj.getDenominacao()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Denominação");
		
		if(isEmpty(obj.getDescricao()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Descrição");

		if(isEmpty(obj.getTipo()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Tipo de Produto");
		
		if(hasMensagens())
			return null;

		sBeanCadastro.cadastrar(obj);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Cadastro de Sub-Tipo de Produto");
		
		reset();
		return cancelar();
	}
	
	/**
	 * Método usado para se cancelar.
	 * @return
	 */
	public String cancelar(){
		return redirect(ConstantesNavegacao.PORTAL_ADMINISTRACAO);
	}
	
	/**
	 * Termite a visualização de forma detalhada de um determinado tipo de produto.
	 * 
	 * @return null
	 * @throws DAOException 
	 * @throws NumberFormatException 
	 */
	public String visualizarDetalhadamente() throws NumberFormatException, DAOException{
		SubTipoProduto subTipo = getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idSubTipoProduto")), SubTipoProduto.class);
		
		if(subTipo.isInativo())
			addMensagem(MensagensArquitetura.ELEMENTO_NAO_DISPONIVEL_NO_BANCO, "Tipo de Produto");
		
		if(hasMensagens())
			return null;
		
		return forward(VISUALIZAR_DETALHADAMENTE);
	}
	
	/**
	 * Permite a remoção de um determinado sub-tipo de produto.
	 * 
	 * @return
	 * @throws DAOException 
	 * @throws NumberFormatException 
	 */
	public String remover() throws NumberFormatException, DAOException{
		SubTipoProduto subTipoProduto = getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idSubTipoProduto")), SubTipoProduto.class);
		
		if(subTipoProduto.isInativo())
			addMensagem(MensagensArquitetura.SOLICITACAO_JA_PROCESSADA, "Remoção do tipo de Produto");
		
		if(hasMensagens())
			return null;
		
		SBeanInativacao.inativar(subTipoProduto);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Remoção do tipo de produto");
		
		return redirect(CRUD_FORM);
	}
	
	/**
	 * Método usado para se buscar por todos os sub-tipos de produtos ativos.
	 * @return os tipos ativos encontrados.
	 * 
	 * @throws DAOException 
	 */
	public List<SelectItem> getAllCombo() throws DAOException{
		return toSelectItems(getGenericDAO().findAllAtivos(SubTipoProduto.class), "id", "denominacao");
	}
	
	/**
	 * Busca todos os sub-tipos ativos.
	 * 
	 * @return
	 * @throws DAOException 
	 */
	public List<SubTipoProduto> getAllSubTiposProduto() throws DAOException{
		return (List<SubTipoProduto>) getGenericDAO().findAllAtivos(SubTipoProduto.class);
	}

	public void setSubTipoProdutoVisualizado(SubTipoProduto subTipoProdutoVisualizado) {
		SubTipoProdutoVisualizado = subTipoProdutoVisualizado;
	}

	public SubTipoProduto getSubTipoProdutoVisualizado() {
		return SubTipoProdutoVisualizado;
	}
}
