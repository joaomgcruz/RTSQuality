<%@include file="/include/basic.jsp" %>
<f:view>
<%@include file="/include/head_administracao.jsp" %>


<h:form>
	<ecommerce:Mensagens />
	
	
	<%@include file="/menu.jsp"%>
	

	<table class="formulario" width="80%">
		<caption>Informações gerais do cliente</caption>
		<tbody>
			<tr>
				<th class="obrigatorio">
					Nome:
				</th>
				<td>
					<h:inputText size="80" value="#{registroMBean.obj.pessoa.nome}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Endereço:
				</th>
				<td>
					<h:inputText size="80" value="#{registroMBean.endereco.endereco}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Número da casa:
				</th>
				<td>
					<h:inputText size="5" value="#{registroMBean.endereco.numeroCasa}"/>
				</td>
			</tr>
			<tr>
				<th>
					Complemento:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.endereco.complemento}"/>
				</td>
			</tr>
			<tr>
				<th class="obrigatorio">
					Bairro:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.endereco.bairro}"/>
				</td>
			</tr>
			<tr>
				<th>
					Cidade:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.endereco.cidade.denominacao}"/>
				</td>
			</tr>
			<tr>
				<th>
					Estado:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.endereco.cidade.estado.denominacao}"/>
				</td>
			</tr>
			<tr>
				<th>
					CEP:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.endereco.CEP}"/>
				</td>
			</tr>
			<tr>
				<th>
					Data de nascimento:
				</th>
				<td>
				<rich:calendar firstWeekDay="0" showWeeksBar="false" 
					enableManualInput="true" datePattern="dd/MM/yyyy" 
					oninputkeyup="formatarData(this, event);" value="#{registroMBean.obj.pessoa.dataNascimento}"/>
				</td>
			</tr>
			<tr>
				<th>
					CPF:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.obj.pessoa.CPF}"/>
				</td>
			</tr>
			<tr>
				<th>
					RG:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.obj.pessoa.RG}"/>
				</td>
			</tr> 
			<tr>
				<th>
					Renda:
				</th>
				<td>
					<h:inputText size="40" value="#{registroMBean.obj.pessoa.demografico.renda}"/>
				</td>
			</tr>
			<tr>
				<th>
					e-mail:
				</th>
				<td>
					<h:inputText size="80" value="#{registroMBean.obj.pessoa.email}"/>
				</td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2">
					<h:commandButton value="Cancelar" action="#{registroMBean.voltar}"/>
					<h:commandButton value="Avançar >>" action="#{registroMBean.cadastroInformacoesGerais}"/>
				</td>
			</tr>
		</tfoot>
	</table>
	
</h:form>
<%@include file="/include/tail_administracao.jsp" %>	
</f:view>