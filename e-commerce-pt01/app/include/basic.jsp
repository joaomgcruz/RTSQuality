
<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@page import="java.text.*, java.util.*"%>

<%-- Não ignorar a Expression Language --%>
<%@page isELIgnored ="false" %>

<%-- Tags --%> 
	<%-- Tags Jstl --%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	
	<%-- Tags Jsf --%>
	<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
	<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
	
	<%-- Tags RichFaces e Ajax --%> 
	<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
	<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
	
	<%-- Tags específicas ecomerce --%>
	<%@ taglib uri="/WEB-INF/tlds/ecommerce.tld" prefix="ecommerce" %> 

<%-- Fim: Tags --%> 