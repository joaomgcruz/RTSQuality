<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Sub-Categoria</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro, modificação e remorção de sub-tipos de produtos cadastrados.<br />
	Um sub-Tipo de produto deve estar ligado a um tipo específico de produto.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Dados do Sub-Tipo</caption>
		<tbody>
			<tr>
				<th class="obrigatorio">
					Denominação:
				</th>
				<td>
					<h:inputText size="70" value="#{subTipoProdutoMBean.obj.denominacao}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Descrição:
				</th>
				<td>
					<h:inputTextarea cols="80" rows="6" value="#{subTipoProdutoMBean.obj.descricao}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Tipo:
				</th>
				<td>
					<h:selectOneMenu value="#{subTipoProdutoMBean.obj.tipo.id}">
						<f:selectItem itemLabel="-- Selecione um Tipo --" itemValue="0"/>
						<f:selectItems value="#{tipoProdutoMBean.allCombo}"/>
					</h:selectOneMenu>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cadastrar" action="#{subTipoProdutoMBean.cadastrar}"/>
					<h:commandButton value="Cancelar" action="#{subTipoProdutoMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
	<br />
	
	<c:if test="${not empty subTipoProdutoMBean.allSubTiposProduto}">
		<div class="legenda">
			<h:graphicImage url="/img/lupa.gif" width="16" height="16"/>: Visualizar
			<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>: Remover
		</div>
		
		<table class="listagem">
			<caption>Sub-Tipos de Produtos Disponíveis</caption>
			<thead>
				<tr>
					<td>Denominação</td>
					<td>Descrição</td>
					<td>Tipo</td>
					<td>Data de Cadastro</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{subTipoProdutoMBean.allSubTiposProduto}" var="subTipo" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							<h:outputText value="#{subTipo.denominacao}"/>
						</td>
						<td>
							<h:outputText value="#{subTipo.descricao}"/>
						</td>
						<td>
							<h:outputText value="#{subTipo.tipo.denominacao}"/>
						</td>
						<td>
							<h:outputText value="#{subTipo.dataCadastro}">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:outputText>
						</td>
						<td>
							<h:commandLink action="#{subTipoProdutoMBean.visualizarDetalhadamente}">
								<h:graphicImage url="/img/lupa.gif" width="16" height="16"/>
								<f:param name="idSubTipoProduto" value="#{subTipo.id}"/>
							</h:commandLink>
							<h:commandLink action="#{subTipoProdutoMBean.remover}">
								<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>
								<f:param name="idSubTipoProduto" value="#{subTipo.id}"/>
							</h:commandLink>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>