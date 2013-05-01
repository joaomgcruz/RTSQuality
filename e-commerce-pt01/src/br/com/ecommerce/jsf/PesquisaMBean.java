package br.com.ecommerce.jsf;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.dao.ProdutoDAO;
import br.com.ecommerce.dominio.TipoArmazenamento;
import br.com.ecommerce.dominio.produto.CampoExtraProduto;
import br.com.ecommerce.dominio.produto.Produto;
import br.com.ecommerce.dominio.produto.SubTipoProduto;
import br.com.ecommerce.dominio.produto.TipoProduto;
import br.com.ecommerce.jsf.administration.productManagement.ProdutoMBean;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

/**
 * Controlador responsável por operações relacionadas a agendamento de serviços.
 * Trabalha em cima do cadastro, remoção, listagem. Oferece métodos para
 * suggestion-box, e listagem de items.
 * 
 * @author Mario Torres
 */
@Component
@Scope("request")
public class PesquisaMBean extends ProdutoMBean {

	private static final String BUSCA = "/administration/content_management/product_database_management/produtos/listagem/produtos_listagem_geral.jsf";

	private static final String ADVANCED_SEARCH = "/catalog/advancedsearch.jsp";

	private List<Produto> produtosEncontrados;
	
	/**
	 * Opções de busca.
	 */
	private boolean byNome, byObservacao, byTipo, bySubTipo, byCaracteristicas, byAll;

	/**
	 * Reinicia as variáveis.
	 */
	private void reset() {
		obj = new Produto();
		obj.setTipo(new TipoProduto());
		obj.setTipoArmazenamento(new TipoArmazenamento());
		obj.setSubTipo(new SubTipoProduto());
		obj.setCamposExtras(new ArrayList<CampoExtraProduto>());
		
		produtosEncontrados = null;

	}
	
	public PesquisaMBean() {
		super();
	}


	/**
	 * Método chamada para se iniciar a busca por produtos.
	 * 
	 * @return
	 */
	public String iniciarBusca() {
		reset();
		return forward(BUSCA);
	}

	public String initAdvancedSearch() {
		reset();
		return forward(ADVANCED_SEARCH);
	}

	// analyzer annotations
	@Scenario(name="Search for products")
	public String buscarProdutos() throws HibernateException, DAOException {

		produtosEncontrados = getDAO(ProdutoDAO.class).findGeral(
				byNome ? obj.getNome() : null,
				byObservacao ? obj.getObservacao() : null,
				byTipo ? obj.getTipo().getId() : null,
				bySubTipo ? obj.getSubTipo().getId() : null,
				byCaracteristicas ? obj.getCaracteristicas() : null);

		if (produtosEncontrados.size() == 0)
			addMensagem(MensagensArquitetura.BUSCA_SEM_RESULTADOS,
					"Busca por produtos");

		return null;
	}

	public String buscaBasicaProdutos() throws HibernateException, DAOException {

		produtosEncontrados = getDAO(ProdutoDAO.class).findGeral(obj.getNome(),
				null, null, null, null);

		if (produtosEncontrados.size() == 0)
			addMensagem(MensagensArquitetura.BUSCA_SEM_RESULTADOS,
					"Busca por produtos");

		return null;
	}

	public void setProdutosEncontrados(List<Produto> produtosEncontrados) {
		this.produtosEncontrados = produtosEncontrados;
	}

	public List<Produto> getProdutosEncontrados() {
		return produtosEncontrados;
	}

	public boolean isByNome() {
		return byNome;
	}

	public void setByNome(boolean byNome) {
		this.byNome = byNome;
	}

	public boolean isByObservacao() {
		return byObservacao;
	}

	public void setByObservacao(boolean byObservacao) {
		this.byObservacao = byObservacao;
	}

	public boolean isByTipo() {
		return byTipo;
	}

	public void setByTipo(boolean byTipo) {
		this.byTipo = byTipo;
	}

	public boolean isBySubTipo() {
		return bySubTipo;
	}

	public void setBySubTipo(boolean bySubTipo) {
		this.bySubTipo = bySubTipo;
	}

	public boolean isByCaracteristicas() {
		return byCaracteristicas;
	}

	public void setByCaracteristicas(boolean byCaracteristicas) {
		this.byCaracteristicas = byCaracteristicas;
	}

	public boolean isByAll() {
		return byAll;
	}

	public void setByAll(boolean byAll) {
		this.byAll = byAll;
	}
}
