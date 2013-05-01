<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Cadastro de Produto</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro de um produto.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario" width="80%">
		<caption>Dados do gerais do Produto</caption>
		<tbody>
			<tr>
				<th class="obrigatorio">
					Nome
				</th>
				<td>
					<h:inputText size="80" value="#{produtoMBean.obj.nome}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Tipo de Produto
				</th>
				<td>
					<h:selectOneMenu value="#{produtoMBean.obj.tipoArmazenamento.id}">
						<f:selectItem itemLabel="--Selecione um Tipo--" itemValue="-1"/>
						<f:selectItems value="#{tipoArmazenamentoMBean.allCombo}"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Categoria:
				</th>
				<td>
					<h:selectOneMenu value="#{produtoMBean.obj.tipo.id}">
						<f:selectItem itemLabel="--Selecione uma Categoria--" itemValue="-1"/>
						<f:selectItems value="#{tipoProdutoMBean.allCombo}"/>
						<a4j:support event="onchange" reRender="subtipo" />
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					SubTipo:
				</th>
				<td>
					<h:selectOneMenu value="#{produtoMBean.obj.subTipo.id}" id="subtipo">
						<f:selectItem itemLabel="--Selecione um SubTipo--" itemValue="-1"/>
						<f:selectItems value="#{produtoMBean.subTiposAjax}"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Preço:
				</th>
				<td>
					<h:inputText size="80" value="#{produtoMBean.obj.preco}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Garantia
				</th>
				<td>
					<h:inputText size="80" value="#{produtoMBean.obj.warranty}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Tamanho
				</th>
				<td>
					<h:inputText size="80" value="#{produtoMBean.obj.size}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Peso
				</th>
				<td>
					<h:inputText size="80" value="#{produtoMBean.obj.weight}"/>
				</td>
			</tr>
			
			<tr>
				<th>
					Observações:
				</th>
				<td>
					<h:inputTextarea cols="80" rows="6" value="#{produtoMBean.obj.observacao}"/>
				</td>
			</tr>
			<tr>
				<th>
					Caracteristicas:
				</th>
				<td>
					<h:inputTextarea cols="80" rows="6" value="#{produtoMBean.obj.caracteristicas}"/>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cancelar" action="#{produtoMBean.cancelar}"/>
					<h:commandButton value="Avançar >>" action="#{produtoMBean.cadastroCamposExtras}"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>