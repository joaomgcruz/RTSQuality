<%@include file="/include/basic.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>EasyCommerce</title>
		
		<!-- Css -->
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/include/resources/style.css">
		
		<!-- JavaScript -->
		<script type="text/javascript" src="<%=request.getContextPath() %>/include/resources/javascripts.js"></script>
		
	</head>
	<body>
	
	
		
	
		<div id="geral">
		
			<div id="topo">
			<h:form>
			
				<h1><h:commandLink action="#{registroMBean.voltar}"><img src="<%=request.getContextPath() %>/include/resources/imgs/easycommerce.gif" alt="EasyCommerce" /></h:commandLink></h1>
		        <div id="menu_user">
		        	<ol>
		        	
						
		            	<li><img src="<%=request.getContextPath() %>/include/resources/imgs/icon_user.gif"  alt="boneco"/> <h:commandLink value="Registre-se" action="#{registroMBean.iniciarCadastro}"/></li>
		                <li><img src="<%=request.getContextPath() %>/include/resources/imgs/icon_car.gif"  alt="carro"/> <h:commandLink value="Ver itens" action="#{carrinhoMBean.iniciarCarrinho}"/></li>
		                <li><img src="<%=request.getContextPath() %>/include/resources/imgs/lock.png"  alt="cadeado"/> <h:commandLink action="#{navegacaoMBean.goPortalAdministrativo}" value="Administração"/></li>
		               
		                
		            </ol>        
		        </div>
		        <div id="busca">
		        	
		        		
<p class="busca_av"><img src="<%=request.getContextPath() %>/include/resources/imgs/seta.gif" alt="seta" /> <h:commandLink value="Busca avançada" action="#{pesquisaMBean.initAdvancedSearch}"/></p>

            	
            		
		           	
		            	<p>
		            	<img src="<%=request.getContextPath() %>/include/resources/imgs/tit_busca.gif" alt="busca" />
		            	<span><h:inputText  value="#{pesquisaMBean.obj.nome}" /></span>
                
                    		<h:commandButton  image="/include/resources/imgs/botao_busca.jpg" action="#{pesquisaMBean.buscaBasicaProdutos}"/></p>
		        
		        </div>
		    </h:form>     
			</div>
			
			<ecommerce:Mensagens />
			<div id="principal">


    
                    







			
