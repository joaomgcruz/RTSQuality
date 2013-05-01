package br.com.ecommerce.action.annotation;

import java.lang.annotation.Annotation;
import java.util.Date;

import javax.faces.context.FacesContext;

import br.com.ecommerce.arq.erros.DAOException;
import br.com.ecommerce.arq.sbeans.SBeanCadastro;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.IActionAnnotation;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimePerformanceData;
import br.ufrn.ppgsc.scenario.analyzer.d.data.RuntimeQAData;

public class ActionPerformance implements IActionAnnotation {
	
	@ScenarioIgnore
	public void execute(RuntimeQAData<? extends Annotation> metadata) {
		PerformanceAnnotation ann_db = new PerformanceAnnotation();
		RuntimePerformanceData meta_perf = (RuntimePerformanceData) metadata;
		
		ann_db.setAnnotation_max_value(meta_perf.getAnnotation().limit());
		ann_db.setAnnotation_name(meta_perf.getAnnotation().name());
		ann_db.setExecution_date(new Date());
		ann_db.setExecution_time(meta_perf.getLastTime());
		ann_db.setClass_name(meta_perf.getMethod().getDeclaringClass().getName());
		ann_db.setMethod_signature(meta_perf.getMethod().getName());
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Object sbean = fc.getELContext().getELResolver().getValue(fc.getELContext(), null, "SBeanCadastro");
		
		try {
			((SBeanCadastro)sbean).saveAnnotation(ann_db);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

}
