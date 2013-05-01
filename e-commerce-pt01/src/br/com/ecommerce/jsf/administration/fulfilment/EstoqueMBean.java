package br.com.ecommerce.jsf.administration.fulfilment;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.com.ecommerce.dao.ProdutoDAO;
import br.com.ecommerce.dominio.Estoque;
import br.com.ecommerce.dominio.produto.Produto;

@Component
@Scope("session")
public class EstoqueMBean extends CadastroAbstractController<Estoque>{

	private static final String FORM_CADASTRO = "/administration/fulfilment/Estoque/cadastro_estoque_form.jsp";
	private static final String ACOMPANHAMENTO_ESTOQUE = "/administration/fulfilment/Estoque/acompanhamento_estoque.jsp";
	
	private Produto produto;
	
	@Autowired
	private SBeanCadastro sBeanCadastro;
	
	private List<Estoque> estoque;
	
	public EstoqueMBean(){
		reset();
	}
	
	private void reset(){
		setProduto(new Produto());
		obj = new Estoque();
		setEstoque(new ArrayList<Estoque>());
	}
	
	public String iniciarCadastro(){
		reset();
		return forward(FORM_CADASTRO);
	}
	
	public String cadastrarEstoque() throws DAOException{
		if(produto.getId() == 0){
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Produto");
		}
		
		if(obj.getQuantidade() <= 0){
			addMensagemErro("É necessário informar uma valor válido para a quantidade do produto");
		}
		
		if(hasMensagens())
			return null;
		
		obj.setProduto(getGenericDAO().findByPrimaryKey(produto.getId(), Produto.class));
		obj.setRestantes(obj.getQuantidade());
		sBeanCadastro.cadastrar(obj);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Cadastro de Estoque.");
		
		return cancelar();
	}
	
	public String acompanhamentoEstoque(){
		reset();
		return forward(ACOMPANHAMENTO_ESTOQUE);
	}
	
	public String selecionarProdutoAcompanhamento() throws DAOException{
		
		if(produto.getId() == 0)
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Produto");
		
		if(hasMensagens())
			return null;
		
		estoque = getDAO(ProdutoDAO.class).findEstoqueByProduto(produto.getId());
		
		return null;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}
	
	public List<Produto> autocompleteProduto(Object nome) throws HibernateException, DAOException{
		String nomeLike = (String) nome;
	       
        ProdutoDAO dao = getDAO(ProdutoDAO.class);
       
        return dao.autoCompleteProduto(nomeLike);
	}

	public void setEstoque(List<Estoque> estoque) {
		this.estoque = estoque;
	}

	public List<Estoque> getEstoque() {
		return estoque;
	}
}
