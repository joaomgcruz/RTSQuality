package br.com.ecommerce.jsf.administration.productManagement;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.hibernate.HibernateException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.ContextProdutoSBean;
import br.com.ecommerce.dao.ProdutoDAO;
import br.com.ecommerce.dominio.TipoArmazenamento;
import br.com.ecommerce.dominio.produto.CampoExtraProduto;
import br.com.ecommerce.dominio.produto.Produto;
import br.com.ecommerce.dominio.produto.SubTipoProduto;
import br.com.ecommerce.dominio.produto.TipoCampoExtraProduto;
import br.com.ecommerce.dominio.produto.TipoProduto;

/**
 * Controlador para tratar de operações com produtos
 * 
 * @author Rodrigo Dutra de Oliveira
 * 
 */
@Component
@Scope("session")
@KeepAlive
public class ProdutoMBean extends CadastroAbstractController<Produto> {

	/**
	 * Página reponsável pela obtenção dos dados básicos de um determinado
	 * produto.
	 */
	private static final String INFORMACOES_GERAIS = "/administration/content_management/product_database_management/produtos/cadastro/produto_informacoes_gerais_form.jsf";

	/**
	 * Página responsável por cadastrar campos extras ao produto.
	 */
	private static final String CADASTRO_CAMPOS_EXTRAS = "/administration/content_management/product_database_management/produtos/cadastro/produto_campos_extras_form.jsf";

	private static final String TELA_AUTORIZACAO = "/administration/content_management/product_database_management/produtos/autorizacao_produtos.jsf";

	/**
	 * Representa um novo campo extra de produto.
	 */
	private CampoExtraProduto campoNovo;

	//@Autowired
	//private ProdutoSBean produtoSBean;
	
	private ContextProdutoSBean produtoSBean;
	
	private List<Produto> produtosNaoAutorizados;

	public ProdutoMBean() {
		reset();
	}

	/**
	 * Reinicia as variáveis.
	 */
	private void reset() {
		obj = new Produto();
		obj.setTipo(new TipoProduto());
		obj.setTipoArmazenamento(new TipoArmazenamento());
		obj.setSubTipo(new SubTipoProduto());
		obj.setCamposExtras(new ArrayList<CampoExtraProduto>());
		
		produtoSBean = new ContextProdutoSBean();
		//produtoSBean = new ContextProdutoSBean(new ProdutoSBeanSemAutorizacao());
		//produtoSBean = new ContextProdutoSBean(new ProdutoSBeanComAutorizacao());

		resetCampoNovo();
	}

	/**
	 * Reseta o campo novo, campo extra de tipo de produto.
	 */
	private void resetCampoNovo() {
		campoNovo = new CampoExtraProduto();
		campoNovo.setTipo(new TipoCampoExtraProduto());
	}

	/**
	 * Permite o cadastro de um novo produto.
	 * 
	 * @return
	 */
	public String iniciarCadastro() {
		reset();
		return forward(INFORMACOES_GERAIS);
	}

	public String iniciarAutorizacao() throws HibernateException, DAOException {
		reset();
		produtosNaoAutorizados = getDAO(ProdutoDAO.class).findAllAtivos();
		if (produtosNaoAutorizados.size() == 0)
			addMensagemInformation("Não existe produtos pendentes à autorização.");
		return forward(TELA_AUTORIZACAO);
	}

	/**
	 * Autoriza o cadastro do produto
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws DAOException
	 */
	public String autorizarProduto() throws NumberFormatException, DAOException {
		produtoSBean.autorizarProduto(Integer
				.parseInt(getParameter("idProduto")));
		return iniciarAutorizacao();
	}

	/**
	 * Cancela o cadastro do produto
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws DAOException
	 */
	public String cancelarProduto() throws NumberFormatException, DAOException {
		produtoSBean.cancelarProduto(Integer
				.parseInt(getParameter("idProduto")));
		return iniciarAutorizacao();
	}

	/**
	 * Valida os compos das informações básicas e avança no fluxo, se validado.
	 * 
	 * @return null
	 */
	public String cadastroCamposExtras() {
		if (isEmpty(obj.getNome()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
					"Nome");

		if (isEmpty(obj.getTipo()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
					"Tipo");

		if (isEmpty(obj.getSubTipo()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
					"SubTipo");

		if (obj.getPreco() == 0.0)
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
					"Preço");
	/*	if (isEmpty(obj.getWarranty()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
				"Garantia");
		if (isEmpty(obj.getSize()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
				"Tamanho");
		if (obj.getWeight()  == 0.0)
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
				"Peso");
		*/
		if (hasMensagens())
			return null;

		return forward(CADASTRO_CAMPOS_EXTRAS);
	}

	/**
	 * Busca os subtipos do tipo escolhido.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<SelectItem> getSubTiposAjax() throws DAOException {
		return toSelectItems(getGenericDAO().findByExactField(
				SubTipoProduto.class, "tipo.id", obj.getTipo().getId()));
	}

	/**
	 * Método usado para se incluir um novo campo extra de produto.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String adicionarCampoExtra() throws DAOException {
		if (isEmpty(campoNovo.getTipo()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
					"Tipo");
		if (isEmpty(campoNovo.getConteudo()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO,
					"Conteudo");

		if (hasMensagens())
			return null;

		campoNovo.setTipo(getGenericDAO().findByPrimaryKey(
				campoNovo.getTipo().getId(), TipoCampoExtraProduto.class));
		// Adiciona o novo campo.
		obj.getCamposExtras().add(campoNovo);
		resetCampoNovo();

		return null;
	}

	/**
	 * Método usado para se finalizar o cadastro de um produto.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public String finalizarCadastroProduto() throws DAOException {
		
		produtoSBean.cadastrar(obj);
		reset();

		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO,
				"Cadastro de Produto");
		return cancelar();

	}

	public List<Produto> getThreeProduto() throws DAOException{
		return getDAO(ProdutoDAO.class).getThreeProducts();
	}
	
	public void setCampoNovo(CampoExtraProduto campoNovo) {
		this.campoNovo = campoNovo;
	}

	public CampoExtraProduto getCampoNovo() {
		return campoNovo;
	}

	public void setProdutosNaoAutorizados(List<Produto> produtosNaoAutorizados) {
		this.produtosNaoAutorizados = produtosNaoAutorizados;
	}

	public List<Produto> getProdutosNaoAutorizados() {
		return produtosNaoAutorizados;
	}
}
