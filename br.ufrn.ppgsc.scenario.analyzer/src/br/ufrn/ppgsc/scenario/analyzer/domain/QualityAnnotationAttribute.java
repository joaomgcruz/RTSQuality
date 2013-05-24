package br.ufrn.ppgsc.scenario.analyzer.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class QualityAnnotationAttribute implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "value")
	private String value;
	
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = ScenarioQualityAnnotation.class)
	@JoinColumn(name = "scenarioQualityAnnotationID")
	private ScenarioQualityAnnotation scenarioQualityAnnotation;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ScenarioQualityAnnotation getScenarioQualityAnnotation() {
		return scenarioQualityAnnotation;
	}

	public void setScenarioQualityAnnotation(ScenarioQualityAnnotation scenarioQualityAnnotation) {
		this.scenarioQualityAnnotation = scenarioQualityAnnotation;
	}
	
}
