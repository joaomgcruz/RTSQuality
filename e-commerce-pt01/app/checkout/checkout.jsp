<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Checkout</h2>
<div class="descricaoOperacao">
	Encerramento de Compras.	
</div>
<h:form>
	<ecommerce:Mensagens />
	<c:if test="${not empty checkoutMBean.valorTotal}">
O total da sua compra é de:		
<h:outputText value="#{checkoutMBean.valorTotal}" >
<f:convertNumber pattern="#,##0.00" />
</h:outputText>	
</c:if>
	<br />
	
	Escolha a forma de pagamento:
	<h:selectOneRadio id="PaymentTypes" title="Escolha a forma de pagamento:" border="1">
		
			<f:selectItem id="p1" itemLabel="Cartão de Crédito" itemValue="1" />
		
			<f:selectItem id="p2" itemLabel="Cartão de Débito" itemValue="2" />
		
			<f:selectItem id="p3" itemLabel="Ordem Bancária" itemValue="3" />
	</h:selectOneRadio>
	<br/>
	Escolha a forma de confirmação do Pedido:
	<h:selectOneRadio id="OrdemConfirmation" title="Escolha a confirmação do pagamento:" border="1">
		
			<f:selectItem id="o1" itemLabel="Página Eletrônica" itemValue="1" />
		
			<f:selectItem id="o2" itemLabel="E-mail" itemValue="2" />
		
			<f:selectItem id="o3" itemLabel="Telefone" itemValue="3" />
	</h:selectOneRadio>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>
