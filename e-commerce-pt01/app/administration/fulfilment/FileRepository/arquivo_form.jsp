<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>
<h2>Estoque de Produtos</h2>
<div class="descricaoOperacao">
	Esta operação permite o cadastro de um arquivo.
</div>
<h:form>
	<ecommerce:Mensagens />
	<table class="formulario">
		<caption>Dados do Arquivo</caption>
		<tr>
			<th class="obrigatorio">
				Nome:
			</th>
			<td>
				<h:inputText value="#{fileRepositoryMBean.obj.nome}"/>
			</td>
		</tr>
		<tr>
			<th class="obrigatorio">
				Descrição:
			</th>
			<td>
				<h:inputTextarea cols="80" rows="5" value="#{fileRepositoryMBean.obj.descricao}"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;">
				<center>
					<rich:fileUpload fileUploadListener="#{fileRepositoryMBean.upload}" addControlLabel="Upload"
						clearAllControlLabel="Limpar Todos" clearControlLabel="Limpar" maxFilesQuantity="100"
						immediateUpload="true"> 
						<a4j:support event="onuploadcomplete" actionListener="#{fileRepositoryMBean.atualizar}" reRender="cadastrarButton"/>
					</rich:fileUpload>
				</center>
			</td>
		</tr>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cadastrar" action="#{fileRepositoryMBean.cadastrar}" disabled="#{fileRepositoryMBean.disabled}" id="cadastrarButton"/>
					<h:commandButton value="Cancelar" action="#{fileRepositoryMBean.cancelar}"/>
				</td>
			</tr>
		</tfoot>
	</table>
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>