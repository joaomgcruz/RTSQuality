<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Cadastro de mensagem da Home Page</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro de uma mensagem para ser mostrada na Home Page.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Informe a mensagem desejada</caption>
		<tbody>
			<tr>
				<th class="obrigatorio">
					Mensagem:
				</th>
				<td>
					<h:inputText size="110" value="#{mensagemMBean.obj.denominacao}" />
				</td>
			</tr>
			<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cancelar" action="#{mensagemMBean.cancelar}"/>
					<h:commandButton value="Cadastrar" action="#{mensagemMBean.cadastrar}"/>
				</td>
			</tr>
			</tfoot>
		</tbody>
	</table>
	<c:if test="${not empty mensagemMBean.allMensagem}">
		<div class="legenda">
			<h:graphicImage url="/img/correct.gif" width="16" height="16"/>: Ativar
			<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>: Remover
		</div>
		
		<table class="listagem">
			<caption>Mensagens Disponíveis</caption>
			<thead>
				<tr>
					<td>Mensagem</td>
					<td>Data de Cadastro</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{mensagemMBean.allMensagem}" var="mensagem" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							${mensagem.denominacao}
						</td>
						<td>
							<h:outputText value="#{mensagem.dataCadastro}">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:outputText>
						</td>
						<td>
							<h:commandLink action="#{mensagemMBean.ativarMensagem}">
								<h:graphicImage url="/img/correct.gif" width="16" height="16"/>
								<f:param name="idMensagem" value="#{mensagem.id}"/>
							</h:commandLink>
							<h:commandLink action="#{mensagemMBean.removerMensagem}">
								<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>
								<f:param name="idMensagem" value="#{mensagem.id}"/>
							</h:commandLink>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>