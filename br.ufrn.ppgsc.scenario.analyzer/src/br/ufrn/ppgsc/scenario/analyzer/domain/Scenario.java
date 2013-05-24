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
public class Scenario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(cascade = CascadeType.REFRESH, targetEntity = Execution.class)
	@JoinColumn(name = "executionID")
	private Execution execution;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scenario")
	private List<StartMethod> startMethods;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scenario")
	private List<ScenarioQualityAnnotation> scenarioQualityAnniotations;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Execution getExecution() {
		return execution;
	}
	public void setExecution(Execution execution) {
		this.execution = execution;
	}
	public List<StartMethod> getStartMethods() {
		return Collections.unmodifiableList(startMethods);
	}
	public void setStartMethods(List<StartMethod> startMethods) {
		this.startMethods = startMethods;
	}
	public void addToStartMethods(StartMethod startMethod) {
		if (this.startMethods == null) {
			setStartMethods(new ArrayList<StartMethod>());
		}
		this.startMethods.add(startMethod);
	}
	public List<ScenarioQualityAnnotation> getScenarioQualityAnniotations() {
		return Collections.unmodifiableList(scenarioQualityAnniotations);
	}
	public void setScenarioQualityAnniotations(
			List<ScenarioQualityAnnotation> scenarioQualityAnniotations) {
		this.scenarioQualityAnniotations = scenarioQualityAnniotations;
	}
	public void addToScenarioQualityAnnotations(ScenarioQualityAnnotation scenarioQualityAnnotation) {
		if (this.scenarioQualityAnniotations == null) {
			setScenarioQualityAnniotations(new ArrayList<ScenarioQualityAnnotation>());
		}
		this.scenarioQualityAnniotations.add(scenarioQualityAnnotation);
	}

}
