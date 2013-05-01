package br.com.ecommerce.jsf.buypath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.dao.ProdutoDAO;
import br.com.ecommerce.dominio.produto.Produto;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;

/**
 * Controlador para tratar de operações com Carrinho de Compras
 * 
 * @author Mario Torres
 * 
 */
@Component
@Scope("session")
@KeepAlive
public class CarrinhoMBean extends CadastroAbstractController<Produto> {

	private static final String CARRINHO = "/buypath/carrinho.jsp";
	
	private List<Produto> produtos;
	
	private double valorTotal;
		
	public CarrinhoMBean() {
		//reset();
		produtos = new ArrayList<Produto>();
		valorTotal = 0;
	}

	/**
	 * Reinicia as variáveis.
	 */
	private void reset() {
		obj = new Produto();
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void addProduto(Produto produto) {
		produtos.add(produto);
	}

	public String iniciarCarrinho() {
		return forward(CARRINHO);
	}
	
	// analyzer annotations
	@Scenario(name="Include product item to cart")
	public String incluirItemCarrinho() throws DAOException {
				reset();
				int idProduto = Integer.parseInt(getParameter("idProduto"));
				Produto itemDaCompra = getDAO(ProdutoDAO.class).findByPrimaryKey(
						idProduto, Produto.class);
				
				//itemDaCompra.setQuantidade(1);
				
				addProduto(itemDaCompra);
				
				valorTotal+=itemDaCompra.getPreco();
				
				//produto = itemDaCompra;
				//sessao.setAttribute("carrinho_do_usuario", carrinho);

				return forward(CARRINHO);
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public String removerProduto() throws DAOException {
		int idProduto = Integer.parseInt(getParameter("idProduto"));
		Produto produtoRemocao = getDAO(ProdutoDAO.class).findByPrimaryKey(
				idProduto, Produto.class);
		
		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto prod = (Produto) iterator.next();
			if (prod.getId() == produtoRemocao.getId()) {
				valorTotal-=produtoRemocao.getPreco();
				iterator.remove();
				break;
			}
		} 
		
		
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Remoção de produto do carrinho");
	
		return null;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
