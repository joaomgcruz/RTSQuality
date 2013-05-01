<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>


<h:form>
	<ecommerce:Mensagens />
	
	
	<%@include file="/menu.jsp"%>
	

	<c:if test="${not empty carrinhoMBean.produtos}">
		<table class="listagem">
			<thead>
				<tr>
					<th>
						Produto
					</th>
					<th>
						Valor
					</th>
					<th>
						Quantidade
					</th>
					<th>
						Remover do Carrinho
					</th>
				</tr>
			</thead>
			<c:set value="0" var="valorTotal"/>
			<c:set value="0" var="quantidadeTotal"/>
			<c:forEach items="#{carrinhoMBean.produtos}" var="produto" varStatus="status">
				<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td>
						<h:outputText value="#{produto.nome}">
						</h:outputText>
					</td>
					<td>
						<c:set value="${produto.preco + valorTotal}" var="valorTotal"/>
						<h:outputText value="#{produto.preco}">
							<f:convertNumber pattern="#,##0.00"/>
						</h:outputText>
					</td>
					<td align="center">
						<c:set value="${1 + quantidadeTotal}" var="quantidadeTotal"/>
						1
					</td>
					<td align="center">
					<h:commandLink action="#{carrinhoMBean.removerProduto}">
								<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>
								<f:param name="idProduto" value="#{produto.id}"/>
					</h:commandLink>
					</td>
				</tr>
			</c:forEach>
			<tfoot>
				<tr>
					<td>
						<strong>TOTAL:</strong>
					</td>
					<td>
					<strong>
						<h:outputText value="#{carrinhoMBean.valorTotal}">
							<f:convertNumber pattern="#,##0.00"/>
						</h:outputText>
						</strong>
					</td>
					<td align="center">
					<strong>
						${quantidadeTotal}
						</strong>
					</td>
				</tr>
			</tfoot>
		</table>
	<h:commandLink value="Checkout" action="#{checkoutMBean.iniciarCheckout}">
		<f:param name="valorTotal" value="#{carrinhoMBean.valorTotal}"/>
	</h:commandLink>
	</c:if>
	<br />
	<h:commandLink value="Voltar" action="#{carrinhoMBean.voltar}"/>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>