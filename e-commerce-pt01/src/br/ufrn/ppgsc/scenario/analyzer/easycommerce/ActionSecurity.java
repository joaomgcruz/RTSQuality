package br.ufrn.ppgsc.scenario.analyzer.easycommerce;

import java.lang.annotation.Annotation;
import java.util.Date;

import javax.faces.context.FacesContext;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.IActionAnnotation;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeQAData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeSecurityData;

public class ActionSecurity implements IActionAnnotation {

	public void execute(RuntimeQAData<? extends Annotation> metadata) {
		SecurityAnnotation ann_db = new SecurityAnnotation();
		RuntimeSecurityData meta_secur = (RuntimeSecurityData) metadata;

		ann_db.setAnnotation_name(meta_secur.getAnnotation().name());
		ann_db.setClass_name(meta_secur.getMethod().getDeclaringClass().getName());
		ann_db.setMethod_signature(meta_secur.getMethod().getName());
		ann_db.setExecution_date(new Date());
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Object sbean = fc.getELContext().getELResolver().getValue(fc.getELContext(), null, "SBeanCadastro");
		
		try {
			((SBeanCadastro)sbean).saveAnnotation(ann_db);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
