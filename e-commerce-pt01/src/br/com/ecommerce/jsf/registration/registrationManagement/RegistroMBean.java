package br.com.ecommerce.jsf.registration.registrationManagement;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import org.ajax4jsf.model.KeepAlive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.mensagem.MensagensArquitetura;
import br.com.ecommerce.arq.sbeans.RegistroSBean;
import br.com.ecommerce.arq.util.CalendarUtils;
import br.com.ecommerce.arq.util.SecurityUtils;
import br.com.ecommerce.arq.util.StringUtils;
import br.com.ecommerce.dao.UsuarioDAO;
import br.com.ecommerce.dominio.usuario.Cidade;
import br.com.ecommerce.dominio.usuario.CreditCard;
import br.com.ecommerce.dominio.usuario.Demografico;
import br.com.ecommerce.dominio.usuario.Endereco;
import br.com.ecommerce.dominio.usuario.Estado;
import br.com.ecommerce.dominio.usuario.Pessoa;
import br.com.ecommerce.dominio.usuario.SecurityCard;
import br.com.ecommerce.dominio.usuario.Usuario;
import br.com.ecommerce.jsf.ConstantesNavegacao;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Performance;
import br.ufrn.ppgsc.scenario.analyzer.annotations.Security;
import br.ufrn.ppgsc.scenario.analyzer.annotations.arq.Scenario;
import br.ufrn.ppgsc.scenario.analyzer.d.data.DataUtil;
import br.ufrn.ppgsc.scenario.analyzer.d.data.ExecutionPaths;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeCallGraph;


/**
 * Controlador para tratar de operações com o registro dos clientes.
 * @author Thiago Viana Dantas
 *
 */
@Component
@Scope("session")
@KeepAlive
public class RegistroMBean extends CadastroAbstractController<Usuario>{

	
	private static final String INFORMACOES_LOGIN= "/registration/registration_information/cadastro/registro_login.jsf";

	private static final String INFORMACOES_GERAIS= "/registration/registration_information/cadastro/registro_informacoes_gerais.jsf";

	private static final String INFORMACOES_CARTAO= "/registration/registration_information/cadastro/registro_cartao.jsf";
	
	private static final String LOJA = ConstantesNavegacao.PORTAL_PRINCIPAL;
		
	private Endereco endereco;
	
	private CreditCard creditCard;
	
	private int idade;

	@Autowired
	private RegistroSBean registroSBean;
	/**
	 * Repetição da senha digitado pelo usuário.
	 */
	private String senha;
	
	public RegistroMBean(){
		reset();
	}
	
	private void reset(){
		obj = new Usuario();
		obj.setPessoa(new Pessoa());
		obj.setSecurityCard(new SecurityCard());
		endereco = new Endereco();
		creditCard = new CreditCard();
		endereco.setCidade(new Cidade());
		endereco.getCidade().setEstado(new Estado());
		obj.getPessoa().setDemografico(new Demografico());
		obj.getPessoa().setEndereco(new ArrayList<Endereco>());
		obj.getPessoa().setCreditCards(new ArrayList<CreditCard>());
	}
	
	/**
	 * Permite o cadastro de um novo usuario.
	 * 
	 * @return
	 */
	public String iniciarCadastro(){
		reset();
		return forward(INFORMACOES_LOGIN);
	}

	@Scenario(name="scenario_login_registration")
//	@Performance(name="performance_login_registration", limit=140)
	@Security(name="security_login_registration")
	public String cadastroInformacoesLogin() throws DAOException{
		obj.getSecurityCard().setLogin(obj.getSecurityCard().getLogin().trim());
		
		if(isEmpty(obj.getSecurityCard().getLogin()) || obj.getSecurityCard().getLogin().equals(""))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Login");
		
		if(obj.getSecurityCard().getLogin() != null && obj.getSecurityCard().getLogin().indexOf(" ") != -1)
			addMensagemErro("Não é permitido espaços no login.");
		
		if(getDAO(UsuarioDAO.class).findQuantUsuariosByLogin(obj.getSecurityCard().getLogin()) > 0){
			addMensagemErro("Já existe o login selecionado");
		}

		if(isEmpty(obj.getSecurityCard().getSenha()) || obj.getSecurityCard().getSenha().equals(""))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Senha");
		
		if(obj.getSecurityCard().getSenha() != null)
			obj.getSecurityCard().setSenha(obj.getSecurityCard().getSenha().trim());
		
		senha = senha.trim();

		if(senha == null || senha.equals(""))
			addMensagemErro("É necessário repetir a senha.");
		
		if(senha != null && obj.getSecurityCard().getSenha() != null && !senha.equals(obj.getSecurityCard().getSenha()))
			addMensagemErro("As senhas não conferem.");
		
		if(hasMensagens())
			return null;
	
		return forward(INFORMACOES_GERAIS);
		
	}

