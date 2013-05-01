package br.com.ecommerce.dominio;

import java.util.List;

import br.com.ecommerce.dominio.produto.SubTipoProduto;
import br.com.ecommerce.dominio.produto.TipoProduto;

/**
 * DTO para operações de navegação
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class NavegacaoDTO{

	private TipoProduto tipoProduto;
	
	private List<SubTipoProduto> subTipos;
	
	public NavegacaoDTO(){
		
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setSubTipos(List<SubTipoProduto> subTipos) {
		this.subTipos = subTipos;
	}

	public List<SubTipoProduto> getSubTipos() {
		return subTipos;
	}

}
