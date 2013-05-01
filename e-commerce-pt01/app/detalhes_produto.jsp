<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Detalhes do produto</h2>
<h:form>
	<ecommerce:Mensagens />		
		<table class="listagem">
			<tbody align="left">
			<tr>
				<td>
					Nome: ${navegacaoMBean.produto.nome}
				</td>
			</tr>
			<tr>
				<td>
					Tipo de armazenamento: ${navegacaoMBean.produto.tipoArmazenamento.descricao}
				</td>
			</tr>
			<tr>
				<td>
					Tipo: ${navegacaoMBean.produto.tipo.denominacao}
				</td>
			</tr>
			<tr>
				<td>
					Subtipo: ${navegacaoMBean.produto.subTipo.denominacao}
				</td>
			</tr>
			<tr>
				<td>
					Características: ${navegacaoMBean.produto.caracteristicas}
				</td>
			</tr>
			<tr>
				<td>
					Observação: ${navegacaoMBean.produto.observacao}
				</td>
			</tr>
			<tr>
				<td>
					Preço: R$ <h:outputText value="#{navegacaoMBean.produto.preco}">
							<f:convertNumber pattern="#,##0.00"/>
						</h:outputText>
				</td>
			</tr>
			<tr>
				<td>
					Peso: ${navegacaoMBean.produto.weight} Kg
				</td>
			</tr>
			<tr>
				<td>
					Tamanho: ${navegacaoMBean.produto.size} m
				</td>
			</tr>
			<tr>
				<td>
					Garantia: ${navegacaoMBean.produto.warranty} anos
				</td>
			</tr>
			</tbody>
		</table>
		<h:commandLink value="Voltar" action="#{navegacaoMBean.back}" />
		<h:commandLink action="#{carrinhoMBean.incluirItemCarrinho}">
			<h:graphicImage url="/img/carrinho.gif" width="25" height="25"/>
			<f:param name="idProduto" value="#{navegacaoMBean.produto.id}"/>
		</h:commandLink>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>
</f:view>