<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Disponibilidade de Serviços</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro, modificação e remorção de intervalos de serviços.<br />
	Formato da hora: HH:mm
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Intervalo</caption>
		<tbody>
		<tr>
		<th class="obrigatorio">
					Produto:
				</th>
				<td>
			<h:inputText value="#{intervaloServicoMBean.obj.produto.nome}" id="produto" style="width: 430px;" />
 
                <rich:suggestionbox for="produto" height="100" width="430"  minChars="1" id="suggestion"
                       suggestionAction="#{intervaloServicoMBean.autocompleteProduto}" var="_produto"
                       fetchValue="#{_produto.nome}">
                 
                      <h:column>
                    	<h:outputText value="#{_produto.nome}" />
                      </h:column>
                 
                    <a4j:support event="onselect">
                    	<f:setPropertyActionListener value="#{_produto.id}" target="#{intervaloServicoMBean.obj.produto.id}"  />
                    </a4j:support>
                </rich:suggestionbox>
                </td>
		</tr>
			<tr>
				<th class="obrigatorio">
					Dia da Semana:
				</th>
				<td>
					<h:selectOneMenu value="#{intervaloServicoMBean.obj.diaSemana.id}">
						<f:selectItem itemLabel="-- Selecione um dia --" itemValue="0"/>
						<f:selectItems value="#{diaSemanaMBean.allCombo}"/>
					</h:selectOneMenu>
					
					<!--<h:inputText size="70" value="#{intervaloServicoMBean.intervalo.dia}"/>-->
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Hora Inicial:
				</th>
				<td>
					<h:inputText size="70" value="#{intervaloServicoMBean.horaInicial}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Hora Final:
				</th>
				<td>
					<h:inputText size="70" value="#{intervaloServicoMBean.horaFinal}"/>
				</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cadastrar" action="#{intervaloServicoMBean.cadastrarNovoIntervalo}"/>
					<h:commandButton value="Cancelar" action="#{intervaloServicoMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
	<br />
	
	<c:if test="${not empty intervaloServicoMBean.allIntervaloServico}">
		<div class="legenda">
			<h:graphicImage url="/img/lupa.gif" width="16" height="16"/>: Visualizar
			<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>: Remover
		</div>
		
		<table class="listagem">
			<caption>Intervalos Disponíveis</caption>
			<thead>
				<tr>
					<td>Produto</td>
					<td>Dia</td>
					<td>Hora Inicial</td>
					<td>Hora Final</td>
					<td>Data de Cadastro</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="#{intervaloServicoMBean.allIntervaloServico}" var="servico" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'linhaPar' : 'linhaImpar' }">
						<td>
							${servico.produto.nome}
						</td>
						<td>
							${servico.diaSemana.denominacao}
						</td>
						<td>
							${servico.horainicio}
						</td>
						<td>
							${servico.horafim}
						</td>
						<td>
							<h:outputText value="#{servico.dataCadastro}">
								<f:convertDateTime pattern="dd/MM/yyyy"/>
							</h:outputText>
						</td>
						<td>
							<h:commandLink action="#{intervaloServicoMBean.visualizarDetalhadamenteIntervalo}">
								<h:graphicImage url="/img/lupa.gif" width="16" height="16"/>
								<f:param name="idIntervaloServico" value="#{servico.id}"/>
							</h:commandLink>
							<h:commandLink action="#{intervaloServicoMBean.removerIntervalo}">
								<h:graphicImage url="/img/lixeira.png" width="16" height="16"/>
								<f:param name="idIntervaloServico" value="#{servico.id}"/>
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