package br.com.ecommerce.arq.sbeans;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.ecommerce.arq.dao.GenericDAO;
import br.com.ecommerce.arq.dao.GenericDAOImpl;
import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.arq.erros.DAOException;

/**
 * Session Bean relativo a opera?›es de registro.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Service
public class RegistroSBean extends AbstractSBean{

	/**
	 * Método chamado para cadastro de CadastroDBs
	 * @throws DAOException 
	 */
//	@UseCase(name="usecase_registration_user", step="3.1",
//			performance=@Performance(name="performance_registration_sbean", max_value=2600),
//			security=@Security("security_registration_sbean"))
	public void cadastrar(CadastroDB obj) throws DAOException{
		obj.setDataCadastro(new Date());
		
		GenericDAO dao = getDAO(GenericDAOImpl.class);
		
		try{
			dao.save(obj);
		}finally{
			dao.close();
		}
		
	}
}
