<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Estoque de Produtos</h2>
<div class="descricaoOperacao">
	Esta operação permite a visualização do estoque.<br />
	Selecione o produto e busque pelo seu estoque.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Seleção do Produto</caption>
		<tr>
			<th>
				Produto:
			</th>
			<td>
				<h:inputText value="#{estoqueMBean.produto.nome}" id="produto" style="width: 430px;" />
 
                <rich:suggestionbox for="produto" height="100" width="430"  minChars="1" id="suggestion"
                       suggestionAction="#{estoqueMBean.autocompleteProduto}" var="_produto"
                       fetchValue="#{_produto.nome}">
                 
                      <h:column>
                    	<h:outputText value="#{_produto.nome}" />
                      </h:column>
                 
                    <a4j:support event="onselect">
                    	<f:setPropertyActionListener value="#{_produto.id}" target="#{estoqueMBean.produto.id}"  />
                    </a4j:support>
                </rich:suggestionbox>
			</td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Mostrar Estoque" action="#{estoqueMBean.selecionarProdutoAcompanhamento}"/>
					<h:commandButton value="Cancelar" action="#{estoqueMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
	<br />
	<br />
	
	<c:if test="${not empty estoqueMBean.estoque}">
		<table class="listagem">
			<thead>
				<tr>
					<th>
						Data Cadastro
					</th>
					<th>
						Quantidade
					</th>
					<th>
						Restantes
					</th>
				</tr>
			</thead>
			<c:set value="0" var="quantidadeTotal"/>
			<c:set value="0" var="restantesTotal"/>
			<c:forEach items="#{estoqueMBean.estoque}" var="estoque" varStatus="status">
				<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
					<td>
						<h:outputText value="#{estoque.dataCadastro}">
							<f:convertDateTime pattern="dd/MM/yyyy"/>
						</h:outputText>
					</td>
					<td>
						<c:set value="${estoque.quantidade + quantidadeTotal}" var="quantidadeTotal"/>
						${estoque.quantidade}
					</td>
					<td>
						<c:set value="${estoque.restantes + restantesTotal}" var="restantesTotal"/>
						${estoque.restantes}
					</td>
				</tr>
			</c:forEach>
			<tfoot>
				<tr>
					<td>
						TOTAL:
					</td>
					<td>
						${quantidadeTotal}
					</td>
					<td>
						${restantesTotal}
					</td>
				</tr>
			</tfoot>
		</table>
	</c:if>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>