package br.com.ecommerce.jsf.administration.fulfilment;

import java.text.ParseException;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.com.ecommerce.arq.sbeans.SBeanInativacao;
import br.com.ecommerce.arq.util.CalendarUtils;
import br.com.ecommerce.dao.ProdutoDAO;
import br.com.ecommerce.dominio.DiaSemana;
import br.com.ecommerce.dominio.IntervaloServico;
import br.com.ecommerce.dominio.produto.Produto;
import br.com.ecommerce.jsf.ConstantesNavegacao;

/**
 * Controlador responsável por operações relacionadas a agendamento de serviços.
 * Trabalha em cima do cadastro, remoção, listagem.
 * Oferece métodos para suggestion-box, e listagem de items.
 * 
 * @author Mario Torres
 *
 */
@Component
@Scope("session")
public class IntervaloServicoMBean extends CadastroAbstractController<IntervaloServico>{

	/**
	 * Página reponsável pelo crud do serviço.
	 */
	private static final String CRUD_FORM = "/administration/fulfilment/services/intervalo.jsf";
	
	/**
	 * Página reponsável pela visualização detalhada do tipo de produto.
	 */
	private static final String VISUALIZAR_DETALHADAMENTE = "";
	
	/**
	 * Intervalo que esta sendo trabalhado atualmente.
	 */
	private IntervaloServico intervalo;
	
	/**
	 * Intervalo que será visualizado.
	 */
	private IntervaloServico intervaloVisualizado;
	
	private String horaInicial;
	
	private String horaFinal;
	

	/**
	 * Processador Cadastro.
	 */
	@Autowired
	private SBeanCadastro sBeanCadastro;
	
	/**
	 * Processador responsável pela inativação de PersistDBs
	 */
	@Autowired
	private SBeanInativacao sBeanInativacao;
	
	public IntervaloServicoMBean(){
		reset();
	}
	
	/**
	 * Reinicia as variáveis do mbean.
	 */
	private void reset(){
		obj = new IntervaloServico();
		obj.setDiaSemana(new DiaSemana());
		obj.setProduto(new Produto());
		
//		intervalo = new IntervaloServico();
	}
	
	/**
	 * Método chamado para se enviar para a página de crud.
	 * 
	 * @return forward(CRUD_FORM)
	 */
	public String iniciarCadastro(){
		reset();
		return forward(CRUD_FORM);
	}
	
	/**
	 * Método chamado para validar e cadastrar um intervalo de serviço.
	 * 
	 * @return null
	 * @throws DAOException 
	 * @throws ParseException 
	 */
	public String cadastrarNovoIntervalo() throws DAOException, ParseException{
		if(isEmpty(obj.getDiaSemana()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Dia da Semana");
		if(isEmpty(horaInicial))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Hora Inicial");
		if(isEmpty(horaFinal))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Hora Final");
		if(isEmpty(obj.getProduto()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Produto");


		obj.setHorainicio(CalendarUtils.convertStringToTime(horaInicial));
		obj.setHorafim(CalendarUtils.convertStringToTime(horaFinal));
		
		if(hasMensagens())
			return null;

		sBeanCadastro.cadastrar(obj);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Cadastro de Intervalo");
		
		reset();
		return cancelar();
	}
	
	/**
	 * Método usado para se cancelar.
	 * @return
	 */
	public String cancelar(){
		return redirect(ConstantesNavegacao.PORTAL_ADMINISTRACAO);
	}
	
	/**
	 * Permite a visualização de forma detalhada de um determinado serviço.
	 * 
	 * @return null
	 * @throws DAOException 
	 * @throws NumberFormatException 
	 */
	public String visualizarDetalhadamenteIntervalo() throws NumberFormatException, DAOException{
		IntervaloServico intervaloServico = getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idIntervaloServico")), IntervaloServico.class);
		
		if(intervaloServico.isInativo())
			addMensagem(MensagensArquitetura.ELEMENTO_NAO_DISPONIVEL_NO_BANCO, "Intervalo");
		
		if(hasMensagens())
			return null;
		
		return forward(VISUALIZAR_DETALHADAMENTE);
	}
	
	/**
	 * Permite a remoção de um determinado Intervalo.
	 * 
	 * @return
	 * @throws DAOException 
	 * @throws NumberFormatException 
	 */
	public String removerIntervalo() throws NumberFormatException, DAOException{
		IntervaloServico intervaloServico= getGenericDAO().findByPrimaryKey(Integer.parseInt(getParameter("idIntervaloServico")), IntervaloServico.class);
		
		if(intervaloServico.isInativo())
			addMensagem(MensagensArquitetura.SOLICITACAO_JA_PROCESSADA, "Remoção do Intervalo");
		
		if(hasMensagens())
			return null;
		
		sBeanInativacao.inativar(intervaloServico);
		
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO, "Remoção do Intervalo");
		
		return redirect(CRUD_FORM);
	}
	
	/**
	 * Método usado para se buscar por todos os dias de semana.
	 * @return os tipos ativos encontrados.
	 * 
	 * @throws DAOException 
	 */
	public List<SelectItem> getAllCombo() throws DAOException{
		return toSelectItems(getGenericDAO().findAll(DiaSemana.class), "id", "denominacao");
	}
	
	public List<Produto> autocompleteProduto(Object nome) throws HibernateException, DAOException{
		String nomeLike = (String) nome;
	       
        ProdutoDAO dao = getDAO(ProdutoDAO.class);
       
        return dao.autoCompleteProduto(nomeLike);
	}
	/**
	 * Busca todos os intervalos ativos.
	 * 
	 * @return
	 * @throws DAOException 
	 */
	public List<IntervaloServico> getAllIntervaloServico() throws DAOException{
		return (List<IntervaloServico>) getGenericDAO().findAllAtivos(IntervaloServico.class);
	}

	public IntervaloServico getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(IntervaloServico intervalo) {
		this.intervalo = intervalo;
	}

	public IntervaloServico getIntervaloVisualizado() {
		return intervaloVisualizado;
	}

	public void setIntervaloVisualizado(IntervaloServico intervaloVisualizado) {
		this.intervaloVisualizado = intervaloVisualizado;
	}
	
	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
}
