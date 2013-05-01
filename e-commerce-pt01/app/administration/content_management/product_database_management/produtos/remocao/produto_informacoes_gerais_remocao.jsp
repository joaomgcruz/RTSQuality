<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Dados do Produto</h2>
<div class="descricaoOperacao">
	Verifique os dados abaixo do produto a ser removido.{Melhorar}
</div>
<h:form>
	<table class="visualizacao">
		<caption>Dados do Produto</caption>
		<tbody>
			<tr>
				<th>
					Nome:
				</th>
				<td>
					<h:outputText />
				</td>
			</tr>
			<tr>
				<th>
					Tipo:
				</th>
				<td>
					<h:outputText />
				</td>
			</tr>
			<tr>
				<th>
					SubTipo:
				</th>
				<td>
					<h:outputText />
				</td>
			</tr>
			<tr>
				<th>
					Observações:
				</th>
				<td>
					<h:outputText />
				</td>
			</tr>
			<tr>
				<th>
					Características:
				</th>
				<td>
					<h:outputText />
				</td>
			</tr>
			<c:forEach items="${CamposExtras}" var="campoExtra">
				<tr>
					<th>
						${campoExtra.tipo.denominacao}
					</th>
					<td>
						${campoExtra.conteudo}
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="<< Voltar"/>
					<h:commandButton value="Confirmar"/>
					<h:commandButton value="Cancelar"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>