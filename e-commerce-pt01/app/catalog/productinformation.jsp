<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>LOJA</h2>
<div class="descricaoOperacao">
	<div align="center">Produtos | Mídias Eletrônicas | Serviços</div>
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Produto</caption>
		<tbody>
		<!-- 
		<tr>
		<th class="obrigatorio">
					Produto:
				</th>
				<td>
			<h:inputText value="#{intervaloServicoMBean.obj.produto.nome}" id="produto" style="width: 430px;" />
 
                <rich:suggestionbox for="produto" height="100" width="430"  minChars="1" id="suggestion"
                       suggestionAction="#{produtoMBean.autocompleteProduto}" var="_produto"
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
			 -->
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
	
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>