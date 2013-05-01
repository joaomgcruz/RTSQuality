<%@include file="/include/basic.jsp"%>
<f:view>
	<%@include file="/include/head_administracao.jsp"%>
	
	
	<h:form>
		<ecommerce:Mensagens />
		
	<%@include file="/menu.jsp"%>
	
	<p><img src="include/resources/imgs/destaque.jpg" alt="Destaque" /></p>
	
	<div class="descricaoOperacao">Esta operação permite a exibição
	dos produtos do tipo e subTipo selecionados.</div>
	
		<c:forEach items="#{navegacaoMBean.colecao}" var="produto">
			<table class="formulario" width="70%">
				<caption>${produto.nome}</caption>
				<tr>
					<th>Observação:</th>
					<td>${produto.observacao}</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<h:commandLink action="#{carrinhoMBean.incluirItemCarrinho}" value="Adicionar ao Carrinho" >
							<f:param name="idProduto" value="#{produto.id}" />
						</h:commandLink>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><h:commandLink value="Voltar"
						action="#{navegacaoMBean.voltar}" /></td>
				</tr>
			</table>
			
		</c:forEach>
		
	</h:form>
	<%@include file="/include/paginacao.jsp"%>

	<%@include file="/include/tail_administracao.jsp"%>
</f:view>