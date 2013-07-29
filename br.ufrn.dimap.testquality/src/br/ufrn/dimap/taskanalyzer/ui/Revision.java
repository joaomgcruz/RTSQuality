package br.ufrn.dimap.taskanalyzer.ui;

public class Revision {
	private Integer id;
	private Float security;
	private Float precision;
	
	public Revision(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getSecurity() {
		return security;
	}

	public void setSecurity(Float security) {
		this.security = security;
	}

	public Float getPrecision() {
		return precision;
	}

	public void setPrecision(Float precision) {
		this.precision = precision;
	}

}
