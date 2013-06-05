package br.com.ecommerce.jsf;

import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.AbstractController;
import br.com.ecommerce.arq.jsf.Paginacao;
import br.com.ecommerce.dao.ProdutoDAO;
import br.com.ecommerce.dominio.NavegacaoDTO;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Controla navega��o.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Component()
@Scope("session")
public class NavegacaoMBean extends AbstractController{

	private static final String NAVEGACAO_PRODUTOS = "/exibir_produtos.jsp";
	
	private static final String DETALHES_PRODUTO = "/detalhes_produto.jsp";
	
	private int idSubTipo;
	
	private int idProduto;
	
	private Produto produto;
	
	private int paginaAtual;
	
	private Paginacao paginacao;
	
	public NavegacaoMBean() {
	
	}

	public String goPortalAdministrativo(){
		return forward(ConstantesNavegacao.PORTAL_ADMINISTRACAO);
	}
	
	/**
	 * Obtem um dto com os tipos de produtos.
	 * @return
	 * @throws DAOException 
	 * @throws HibernateException 
	 */
	public List<NavegacaoDTO> getNavegacaoDTO() throws HibernateException, DAOException{
		return getDAO(ProdutoDAO.class).getNavegacaoDTO();
	}
	
	public String navegarSubTipo(){
		idSubTipo = Integer.parseInt(getParameter("idSubTipo"));
		//Usado para na primeira chamada popular os dados
		paginaAtual = -1;
		
		setPaginaAtual(0);
		
		paginacao = getPaginacao();
		return forward(NAVEGACAO_PRODUTOS);
	}
	
	public String detalhesProduto() throws NumberFormatException, DAOException{
		idProduto = Integer.parseInt(getParameter("idProduto"));
		produto = getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idProduto")), Produto.class);
		System.out.print("");
		return forward(DETALHES_PRODUTO);
	}
	
	public String back(){
		return voltar();
	}
	
	public Collection<Produto> getColecao() throws HibernateException, DAOException{
		//Mudan�a de p�gina
		if(paginacao.getPaginaAtual() != paginaAtual){
			paginaAtual = getPaginacao().getPaginaAtual();
			
			colecao = getDAO(ProdutoDAO.class).findProdutosBySubTipoPaginacao(idSubTipo, getPaginacao());
		}
		
		return colecao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
}
