package br.com.ecommerce.action.annotation;

import java.util.Date;

import javax.persistence.Entity;

import br.com.ecommerce.arq.dominio.SimpleDB;
import br.ufrn.ppgsc.scenario.analyzer.d.aspects.ScenarioIgnore;

@Entity
public class SecurityAnnotation extends SimpleDB {

	private String annotation_name;
	private String class_name;
	private String method_signature;
	private Date execution_date;

	@ScenarioIgnore
	public String getAnnotation_name() {
		return annotation_name;
	}

	@ScenarioIgnore
	public void setAnnotation_name(String annotation_name) {
		this.annotation_name = annotation_name;
	}

	@ScenarioIgnore
	public String getClass_name() {
		return class_name;
	}

	@ScenarioIgnore
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	@ScenarioIgnore
	public String getMethod_signature() {
		return method_signature;
	}

	@ScenarioIgnore
	public void setMethod_signature(String method_signature) {
		this.method_signature = method_signature;
	}

	@ScenarioIgnore
	public Date getExecution_date() {
		return execution_date;
	}

	@ScenarioIgnore
	public void setExecution_date(Date execution_date) {
		this.execution_date = execution_date;
	}
}
