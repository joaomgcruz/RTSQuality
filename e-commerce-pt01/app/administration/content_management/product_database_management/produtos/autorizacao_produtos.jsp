<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>

<h2>Listagem de Produtos Pendentes à Autorização</h2>
<div class="descricaoOperacao">
	Permite a listagem dos produtos pendentes de autorização.
</div>
<h:form>
	<ecommerce:Mensagens />
	<c:if test="${not empty produtoMBean.produtosNaoAutorizados}">
	
		<div class="legenda">
			<h:graphicImage url="/img/correct.gif" width="16" height="16"/>: Confirmar
			<h:graphicImage url="/img/incorrect.gif" width="16" height="16"/>: Rejeitar
		</div>
		
		<table class="listagem">
			<caption>Produtos Encontrados (${fn:length(produtoMBean.produtosNaoAutorizados)})</caption>
			<thead>
				<tr>
					<td>Nome</td>
					<td>Tipo</td>
					<td>Sub-Tipo</td>
					<td>Data de Cadastro</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{produtoMBean.produtosNaoAutorizados}" var="produto" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							${produto.nome}
						</td>
						<td>
							${produto.tipo.denominacao}
						</td>
						<td>
							${produto.subTipo.denominacao}
						</td>
						<td>
							<h:outputText value="#{produto.dataCadastro}">
								<f:convertDateTime pattern="dd/MM/yyyy" />
							</h:outputText>	
						</td>
						<td>
							<h:commandLink action="#{produtoMBean.autorizarProduto}">
								<h:graphicImage url="/img/correct.gif" width="16" height="16"/>
								<f:param name="idProduto" value="#{produto.id}"/>
							</h:commandLink>
						</td>
						<td>
							<h:commandLink action="#{produtoMBean.cancelarProduto}">
								<h:graphicImage url="/img/incorrect.gif" width="16" height="16"/>
								<f:param name="idProduto" value="#{produto.id}"/>
							</h:commandLink>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<br></br>
		<h:commandLink value="Voltar" action="#{produtoMBean.cancelar}"/><br />
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>