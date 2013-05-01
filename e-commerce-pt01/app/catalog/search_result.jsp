<%@include file="/include/basic.jsp" %>

<h:form>
    <ecommerce:Mensagens />
<c:if test="${not empty pesquisaMBean.produtosEncontrados}">
        <table class="listagem">
            <caption>Produtos Encontrados (${fn:length(pesquisaMBean.produtosEncontrados)})</caption>
            <thead>
                <tr>
                    <td>Nome</td>
                    <td>Categoria</td>
                    <td>Sub-Categoria</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="#{pesquisaMBean.produtosEncontrados}" var="produto">
                    <tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
                        <td>
                        <h:commandLink action="#{navegacaoMBean.detalhesProduto}">
                            ${produto.nome}
								<f:param name="idProduto" value="#{produto.id}"/>
						</h:commandLink>

                        </td>
                        <td>
                            ${produto.tipo.denominacao}
                        </td>
                        <td>
                            ${produto.subTipo.denominacao}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</h:form>