<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" version="2.0">
    <jsp:directive.page language="java"
        contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" />
    <jsp:text>
        <![CDATA[ <?xml version="1.0" encoding="ISO-8859-1" ?> ]]>
    </jsp:text>
    <jsp:text>
        <![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
    </jsp:text>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<f:view>
<h:form>
<table>
	<tr><td colspan="2"><h:outputLabel style="color: red" value="#{operacaoBean.alerta}"/></td></tr>
	<tr>
		<td>Termo Um:</td>
		<td><h:inputText value="#{operacaoBean.termoUm}"/></td>
	</tr>
	<tr>
		<td>Termo Dois:</td>
		<td><h:inputText value="#{operacaoBean.termoDois}"/></td>
	</tr>
	<tr>
		<td>Resultado:</td>
		<td><h:inputText value="#{operacaoBean.resultado}"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<h:panelGroup>
				<h:commandButton action="#{operacaoBean.operacaoAdicao}" type="submit" value="Adição"/>
				<h:commandButton action="#{operacaoBean.operacaoSubtracao}" type="submit" value="Subtração"/>
				<h:commandButton action="#{operacaoBean.operacaoMultiplicacao}" type="submit" value="Multiplicação"/>
				<h:commandButton action="#{operacaoBean.operacaoDivisao}" type="submit" value="Divisão"/>
			</h:panelGroup>
		</td>
	</tr>
</table>



</h:form>
</f:view>
</body>
</html>
</jsp:root>