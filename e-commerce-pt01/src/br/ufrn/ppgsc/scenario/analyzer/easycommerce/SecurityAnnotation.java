package br.ufrn.ppgsc.scenario.analyzer.easycommerce;

import java.util.Date;

import javax.persistence.Entity;

import br.com.ecommerce.arq.dominio.SimpleDB;

@Entity
public class SecurityAnnotation extends SimpleDB {

	private String annotation_name;
	private String class_name;
	private String method_signature;
	private Date execution_date;

	public String getAnnotation_name() {
		return annotation_name;
	}

	public void setAnnotation_name(String annotation_name) {
		this.annotation_name = annotation_name;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getMethod_signature() {
		return method_signature;
	}

	public void setMethod_signature(String method_signature) {
		this.method_signature = method_signature;
	}

	public Date getExecution_date() {
		return execution_date;
	}

	public void setExecution_date(Date execution_date) {
		this.execution_date = execution_date;
	}
}
