package br.com.ecommerce.arq.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import br.com.ecommerce.arq.dao.DAOFactory;
import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.Mensagem;
import br.com.ecommerce.arq.dominio.PersistDB;
import br.com.ecommerce.arq.dominio.TipoMensagem;
import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.mensagem.MensagemArq;
import br.com.ecommerce.arq.util.CalendarUtils;
import br.com.ecommerce.arq.util.ReflectionUtils;
import br.com.ecommerce.jsf.ConstantesNavegacao;

/**
 * Controler abstrato que deve ser herdado por todos os controladores.
 * @author Rodrigo Dutra de Oliveira
 */
public abstract class AbstractController implements Serializable{

	/** 
	 * Combo para meses 
	 **/
	private static List<SelectItem> meses = new ArrayList<SelectItem>();
	
	/** 
	 * Combo para anos 
	 **/
	private static List<SelectItem> anos = new ArrayList<SelectItem>();
	
	/** 
	 * Combo booleano 
	 **/
	private static List<SelectItem> simNao = new ArrayList<SelectItem>();
	
	/** 
	 * Combo para sexo 
	 **/
	private static List<SelectItem> mascFem = new ArrayList<SelectItem>();
	
	static{
		
		/** 
		 * Combo para meses 
		 **/
		meses.add(new SelectItem("1", "Janeiro"));
		meses.add(new SelectItem("2", "Fevereiro"));
		meses.add(new SelectItem("3", "Maro"));
		meses.add(new SelectItem("4", "Abril"));
		meses.add(new SelectItem("5", "Maio"));
		meses.add(new SelectItem("6", "Junho"));
		meses.add(new SelectItem("7", "Julho"));
		meses.add(new SelectItem("8", "Agosto"));
		meses.add(new SelectItem("9", "Setembro"));
		meses.add(new SelectItem("10", "Outubro"));
		meses.add(new SelectItem("11", "Novembro"));
		meses.add(new SelectItem("12", "Dezembro"));
		
		/** 
		 * Combo para anos.
		 **/
		for (int i = CalendarUtils.ANO_INICIO_PADRAO; i <= CalendarUtils.getAnoAtual() + 4; i++){
			anos.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
		}
		
		/** 
		 * Combo booleano 
		 **/
		simNao.add(new SelectItem(String.valueOf(true), "Sim"));
		simNao.add(new SelectItem(String.valueOf(false), "No"));
		
		/** 
		 * Combo para sexo 
		 **/
		mascFem.add(new SelectItem("F", "Feminino"));
		mascFem.add(new SelectItem("M", "Masculino"));
		
	}
	
	/**
	 * Coleção usada para trato com paginação
	 */
	@SuppressWarnings("unchecked")
	protected Collection colecao;
	
	public AbstractController(){
		
	}
	
	/**
	 * Adiciona uma mensagem para o usuário.
	 * @param mensagem
	 * @param tipoMensagem
	 */
	@SuppressWarnings("unchecked")
	private void addMensagem(String mensagem, TipoMensagem tipoMensagem){
		((List<Mensagem>) getCurrentRequest().getSession().getAttribute("mensagens")).add(new Mensagem(mensagem, tipoMensagem));
	}
	
	/**
	 * Mostra ao usuário uma mensagem padrão da arquitetura
	 * @param mensagem
	 * @param argumentos
	 */
	public void addMensagem(MensagemArq mensagem, Object... argumentos){
		addMensagem(mensagem.getMensagem(argumentos), mensagem.getTipoMensagem());
	}
	
	/**
	 * Método usado para verificar se existem mensagens na sessão para serem
	 * exibidas ao usuário.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean hasMensagens(){
		return ((List<Mensagem>) getCurrentRequest().getSession().getAttribute("mensagens")).size() > 0;
	}
	
	/**
	 * Adiciona uma mensagem de erro para o usuário.
	 * @param mensagem
	 */
	public void addMensagemErro(String mensagem){
		addMensagem(mensagem, TipoMensagem.ERRO);
	}
	
