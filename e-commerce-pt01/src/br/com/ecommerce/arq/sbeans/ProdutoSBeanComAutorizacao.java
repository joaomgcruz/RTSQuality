package br.com.ecommerce.arq.sbeans;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Session Bean relativo a opera?›es com produto.
 * @author Mario Torres
 *
 */
@Service
public class ProdutoSBeanComAutorizacao extends AbstractSBean implements IProdutoSBean{

	/**
	 * Método chamado para cadastro de CadastroDBs
	 * @throws DAOException 
	 */
	public void cadastrar(CadastroDB obj) throws DAOException{
		/*TRECHO DE CÓDIGO MOVIDO PARA O ASPECTO
		NÃO REMOVER
		SERÁ UTILIZADO NA APRESENTACAO*/
		/*if(ParametroHelper.getInstance().getBooleanParametro(ProductManagementParametros.NECESSARIO_AVAL_LIBERACAO_PRODUTO)){
			((Produto) obj).setAutorizado(false);
		}else{
			((Produto) obj).setAutorizado(true);
		}
		*/
		obj.setDataCadastro(new Date());
		
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.save(obj);
		}finally{
			dao.close();
		}
		
	}
	
	/**
	 * Método usado para efetuar a autorização de um produto.
	 * @param idProduto
	 * @throws DAOException 
	 */
	public void autorizarProduto(int idProduto) throws DAOException{
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		Produto produto = dao.findByPrimaryKey(idProduto, Produto.class);
		
		produto.setAutorizado(true);
		dao.update(produto);
		try{
			dao.save(produto);
		}finally{
			dao.close();
		}
	}
	
	public void cancelarProduto(int idProduto) throws DAOException{
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		Produto produto = dao.findByPrimaryKey(idProduto, Produto.class);
	
		produto.setInativo(true);
		dao.update(produto);
		try{
			dao.save(produto);
		}finally{
			dao.close();
		}
	}
}
