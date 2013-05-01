package br.com.ecommerce.arq.aspects;

import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.dominio.produto.Produto;

@Aspect
public class AutorizacaoNecessariaAspect {
	
	//@Around("execution(* br.com.ecommerce.arq.sbeans.ContextProdutoSBean.cadastrar(br.com.ecommerce.arq.dominio.CadastroDB)) && args(obj)")
	@Around("execution(* br.com.ecommerce.arq.sbeans.ProdutoSBean.cadastrar(br.com.ecommerce.arq.dominio.CadastroDB)) && args(obj)")
	public void InsereAutorizacao(ProceedingJoinPoint pjp, CadastroDB obj)
			throws Throwable {
		((Produto) obj).setAutorizado(false);
		pjp.proceed();
	}

	@AfterReturning(pointcut = "execution(* br.com.ecommerce.dao.ProdutoDAO.findAllAtivos())", returning = "produtos", argNames = "produtos")
	public void RetiraAutorizados(List<Produto> produtos) throws Throwable {

		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto produto = (Produto) iterator.next();
			if (produto.isAutorizado())
				iterator.remove();
		}

	}
	
	@AfterReturning(pointcut = "execution(* br.com.ecommerce.dao.ProdutoDAO.findGeral(..))", returning = "produtos", argNames = "produtos")
	public void RetiraNaoAutorizados(List<Produto> produtos) throws Throwable {

		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto produto = (Produto) iterator.next();
			if (!produto.isAutorizado())
				iterator.remove();
		}

	}
	
	@AfterReturning(pointcut = "execution(* br.com.ecommerce.dao.ProdutoDAO.autoCompleteProduto(..))", returning = "produtos", argNames = "produtos")
	public void AutoCompleteNaoAutorizados(List<Produto> produtos) throws Throwable {

		for (Iterator iterator = produtos.iterator(); iterator.hasNext();) {
			Produto produto = (Produto) iterator.next();
			if (!produto.isAutorizado())
				iterator.remove();
		}

	}

}