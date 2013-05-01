<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Relatório de Produtos Cadastrados</h2>
<div class="descricaoOperacao">
	{Melhorar}
</div>
<h:form>
	<table class="formulario">
		<caption></caption>
		<tbody>
			<tr>
				<th>
					Período de Cadastro:
				</th>
				<td>
					<rich:calendar /> a <rich:calendar />
				</td>
			</tr>
			<tr>
				<th>
					<h:selectBooleanCheckbox />
					Tipo:
				</th>
				<td>
					<h:selectOneMenu>
						<f:selectItem itemLabel="-- Selecione um Tipo --" itemValue="0"/>
						<f:selectItems/>
					</h:selectOneMenu>
				</td>
			</tr>
			<tr>
				<th class="radio">
					<h:selectBooleanCheckbox />
					Sub-Tipo
				</th>
				<td>
					<h:selectOneMenu>
						<f:selectItem itemLabel="--Selecione um Sub-Tipo--"/>
					</h:selectOneMenu>				
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Gerar Relatório"/>
					<h:commandButton value="Cancelar"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>