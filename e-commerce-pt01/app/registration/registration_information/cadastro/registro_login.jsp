<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>


<h:form>
	<ecommerce:Mensagens />
	
	
	<%@include file="/menu.jsp"%>
	

	<table class="formulario">
		<caption>Informe o login desejado</caption>
		<tbody>
			<tr>
				<th class="obrigatorio">
					Login:
				</th>
				<td>
					<h:inputText size="30" value="#{registroMBean.obj.securityCard.login}" />
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Senha:
				</th>
				<td>
					<h:inputSecret size="30" value="#{registroMBean.obj.securityCard.senha}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Confirme a Senha:
				</th>
				<td>
					<h:inputSecret size="30" value="#{registroMBean.senha}"/>
				</td>
			</tr>
			<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cancelar" action="#{registroMBean.voltar}"/>
					<h:commandButton value="Avançar >>" action="#{registroMBean.cadastroInformacoesLogin}"/>
				</td>
			</tr>
		</tfoot>
	</table>

</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>