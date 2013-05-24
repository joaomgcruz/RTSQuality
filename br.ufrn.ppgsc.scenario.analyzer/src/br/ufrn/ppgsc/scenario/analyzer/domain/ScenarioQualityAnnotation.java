package br.ufrn.ppgsc.scenario.analyzer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ScenarioQualityAnnotation implements Serializable {
	
	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public QualityAnnotation getQualityAnnotation() {
		return qualityAnnotation;
	}

	public void setQualityAnnotation(QualityAnnotation qualityAnnotation) {
		this.qualityAnnotation = qualityAnnotation;
	}
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Scenario.class)
	@JoinColumn(name = "scenarioID")
	private Scenario scenario;
	
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = QualityAnnotation.class)
	@JoinColumn(name = "qualityAnnotationID")
	private QualityAnnotation qualityAnnotation;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scenarioQualityAnnotation")
	private List<QualityAnnotationAttribute> qualityAnnotationAttributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<QualityAnnotationAttribute> getQualityAnnotationAttributes() {
		return Collections.unmodifiableList(qualityAnnotationAttributes);
	}

	public void setQualityAnnotationAttributes(
			List<QualityAnnotationAttribute> qualityAnnotationAttributes) {
		this.qualityAnnotationAttributes = qualityAnnotationAttributes;
	}
	public void addToQualityAnnotationAttributes(QualityAnnotationAttribute qualityAnnotationAttribute) {
		if (this.qualityAnnotationAttributes == null) {
			setQualityAnnotationAttributes(new ArrayList<QualityAnnotationAttribute>());
		}
		this.qualityAnnotationAttributes.add(qualityAnnotationAttribute);
	}
	
}
