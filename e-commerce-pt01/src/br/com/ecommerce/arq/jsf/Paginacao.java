package br.com.ecommerce.arq.jsf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementação da paginação.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Component
@Scope("session")
public class Paginacao extends AbstractController{
	
	private List<Integer> navegacao;

	/**
	 * Página que se esta sendo usada para trabalho.
	 */
	private int paginaAtual;
	
	/**
	 * Tamanho de registros na página.
	 */
	private int tamanhoPagina;
	
	private long quantidadeResultados;
	
	public Paginacao(){
		reset();
	}
	
	public void reset(){
		paginaAtual = 0;
		tamanhoPagina = 20;
		ajustarIntervalo();
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setTamanhoPagina(int tamanhoPagina) {
		this.tamanhoPagina = tamanhoPagina;
	}

	public int getTamanhoPagina() {
		return tamanhoPagina;
	}

	public void setQuantidadeResultados(long quantidadeResultados) {
		this.quantidadeResultados = quantidadeResultados;
	}

	public long getQuantidadeResultados() {
		return quantidadeResultados;
	}

	public boolean isFirstPage(){
		return paginaAtual == 0;
	}
	
	public boolean isLastPage(){
		return paginaAtual*tamanhoPagina + tamanhoPagina >= quantidadeResultados ? true : false;
	}
	
	public String voltar(){
		if(paginaAtual != 0)
			paginaAtual -= 1;
		return ajustarIntervalo();
	}
	
	public String avancar(){
		if(paginaAtual*tamanhoPagina + tamanhoPagina <= quantidadeResultados)
			paginaAtual += 1;
		return ajustarIntervalo();
	}

	public void setNavegacao(List<Integer> navegacao) {
		this.navegacao = navegacao;
	}

	public List<Integer> getNavegacao() {
		return navegacao;
	}
	
	public String irPagina(){
		paginaAtual = Integer.parseInt(getParameter("numeroPaginaDestino"));
		return ajustarIntervalo();
	}
	
	public String ajustarIntervalo(){
		navegacao = new ArrayList<Integer>(10);
		
		for(int i = 0; i<10; i++){
			if((paginaAtual + i) - 5 > 0){
				if(((paginaAtual + i) - 5)*tamanhoPagina + tamanhoPagina <= quantidadeResultados)
					navegacao.add((paginaAtual + i) - 5);
			}
		}
		
		return null;
	}
}
