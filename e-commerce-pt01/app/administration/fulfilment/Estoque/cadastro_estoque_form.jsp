<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Estoque de Produtos</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro de estoque de um determinado produto.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Seleção de produto</caption>
		<tr>
			<th class="obrigatorio">
				Produto:
			</th>
			<td>
				<h:inputText value="#{estoqueMBean.produto.nome}" id="produto" style="width: 430px;" />
 
                <rich:suggestionbox for="produto" height="100" width="430"  minChars="1" id="suggestion"
                       suggestionAction="#{estoqueMBean.autocompleteProduto}" var="_produto"
                       fetchValue="#{_produto.nome}">
                 
                      <h:column>
                    	<h:outputText value="#{_produto.nome}" />
                      </h:column>
                 
                    <a4j:support event="onselect">
                    	<f:setPropertyActionListener value="#{_produto.id}" target="#{estoqueMBean.produto.id}"  />
                    </a4j:support>
                </rich:suggestionbox>
			</td>
		</tr>
		<tr>
			<th class="obrigatorio">
				Quantidade:
			</th>
			<td>
				<h:inputText value="#{estoqueMBean.obj.quantidade}" size="7"/>
			</td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cadastrar" action="#{estoqueMBean.cadastrarEstoque}"/>
					<h:commandButton value="Cancelar" action="#{estoqueMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>