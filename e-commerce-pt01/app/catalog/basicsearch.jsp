<%@include file="/include/basic.jsp" %>

<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Pesquisa Básica</caption>
		<tbody>
			<tr>
				<td>
					Produto:
				</td>
				<td>
					<h:inputText size="30" value="#{pesquisaMBean.obj.nome}" />
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Buscar" action="#{pesquisaMBean.buscaBasicaProdutos}"/>
					</td>
			</tr>
		</tfoot>
	</table>
	
	<br />
	<br />
	<c:if test="${not empty pesquisaMBean.produtosEncontrados}">
		<table class="listagem">
			<caption>Produtos Encontrados (${fn:length(pesquisaMBean.produtosEncontrados)})</caption>
			<thead>
				<tr>
					<td>Nome</td>
					<td>Tipo</td>
					<td>Sub-Tipo</td>
					<td>Data de Cadastro</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{pesquisaMBean.produtosEncontrados}" var="produto">
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
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</h:form>