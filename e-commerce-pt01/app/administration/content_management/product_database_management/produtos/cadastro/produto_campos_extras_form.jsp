<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Cadastro de Produto >> Cadastro de Campo Extra</h2>
<div class="descricaoOperacao"> 
	Esta operação permite o cadastro de campos extras de produto.<br />
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario" width="90%">
		<caption>Campos Extras</caption>
		<tbody>
			<tr>
				<th class="obrigatorio">
					Tipo de Campo Extra:
				</th> 
				<td>
					<h:selectOneMenu value="#{produtoMBean.campoNovo.tipo.id}">
						<f:selectItem itemLabel="--Selecione um Tipo--" itemValue="-1"/>
						<f:selectItems value="#{tipoCampoExtraProdutoMBean.allCombo}"/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<th>
					Conteúdo:
				</th>
				<td>
					<h:inputTextarea cols="80" rows="6" value="#{produtoMBean.campoNovo.conteudo}"/>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Adicionar" action="#{produtoMBean.adicionarCampoExtra}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
		<br />
	
	<c:if test="${not empty produtoMBean.obj.camposExtras}">
	
		<table class="listagem">
			<caption>Campos Extras</caption>
			<thead>
				<tr>
					<td>Tipo de Campo Extra</td>
					<td>Conteudo</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{produtoMBean.obj.camposExtras}" var="campoExtra" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							<h:outputText value="#{campoExtra.tipo.denominacao}"/>
						</td>
						<td>
							<h:outputText value="#{campoExtra.conteudo}"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br />
	</c:if>
	
	<table class="formulario" width="90%">
		<tfoot>
			<tr>
				<td>
					<h:commandButton value="<< Voltar"/>
					<h:commandButton value="Cancelar" action="#{produtoMBean.cancelar}"/>
					<h:commandButton value="Cadastrar" action="#{produtoMBean.finalizarCadastroProduto}"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>