	@Scenario(name="scenario_personal_registration")
//	@Performance(name="performance_personal_registration", limit=140)
	@Security(name="security_personal_registration")
	public String cadastroInformacoesGerais(){
		if(isEmpty(obj.getPessoa().getNome()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Nome");
		
		if(isEmpty(endereco.getEndereco()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Endereço");
		
		if(isEmpty(endereco.getNumeroCasa()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Número da casa");
		
		if(isEmpty(endereco.getBairro()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Bairro");
		
		if(isEmpty(endereco.getCidade().getDenominacao()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Cidade");
		
		if(isEmpty(endereco.getCidade().getEstado().getDenominacao()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Estado");
		
		if(isEmpty(endereco.getCEP()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "CEP");
		
		if(isEmpty(obj.getPessoa().getDataNascimento()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Data de nascimento");
		else if (!validarData(obj.getPessoa().getDataNascimento()))
			addMensagemErro("A data de nascimento informada não é válida");
		else {
			idade = CalendarUtils.calcularIdade(obj.getPessoa().getDataNascimento());
//			obj.getPessoa().getDemografico().setIdade(idade);
		}
		
		if(!StringUtils.validaEmail(obj.getPessoa().getEmail()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "e-mail");
		
		if(isEmpty(obj.getPessoa().getCPF()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "CPF");
		
		if(isEmpty(obj.getPessoa().getRG()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "RG");
		
		if(hasMensagens())
			return null;
		
		return forward(INFORMACOES_CARTAO);
	}
	
	@Scenario(name="scenario_credit_card_registration")
//	@Performance(name="performance_credit_card_registration", limit=140)
	@Security(name="security_credit_card_registration")
	public String iniciarCadastroCartao() throws DAOException, NoSuchAlgorithmException{
		if(isEmpty(creditCard.getNomeTitular()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Nome do titular");
		
		if(isEmpty(creditCard.getNumeroCartao()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Número do cartão");
		
		if(isEmpty(creditCard.getDataVencimento()))
			addMensagem(MensagensArquitetura.CAMPO_OBRIGATORIO_NAO_INFORMADO, "Data de vencimento");
		else if (validarData(creditCard.getDataVencimento()))
			addMensagemErro("A data de vencimento não é válida");

		if(hasMensagens())
			return null;
		
		obj.getSecurityCard().setSenha(SecurityUtils.toMD5(obj.getSecurityCard().getSenha()));
		creditCard.setProprietario(obj.getPessoa());
		endereco.setPessoa(obj.getPessoa());
		obj.getPessoa().getCreditCards().add(creditCard);
		obj.getPessoa().getEndereco().add(endereco);
		obj.getPessoa().getDemografico().setCaracteristica(obj.getPessoa());
		registroSBean.cadastrar(obj);
		addMensagem(MensagensArquitetura.OPERACAO_REALIZADA_COM_SUCESSO,
		"Cadastro de Usuário");
		return forward(LOJA);
		
	}
	
	/**
	 * Retorne true caso a data fornecida seja antes da data atual e false caso contrário.
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean validarData(Date date){
		Date data = new Date();
		boolean valida = false;
		int ano = data.getYear() - date.getYear();
		if (ano == 0){
			int mes = data.getMonth() - date.getMonth();
			if (mes ==0){
				int dia = data.getDate() - date.getDate();
				if (dia >= 0){
					valida = true;
				}
			} else if (mes > 0){
				valida = true;
			}
		} else if (ano > 0)
			valida = true;
		return valida;
	}
 
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}
	
	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
}
