
<%@include file="/include/basic.jsp" %>
<div id="esq">
            
     <ul>       
     	<c:forEach items="#{navegacaoMBean.navegacaoDTO}" var="dto">
		
			<li>${dto.tipoProduto.denominacao}
				<ul>
					<c:forEach items="#{dto.subTipos}" var="st">					
						<li><h:commandLink value="#{st.denominacao}" action="#{navegacaoMBean.navegarSubTipo}">
							<f:param name="idSubTipo" value="#{st.id}" />
						</h:commandLink>
						</li>
					</c:forEach>
				</ul>
			</li>
		</c:forEach>
	</ul>
   </div>
   <div id="conteudo">
            	