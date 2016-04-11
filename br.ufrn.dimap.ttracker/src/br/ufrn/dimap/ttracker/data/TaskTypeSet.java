package br.ufrn.dimap.ttracker.data;

import java.util.HashSet;
import java.util.Set;

public class TaskTypeSet {
	private TaskType name;
	private Set<Task> tasks;
	private Float inclusion;
	private Float precision;
	private Set<Integer> qtdInclusionStart;
	private Set<Integer> qtdInclusionEnd;
	private Set<Integer> qtdPrecisionStart;
	private Set<Integer> qtdPrecisionEnd;

	public TaskTypeSet(TaskType name) {
		this.name = name;
		this.tasks = new HashSet<Task>();
		this.inclusion = 0F;
		this.precision = 0F;
		this.qtdInclusionStart = new HashSet<Integer>();
		this.qtdInclusionEnd = new HashSet<Integer>();
		this.qtdPrecisionStart = new HashSet<Integer>();
		this.qtdPrecisionEnd = new HashSet<Integer>();
	}

	public String getName() {
		return name.getName();
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Float getInclusion() {
		return inclusion;
	}

	public void setInclusion(Float inclusion) {
		this.inclusion = inclusion;
	}

	public Float getPrecision() {
		return precision;
	}

	public void setPrecision(Float precision) {
		this.precision = precision;
	}

	public Set<Integer> getQtdInclusionStart() {
		return qtdInclusionStart;
	}

	public void setQtdInclusionStart(Set<Integer> qtdInclusionStart) {
		this.qtdInclusionStart = qtdInclusionStart;
	}

	public Set<Integer> getQtdInclusionEnd() {
		return qtdInclusionEnd;
	}

	public void setQtdInclusionEnd(Set<Integer> qtdInclusionEnd) {
		this.qtdInclusionEnd = qtdInclusionEnd;
	}

	public Set<Integer> getQtdPrecisionStart() {
		return qtdPrecisionStart;
	}

	public void setQtdPrecisionStart(Set<Integer> qtdPrecisionStart) {
		this.qtdPrecisionStart = qtdPrecisionStart;
	}

	public Set<Integer> getQtdPrecisionEnd() {
		return qtdPrecisionEnd;
	}

	public void setQtdPrecisionEnd(Set<Integer> qtdPrecisionEnd) {
		this.qtdPrecisionEnd = qtdPrecisionEnd;
	}

}
