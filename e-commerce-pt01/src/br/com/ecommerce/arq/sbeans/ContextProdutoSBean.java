package br.com.ecommerce.arq.sbeans;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.erros.DAOException;

public class ContextProdutoSBean {
	private IProdutoSBean strategy;
	 
    public ContextProdutoSBean(IProdutoSBean strategy) {
        this.strategy = strategy;
    }
    
    public ContextProdutoSBean() {
        //this.strategy = new ProdutoSBeanComAutorizacao();
        this.strategy = new ProdutoSBeanSemAutorizacao();
    }
 
    public void cadastrar(CadastroDB obj) throws DAOException {
    	strategy.cadastrar(obj);
    }

	public void autorizarProduto(int idProduto) throws DAOException {
		strategy.autorizarProduto(idProduto);
	}

	public void cancelarProduto(int idProduto) throws DAOException {
		strategy.cancelarProduto(idProduto);
	}
}
