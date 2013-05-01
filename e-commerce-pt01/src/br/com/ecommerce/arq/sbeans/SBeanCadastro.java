package br.com.ecommerce.arq.sbeans;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.dominio.SimpleDB;
import br.com.ecommerce.arq.erros.DAOException;

/**
 * Processador de cadastro que deve ser herdado por todos os processadores
 * que cadastrarem CadastroDBs
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Service
@Transactional
public class SBeanCadastro extends AbstractSBean{

	public SBeanCadastro(){
		
	}
	
	/**
	 * Método chamado para cadastro de um log de anotação no banco
	 * @throws DAOException 
	 */
	public void saveAnnotation(SimpleDB obj) throws DAOException{
		
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.saveAnnotation(obj);
		}finally{
			dao.close();
		}
	}
		
	/**
	 * Método chamado para cadastro de CadastroDBs
	 * @throws DAOException 
	 */
	public void cadastrar(CadastroDB obj) throws DAOException{
		obj.setDataCadastro(new Date());
		
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.save(obj);
		}finally{
			dao.close();
		}
	}
	
	/**
	 * Método chamado para cadastro de SimpleDB
	 * @throws DAOException 
	 */
	public void cadastrar(SimpleDB obj) throws DAOException{
		
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.save(obj);
		}finally{
			dao.close();
		}
	}
}
