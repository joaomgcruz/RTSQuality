package br.ufrn.ppgsc.scenario.analyzer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Execution implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "date")
	private Date date;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "execution")
	private List<Scenario> scenarios;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Scenario> getScenarios() {
		return Collections.unmodifiableList(scenarios);
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}
	public void addToScenarios(Scenario scenario) {
		if (this.scenarios == null) {
			setScenarios(new ArrayList<Scenario>());
		}
		this.scenarios.add(scenario);
	}
}