	/**
	 * Adiciona uma mensagem de aviso para o usuário.
	 * @param mensagem
	 */
	public void addMensagemWarning(String mensagem){
		addMensagem(mensagem, TipoMensagem.WARNING);
	}
	
	/**
	 * Adiciona uma mensagem de informação para o usuário.
	 * @param mensagem
	 */
	public void addMensagemInformation(String mensagem){
		addMensagem(mensagem, TipoMensagem.INFORMATION);
	}
	
	/**
	 * Mtodo para o padro de data
	 * @return Padro de dd/MM/yyyy
	 */
	public String getPadraoData() {
		return "dd/MM/yyyy";
	}

	/**
	 * Mtodo para o pado de números
	 * @return Padro #,##0.00
	 */
	public String getPadraoNumero() {
		return "#,##0.00";
	}
	
	/**
	 * Mtodo usado para se pegar um parâmetro da request.
	 * @param Nome do parametro
	 * @return Parametro
	 */
	public String getParameter(String param) {
		return getCurrentRequest().getParameter(param);
	}
	
	/**
	 * Método utilitário
	 * @return Ano atual
	 */
	public int getAnoAtual() {
		return CalendarUtils.getAnoAtual();
	}

	/**
	 * Metodo usando pelas jsf para iniciar um mbean
	 * Usando como em: <h:outputText value="#{bean.create}" />
	 * @return
	 * @throws DAOException 
	 */
	public String getCreate() throws DAOException {
		return "";
	}
	
	/**
	 * Método usado para se cancelar.
	 * @return
	 */
	public String cancelar(){
		return redirect(ConstantesNavegacao.PORTAL_ADMINISTRACAO);
	}
	
	/**
	 * Método usado para se voltar.
	 * @return
	 */
	public String voltar(){
		return redirect(ConstantesNavegacao.PORTAL_PRINCIPAL);
	}
	
	/**
	 * Retorna um managed-bean existente no container do JavaServer Faces.
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> T getMBean(String mbeanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (T) fc.getApplication().getVariableResolver().resolveVariable(fc, mbeanName);
	}
	
	/**
	 * Transforma um mapa em uma coleção de SelectItem. A key de uma Entry
	 * ser utilizado como value do SelectItem e o value de uma Entry 
	 * como label.
	 */
	public static Collection<SelectItem> toSelectItems(Map<?, ?> map) {
		Collection<SelectItem> itens = new ArrayList<SelectItem>();

		for (Iterator<?> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			itens.add(new SelectItem(entry.getKey().toString(), entry.getValue().toString()));
		}
		
		return itens;
	}
	
	public static List<SelectItem> toSelectItems(Collection<?> col){
		return toSelectItems(col, "id", "denominacao");
	}
	
