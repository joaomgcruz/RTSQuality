package br.com.ecommerce.jsf.administration.fulfilment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.jsf.CadastroAbstractController;
import br.com.ecommerce.arq.jsf.OrderedSelectItem;
import br.com.ecommerce.arq.util.ReflectionUtils;
import br.com.ecommerce.dominio.IntervaloServico;
import br.com.ecommerce.dominio.TipoArmazenamento;

/**
 * Controlador responsável por operações relacionadas a agendamento de serviços.
 * Trabalha em cima do cadastro, remoção, listagem.
 * Oferece métodos para suggestion-box, e listagem de items.
 * 
 * @author Mario Torres
 */
@Component
@Scope("request")
public class TipoArmazenamentoMBean extends CadastroAbstractController<IntervaloServico>{

	/**
	 * Método usado para se buscar por todos os dias de semana.
	 * @return os tipos ativos encontrados.
	 * 
	 * @throws DAOException 
	 */
	public List<SelectItem> getAllCombo() throws DAOException{
		return toSelectItems(getGenericDAO().findAll(TipoArmazenamento.class,"id"), "id", "descricao");
	}
	
	public static List<SelectItem> toSelectItems(Collection<?> col, String value, String showText) {

		ArrayList<OrderedSelectItem> itensOrdenaveis = new ArrayList<OrderedSelectItem>();
		ArrayList<SelectItem> itens = new ArrayList<SelectItem>();

		try {
			if (col != null) {
				for (Iterator<?> it = col.iterator(); it.hasNext();) {
					Object obj = it.next();
					Object id = ReflectionUtils.evalProperty(obj, value);
					Object text = ReflectionUtils.evalProperty(obj, showText);
					if (text == null) {
						text = "";
					}
					OrderedSelectItem item = new OrderedSelectItem(id.toString(), text
							.toString());
					itensOrdenaveis.add(item);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for ( OrderedSelectItem item : itensOrdenaveis ) {
			itens.add(item.toSelectItem());
		}

		return itens;

	}

}
