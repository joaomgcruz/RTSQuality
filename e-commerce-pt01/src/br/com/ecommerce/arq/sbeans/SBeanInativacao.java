package br.com.ecommerce.arq.sbeans;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.erros.DAOException;

/**
 * Processador de inativação que deve ser herdado por todos os processadores
 * que inativarem CadastroDBs
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Service
public class SBeanInativacao extends AbstractSBean{

	public SBeanInativacao(){
		
	}
	
	public void inativar(CadastroDB obj) throws DAOException{
		obj.setInativo(true);
		obj.setDataInativacao(new Date());
		
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.detach(obj);
			dao.update(obj);
		}finally{
			dao.close();
		}
	}
}
