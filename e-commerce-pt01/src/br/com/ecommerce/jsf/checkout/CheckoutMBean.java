package br.com.ecommerce.jsf.checkout;

import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Controlador para tratar de fechamento de compra
 * 
 * @author Mario Torres
 * 
 */
@Component
@Scope("session")
@KeepAlive
public class CheckoutMBean extends CadastroAbstractController<Produto> {

	private static final String CHECKOUT = "/checkout/checkout.jsp";
	
	private List<Produto> produtos;
	
	private double valorTotal;
		
	public CheckoutMBean() {

	}

	/**
	 * Reinicia as variáveis.
	 */
	private void reset() {
	}

	public String iniciarCheckout() {
		 valorTotal = Double.parseDouble(getParameter("valorTotal"));
		return forward(CHECKOUT);
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
