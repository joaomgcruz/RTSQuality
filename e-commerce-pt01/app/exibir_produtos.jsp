<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>

<h:form>
	<ecommerce:Mensagens />		
		
		<%@include file="/menu.jsp"%>

		<c:if test="${not empty navegacaoMBean.colecao}">
		<table class="listagem">
			
			<div class="legenda">
				<h:graphicImage url="/img/detalhes.gif" width="25" height="25"/>: Detalhes do produto
				<h:graphicImage url="/img/carrinho.gif" width="25" height="25"/>: Adicionar ao carrinho
			</div>
			<caption>Produtos Disponíveis</caption>
			<thead>
				<tr>
					<td>Produto</td>
					<td>Preço</td>
					<td>Incluir no Carrinho</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{navegacaoMBean.colecao}" var="produto" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							<h:commandLink action="#{navegacaoMBean.detalhesProduto}">
								${produto.nome}
								<f:param name="idProduto" value="#{produto.id}"/>
							</h:commandLink>
						</td>
						<td align="center">
						<h:outputText value="#{produto.preco}">
							<f:convertNumber pattern="#,##0.00"/>
						</h:outputText>
						</td>
						<td align="center">
							<h:commandLink action="#{carrinhoMBean.incluirItemCarrinho}">
								<h:graphicImage url="/img/carrinho.gif" width="25" height="25"/>
								<f:param name="idProduto" value="#{produto.id}"/>
							</h:commandLink>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		</c:if>
		<c:if test="${empty navegacaoMBean.colecao}">
			Nenhum produto encontrado.
		</c:if>
		<div align="center">
		<h:commandLink value="Voltar" action="#{navegacaoMBean.voltar}" />
		</div>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>