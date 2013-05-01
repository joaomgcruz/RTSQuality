package br.com.ecommerce.arq.sbeans;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.erros.DAOException;

/**
 * Session Bean relativo a opera?›es com produto.
 * 
 * @author Mario Torres
 * 
 */
public interface IProdutoSBean {

	public void cadastrar(CadastroDB obj) throws DAOException;

	public void autorizarProduto(int idProduto) throws DAOException;

	public void cancelarProduto(int idProduto) throws DAOException;
}
