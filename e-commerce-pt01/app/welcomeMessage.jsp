<%@include file="/include/basic.jsp" %>

<div align="center">
	<c:if test="${not empty mensagemMBean.allMensagem}">
		<c:forEach items="#{mensagemMBean.allMensagem}" var="mensagem">
			<c:if test="${mensagem.ativado}">
				<br /><h7> ${mensagem.denominacao}</h7>
			</c:if>
		</c:forEach>
	</c:if>	
</div>