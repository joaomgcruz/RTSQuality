<%@include file="/include/basic.jsp" %>
	
	
	<h2><img src="<%=request.getContextPath() %>/include/resources/imgs/ofertas.gif" alt="Ofertas especiais" /></h2>
	<c:if test="${not empty produtoMBean.threeProduto}">
		<table class="listagem">
			<thead>
				<tr>
					<td>Nome do produto</td>
					<td>Preço</td>
				</tr>
			</thead>
		<tbody>
			<c:forEach items="#{produtoMBean.threeProduto}" var="produto" varStatus="status">
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
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</c:if>