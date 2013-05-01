<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Disponibilidade de Serviços</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro, modificação e remorção de planejamentos de serviços.<br />
	Formato da hora: HH:mm
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Planejamento</caption>
		<tbody>
		<tr>
		<th class="obrigatorio">
					Produto:
				</th>
				<td>
			<h:inputText value="#{planejamentoServicoMBean.obj.produto.nome}" id="produto" style="width: 430px;" />
 
                <rich:suggestionbox for="produto" height="100" width="430"  minChars="1" id="suggestion"
                       suggestionAction="#{planejamentoServicoMBean.autocompleteProduto}" var="_produto"
                       fetchValue="#{_produto.nome}">
                 
                      <h:column>
                    	<h:outputText value="#{_produto.nome}" />
                      </h:column>
                 
                    <a4j:support event="onselect">
                    	<f:setPropertyActionListener value="#{_produto.id}" target="#{planejamentoServicoMBean.obj.produto.id}"  />
                    </a4j:support>
                </rich:suggestionbox>
                </td>
		</tr>
			<tr>
				<th class="obrigatorio">
					Equipe:
				</th>
				<td>
					<h:inputText size="70" value="#{planejamentoServicoMBean.obj.equipe}"/>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cadastrar" action="#{planejamentoServicoMBean.cadastrarNovoPlanejamento}"/>
					<h:commandButton value="Cancelar" action="#{planejamentoServicoMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
	<br />
	
	<c:if test="${not empty planejamentoServicoMBean.allPlanejamentoServico}">
		<div class="legenda">
			<h:graphicImage url="/img/lupa.gif" width="16" height="16"/>: Visualizar
			<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>: Remover
		</div>
		
		<table class="listagem">
			<caption>Planejamentos Disponíveis</caption>
			<thead>
				<tr>
					<td>Produto</td>
					<td>Equipe</td>
					<td>Data de Cadastro</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{planejamentoServicoMBean.allPlanejamentoServico}" var="servico" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							${servico.produto.nome}
						</td>
						<td>
							${servico.equipe}
						</td>
						<td>
							<h:outputText value="#{servico.dataCadastro}">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:outputText>
						</td>
						<td>
							<h:commandLink action="#{planejamentoServicoMBean.visualizarDetalhadamentePlanejamento}">
								<h:graphicImage url="/img/lupa.gif" width="16" height="16"/>
								<f:param name="idPlanejamentoServico" value="#{servico.id}"/>
							</h:commandLink>
							<h:commandLink action="#{planejamentoServicoMBean.removerPlanejamento}">
								<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>
								<f:param name="idPlanejamentoServico" value="#{servico.id}"/>
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