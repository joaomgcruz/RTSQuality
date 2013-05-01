package br.com.ecommerce.arq.sbeans;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Session Bean relativo a opera?›es com produto sem autorizacao.
 * @author Mario Torres
 *
 */
@Service
public class ProdutoSBeanSemAutorizacao extends AbstractSBean implements IProdutoSBean{

	/**
	 * Método chamado para cadastro de CadastroDBs
	 * @throws DAOException 
	 */
	public void cadastrar(CadastroDB obj) throws DAOException{
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
