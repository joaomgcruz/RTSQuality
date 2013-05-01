package br.com.ecommerce.arq.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.ecommerce.arq.util.SessionUtil;


/**
 * Gerência a sessão do usuário.
 */
public class ViewFilter implements Filter {
	private ArrayList<String> paginasPermitidas;
	
	public void destroy() {				
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
						throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String reqURL = req.getRequestURI().substring(req.getContextPath().length());

		try{
			if(paginasPermitidas.contains(reqURL) || paginasPermitidas.contains("*")){
					chain.doFilter(request, response);
			}else{
				if((reqURL.endsWith(".jsp") || reqURL.endsWith(".jsf")) && req.getSession().getAttribute("registroentrada") == null){
						((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsf");
				}else
					chain.doFilter(request, response);
			}
		}finally{
			closeSessions((HttpServletRequest) request);
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
		StringTokenizer paginas = new StringTokenizer(cfg.getInitParameter("PaginasPermitidas"), ",");
		
		paginasPermitidas = new ArrayList<String>();
		
		while(paginas.hasMoreTokens()){
			paginasPermitidas.add(paginas.nextToken().trim());
		}
		
		paginasPermitidas.add("/");
	}
	
	/**
	 * Método usado para se fechar as sessões com o banco ainda abertas.
	 * Usado para implementar o Open Session in View.
	 */
	private void closeSessions(HttpServletRequest request){
		
		if(request.getSession().getAttribute(SessionUtil.SESSIONS_ATRIBUTE) != null){
			Session session = (Session) request.getSession().getAttribute(SessionUtil.SESSIONS_ATRIBUTE);
			if(session.isOpen()){
				session.close();
				request.getSession().setAttribute(SessionUtil.SESSIONS_ATRIBUTE, null);
				System.out.println("Sessão fechada no viewFilter");
			}
		}
		
	}

}
