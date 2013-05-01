<%@include file="/include/basic.jsp" %>
<f:view>
<h2>Relatório de Produtos Cadastrados</h2>
<table class="relatorioBorda">
	<thead>
		<tr>
			<td class="tamanhoVariavelTextual">
				Nome
			</td>
			<td class="tamanhoVariavelTextual">
				Tipo
			</td>
			<td class="tamanhoVariavelTextual">
				SubTipo
			</td>
			<td class="tamanhoFixo">
				Data Cadastro
			</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${produtos}" var="produto" varStatus="status">
			<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar'}">
				<td class="tamanhoVariavelTextual"></td>
				<td class="tamanhoVariavelTextual"></td>
				<td class="tamanhoVariavelTextual"></td>
				<td class="tamanhoFixo"></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</f:view>