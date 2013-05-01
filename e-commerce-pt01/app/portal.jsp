<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h:form>
	<ecommerce:Mensagens />
	<h:commandLink value="Cadastro Categoria de Produto" action="#{tipoProdutoMBean.iniciarCadastro}"/><br />
	<h:commandLink value="Cadastro de Campo Extra de Produto" action="#{tipoCampoExtraProdutoMBean.iniciarCadastro}"/><br />
	<h:commandLink value="Cadastro de SubCategoria de Produto" action="#{subTipoProdutoMBean.iniciarCadastro}"/><br />
	<br />
	<h:commandLink value="Cadastro de Produto" action="#{produtoMBean.iniciarCadastro}"/><br />
		<h:commandLink value="Confirmação cadastro de Produto" action="#{produtoMBean.iniciarAutorizacao}"/><br />
	<h:commandLink value="Busca de Produto" action="#{pesquisaMBean.iniciarBusca}"/><br />
	<br />
	<h:commandLink value="Intervalo de Serviço" action="#{intervaloServicoMBean.iniciarCadastro}"/><br /> 
	<h:commandLink value="Planejamento de Serviço" action="#{planejamentoServicoMBean.iniciarCadastro}"/><br /> 
	<br />
	<h:commandLink value="Cadastro de Estoque" action="#{estoqueMBean.iniciarCadastro}"/><br />
	<h:commandLink value="Acompanhamento de Estoque" action="#{estoqueMBean.acompanhamentoEstoque}"/><br />
	<br />
	<h:commandLink value="Cadastro de Arquivos" action="#{fileRepositoryMBean.iniciarCadastroArquivo}"/><br />
	<br></br>
	<h:commandLink value="Cadastro de Mensagens" action="#{mensagemMBean.iniciarCadastro}"/>
	<br></br>	
	<br></br>
	<h:commandLink value="Voltar" action="#{registroMBean.voltar}"/>
</h:form> 
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>