	 /**
	  * Transforma uma coleção de objetos em uma lista de SelectItems
	  * @param col Coleo a ser transformada em SelectItems
	  * @param value Atributo que ser utilizado no value do option
	  * @param showText Valor que ser exibido ao usurio
	  */
	public static List<SelectItem> toSelectItems(Collection<?> col, String value, String showText) {

		ArrayList<OrderedSelectItem> itensOrdenaveis = new ArrayList<OrderedSelectItem>();
		ArrayList<SelectItem> itens = new ArrayList<SelectItem>();

		try {
			if (col != null) {
				for (Iterator<?> it = col.iterator(); it.hasNext();) {
					Object obj = it.next();
					Object id = ReflectionUtils.evalProperty(obj, value);
					Object text = ReflectionUtils.evalProperty(obj, showText);
					if (text == null) {
						text = "";
					}
					OrderedSelectItem item = new OrderedSelectItem(id.toString(), text
							.toString());
					itensOrdenaveis.add(item);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.sort(itensOrdenaveis);
		for ( OrderedSelectItem item : itensOrdenaveis ) {
			itens.add(item.toSelectItem());
		}

		return itens;

	}
	
	/**
	 * Retorna a instância do DAO
	 * @param daoClass Herda de GenericDAO
	 * @return A instncia do DAO
	 * @throws DAOException
	 */
	public <T extends GenericDAO> T getDAO(Class<T> daoClass)throws DAOException {
		return DAOFactory.getInstance().getDAO(daoClass, getCurrentRequest().getSession());
	}
	
	/**
	 * Retorna a implementação do genericDAO
	 * @return
	 * @throws DAOException
	 */
	public GenericDAO getGenericDAO() throws DAOException{
		return getDAO(GenericDAOImpl.class);
	}
	
	/**
	 * Transforma uma coleção de objetos em uma lista de SelectItems
	 * @param col Coleção a ser transformada em SelectItems
	 * @param showText Valor que ser exibido ao usurio
	 * @return Lista de SelectItems correspondente
	 */
	public static List<SelectItem> toSelectItems(Collection<?> col, String showText) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		
		try {
			if (col != null) {
				for (Object obj : col) {
					items.add(new SelectItem(obj, BeanUtils.getProperty(obj, showText)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}
	

	/**
	 * Redireciona o usurio para uma url concatenando
	 * o contexto da aplicao.
	 */
	public String redirect(String url) {
		String context = getContextPath();
		if (!url.startsWith(context))
			url = context + url;
		return redirectSemContexto(url);
	}
	
	/**
	 * Redireciona o usurio para uma url sem concatenar
	 * o contexto.
	 */
	public String redirectSemContexto(String url) {
		
		try {
			getCurrentResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}
	
	/**
	 * Realiza um forward para a jsp passada como parâmetro.
	 * @param url
	 * @return null
	 */
	public String forward(String url) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		UIViewRoot view = app.getViewHandler().createView(context, url);
		context.setViewRoot(view);
		context.renderResponse();

		// Retorna null para evitar o return null no action do Managed Bean
		return null;
	}
	
	/**
	 * Possibilita o acesso ao HttpServletRequest.
	 */
	public HttpServletRequest getCurrentRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	/**
	 * Retorna o contexto da aplicao WEB
	 */
	public String getContextPath() {
		return getCurrentRequest().getContextPath();
	}
	
	/**
	 * Possibilita o acesso ao HttpServletResponse.
	 */
	public HttpServletResponse getCurrentResponse() {
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
	/**
	 * Possibilita o acesso ao HttpSession.
	 */
	public HttpSession getCurrentSession() {
		return getCurrentRequest().getSession(true);
	}
	
	/**
	 * Acessa o external context do JavaServer Faces
	 */
	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public List<SelectItem> getMeses() {
		return meses;
	}

	public static List<SelectItem> getAnos() {
		return anos;
	}

	public List<SelectItem> getSimNao() {
		return simNao;
	}

	public List<SelectItem> getMascFem() {
		return mascFem;
	}
	
	/**
	 * Retorna a páginação associada ao usuário.
	 * @return
	 */
	public Paginacao getPaginacao(){
		return getMBean("paginacao");
	}
	
	/**
	 * Seta a página atual que se esta navegando.
	 * @param paginaAtual
	 */
	public void setPaginaAtual(int paginaAtual){
		getPaginacao().setPaginaAtual(paginaAtual);
	}
	
	/**
	 * Reseta o sistema de paginação.
	 */
	public void resetPaginacao(){
		getPaginacao().reset();
	}
	
	/**
	 * Verifica se o objeto passado é vazio.
	 * @param obj
	 * @return
	 */
	public boolean isEmpty(Object obj){
		if(obj == null)
			return true;
		
		if(obj instanceof String)
			if(((String) obj).trim().equals(""))
				return true;
			else
				return false;
		
		if(obj instanceof PersistDB)
			if(((PersistDB) obj).getId() <= 0)
				return true;
			else
				return false;
		
		if(obj instanceof Date)
			if(((Date) obj) == null)
				return true;
			else
				return false;
		
		if(obj instanceof Integer)
			if(((Integer) obj) == null || ((Integer) obj) == 0)
				return true;
			else
				return false;
		
		if(obj instanceof Long)
			if(((Long) obj) == null || ((Long) obj) == 0)
				return true;
			else
				return false;
		
		throw new UnsupportedOperationException("Este tipo ainda não é suportado.");
	}

}
