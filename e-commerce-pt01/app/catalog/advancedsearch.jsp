<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Pesquisa Avançada</h2>
<div class="descricaoOperacao">
	Com esta operação, você pode adicionar mais elementos a sua busca.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Dados do Busca</caption>
		<tbody>
			<tr>
				<td class="radio">
					<h:selectBooleanCheckbox value="#{pesquisaMBean.byNome}" title="Clique aqui para ativar ou desativar."/>
					Nome:
				</td>
				<td>
					<h:inputText size="70" value="#{pesquisaMBean.obj.nome}" />
				</td>
			</tr>
			<tr>
				<td class="radio">
					<h:selectBooleanCheckbox value="#{pesquisaMBean.byObservacao}" title="Clique aqui para ativar ou desativar."/>
					Observação:
				</td>
				<td>
					<h:inputTextarea cols="80" rows="6" value="#{pesquisaMBean.obj.observacao}"/>
				</td>
			</tr>
			<tr>
				<td class="radio">
					<h:selectBooleanCheckbox value="#{pesquisaMBean.byTipo}" title="Clique aqui para ativar ou desativar."/>
					Tipo:
				</td>
				<td>
					<h:selectOneMenu value="#{pesquisaMBean.obj.tipo.id}">
						<f:selectItem itemLabel="-- Selecione um Tipo --" itemValue="0"/>
						<f:selectItems value="#{tipoProdutoMBean.allCombo}"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<td class="radio">
					<h:selectBooleanCheckbox value="#{pesquisaMBean.bySubTipo}" title="Clique aqui para ativar ou desativar."/>
					Sub-Tipo:
				</td>
				<td>
					<h:selectOneMenu value="#{pesquisaMBean.obj.subTipo.id}">
						<f:selectItem itemLabel="--Selecione um Sub-Tipo--" itemValue="0"/>
						<f:selectItems value="#{subTipoProdutoMBean.allCombo}"/>
					</h:selectOneMenu>				
				</td>
			</tr>
			<tr>
				<td class="radio">
					<h:selectBooleanCheckbox value="#{pesquisaMBean.byCaracteristicas}" title="Clique aqui para ativar ou desativar."/>
					Caracteristícas:
				</td>
				<td>
					<h:inputTextarea cols="80" rows="6" value="#{pesquisaMBean.obj.caracteristicas}"/>
				</td>
			</tr>
			<tr>
				<td class="radio" colspan="2">
					<h:selectBooleanCheckbox value="#{pesquisaMBean.byAll}" />
					Buscar todos
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Buscar" action="#{pesquisaMBean.buscarProdutos}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
	<br />
	<br />
	<!--  
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
	-->
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>