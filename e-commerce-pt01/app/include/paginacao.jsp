
<%@include file="/include/basic.jsp" %>

<h:form>
	<c:if test="${!paginacao.firstPage}">
		<h:commandLink action="#{paginacao.voltar}">
			<h:graphicImage url="/img/voltar.png" />
		</h:commandLink>
	</c:if>
	<c:forEach items="#{paginacao.navegacao}" var="numero">
		<c:if test="${numero == paginacao.paginaAtual}">
			<font color="red;">
				&nbsp;&nbsp;${numero}&nbsp;&nbsp;
			</font>
		</c:if>
		<h:commandLink action="#{paginacao.irPagina}">
			&nbsp;&nbsp;${numero}&nbsp;&nbsp;
			<f:param value="#{numero}" name="numeroPaginaDestino"/>
		</h:commandLink>
	</c:forEach>
	<c:if test="${!paginacao.lastPage}">
		<h:commandLink action="#{paginacao.avancar}">
			<h:graphicImage url="/img/avancar.png" />
		</h:commandLink>
	</c:if>
</h:form>