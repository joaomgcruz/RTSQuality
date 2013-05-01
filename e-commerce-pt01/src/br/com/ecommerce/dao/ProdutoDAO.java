package br.com.ecommerce.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.Paginacao;
import br.com.ecommerce.dominio.Estoque;
import br.com.ecommerce.dominio.NavegacaoDTO;
import br.com.ecommerce.dominio.produto.Produto;
import br.com.ecommerce.dominio.produto.SubTipoProduto;
import br.com.ecommerce.dominio.produto.TipoProduto;

public class ProdutoDAO extends GenericDAOImpl implements GenericDAO{

	public ProdutoDAO(){
		this.daoName = ProdutoDAO.class.getName(); 
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> findAllAtivos() throws HibernateException, DAOException{
		//String hql = "select p from Produto as p where p.inativo = false and p.autorizado = false";

		String hql = "select p from Produto as p where p.inativo = false";

		return getSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> findGeral(String nomeLike, String observacaoLike, Integer idTipo, Integer idSubTipo, String caracteristicasLike) throws HibernateException, DAOException{
		
		StringBuffer buffer = new StringBuffer();
		
		//buffer.append("select p from Produto p where p.inativo = false and p.autorizado = true ");
		buffer.append("select p from Produto p where p.inativo = false ");
		
		if(nomeLike != null)
			buffer.append("and upper(p.nome) like :nomeLike ");
		if(observacaoLike != null)
			buffer.append("and upper(p.observacao) like :observacaoLike ");
		if(idTipo != null)
			buffer.append("and p.tipo.id = :idTipo ");
		if(idSubTipo != null)
			buffer.append("and p.subTipo.id = :idSubTipo ");
		if(caracteristicasLike != null)
			buffer.append("and p.caracteristicas = :caracteristicasLike ");
		
		buffer.append("order by p.nome, p.dataCadastro ");
		
		Query query = getSession().createQuery(buffer.toString());
		
		if(nomeLike != null)
			query.setString("nomeLike", "%" + nomeLike.toUpperCase() + "%");
		if(observacaoLike != null)
			query.setString("observacaoLike", "%" + observacaoLike.toUpperCase() + "%");
		if(idTipo != null)
			query.setInteger("idTipo", idTipo);
		if(idSubTipo != null)
			query.setInteger("idSubTipo", idSubTipo);
		if(caracteristicasLike != null)
			query.setString("observacaoLike", "%" + caracteristicasLike.toUpperCase() + "%");
		

		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> autoCompleteProduto(String nomeLike) throws DAOException {
		StringBuffer hql = new StringBuffer(); 
		List<Produto> produtos;
		
		hql.append("select p from Produto p where p.inativo = false ");
		
/*		if(ParametroHelper.getInstance().getBooleanParametro(ProductManagementParametros.NECESSARIO_AVAL_LIBERACAO_PRODUTO)){
			hql.append("and p.autorizado = true ");
		}
		*/
		hql.append("and upper(p.nome) like :nomeLike order by p.nome");
		
		Query query = getSession().createQuery(hql.toString());
		query.setString("nomeLike", "%" + nomeLike.toUpperCase().trim() + "%");
		
		produtos = query.list();
		
		/*for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto produto = (Produto) iterator.next();
			if (!produto.isAutorizado())
				iterator.remove();
			
		}*/
		
		return produtos;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Estoque> findEstoqueByProduto(int idProduto) throws HibernateException, DAOException{
		String hql = "select e from Estoque e where e.produto.id = :idProduto and e.inativo = false";
		
		Query query = getSession().createQuery(hql);
		query.setInteger("idProduto", idProduto);
		
		return query.list();
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	public List<NavegacaoDTO> getNavegacaoDTO() throws HibernateException, DAOException{
		String hql = "select st, st.tipo from SubTipoProduto as st where st.inativo = false and st.tipo.inativo = false " +
				"order by st.tipo.denominacao, st.denominacao asc";
		
		Query query = getSession().createQuery(hql);
		
		List<Object[]> resultado = query.list();
		List<NavegacaoDTO> retorno = new LinkedList<NavegacaoDTO>();
		
		String denominacaoTipoAnterior = ""; 
		NavegacaoDTO navegacaoAtual = null;
		
		for(Object[] obj : resultado){
			//Mudou o tipo
			if(!denominacaoTipoAnterior.equals(((TipoProduto) obj[1]).getDenominacao().trim())){
				if(navegacaoAtual != null)
					retorno.add(navegacaoAtual);
				navegacaoAtual = new NavegacaoDTO();
				navegacaoAtual.setTipoProduto((TipoProduto) obj[1]);
				navegacaoAtual.setSubTipos(new LinkedList<SubTipoProduto>());
			}
			
			navegacaoAtual.getSubTipos().add((SubTipoProduto) obj[0]);
			
			denominacaoTipoAnterior = ((TipoProduto) obj[1]).getDenominacao().trim();
		}
		
		if(navegacaoAtual != null)
			retorno.add(navegacaoAtual);
		
		return retorno;
	}
	
	/**
	 * Busca pelos produtos de um SubTipo
	 * @param idSubTipo
	 * @param paginacao
	 * @return
	 * @throws DAOException 
	 * @throws HibernateException 
	 */
	@SuppressWarnings("unchecked")
	public List<Produto> findProdutosBySubTipoPaginacao(int idSubTipo, Paginacao paginacao) throws HibernateException, DAOException{
		String hql = "select p from Produto as p where p.subTipo.id = :idSubTipo and p.inativo = false " +
				"order by p.nome";

		Query query = getSession().createQuery(hql);
		
		query.setFirstResult(paginacao.getPaginaAtual()*paginacao.getTamanhoPagina());
		query.setMaxResults(paginacao.getTamanhoPagina());
		query.setInteger("idSubTipo", idSubTipo);
		
		String hqlCount = "select count(p) from Produto as p where p.subTipo.id = :idSubTipo and p.inativo = false ";
		
		Query queryCount = getSession().createQuery(hqlCount);
		
		queryCount.setInteger("idSubTipo", idSubTipo);
		
		paginacao.setQuantidadeResultados((Long)queryCount.uniqueResult());
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> getThreeProducts() throws DAOException{
		String hql = "select p from Produto p where p.inativo = false ";
		
		Query query = getSession().createQuery(hql);
		
		query.setMaxResults(3);
		
		return query.list();
	}
}
