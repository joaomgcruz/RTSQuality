<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Listagem de Produtos</h2>
<div class="descricaoOperacao">
	Permite a listagem dos produtos cadastrados.
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
					<h:commandButton value="Cancelar" action="#{pesquisaMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>