package br.com.ecommerce.arq.aspects;

import javax.persistence.Column;

import org.aspectj.lang.annotation.Aspect;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.dominio.produto.Produto;
import br.com.ecommerce.arq.sbeans.ProdutoSBean;

public aspect ProdutoAut {
	
    @Column(name="autorizado",columnDefinition="autorizado") 
	private boolean Produto.autorizado;
	
	public void Produto.setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}

	public boolean Produto.isAutorizado() {
		return autorizado;
	}
	public void ProdutoSBean.autorizarProduto(int idProduto) throws DAOException {
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
	
}