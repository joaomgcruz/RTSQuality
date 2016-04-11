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
	<tr>
		<td>
			<h:commandButton action="#{testTrackerBean.runTests}" type="submit" value="Run AllTests"/>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td>
			<h:inputTextarea value="#{testTrackerBean.text}" style="width: 500px;height: 200px"/>
		</td>
	</tr>
	<tr>
		<td>
			<h:inputText value="#{testTrackerBean.directory}" style="width: 100%"/>
		</td>
	</tr>
	<tr>
		<td align="right">
			<h:commandButton action="#{testTrackerBean.showAsXML}" type="submit" value="Show"/>
			<h:commandButton action="#{testTrackerBean.saveAsXML}" type="submit" value="Salve"/>
		</td>
	</tr>
	<tr>
		<td>
			<h:inputText value="#{testTrackerBean.oldExecution}" style="width: 100%"/>
		</td>
	</tr>
	<tr>
		<td align="right">
			<h:commandButton action="#{testTrackerBean.compare}" type="submit" value="Compare"/>
		</td>
	</tr>
	<tr>
		<td>
			<h:commandButton action="#{testTrackerBean.save}" type="submit" value="Save Tests Mapping"/>
			<h:commandButton action="#{testTrackerBean.differencingTextTestSelectionMeasurement}" type="submit" value="Measurement"/>
		</td>
	</tr>
</table>
</h:form>
</f:view>
</body>
</html>
</jsp:root>