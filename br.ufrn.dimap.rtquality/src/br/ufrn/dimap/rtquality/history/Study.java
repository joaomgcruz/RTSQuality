package br.ufrn.dimap.rtquality.history;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufrn.dimap.ttracker.data.Revision;
import br.ufrn.dimap.ttracker.data.Task;

public class Study {
	private Revision startRevision;
	private Revision endRevision;
	private List<Task> tasks;
	
	public Study(Revision startRevision, Revision endRevision, List<Task> tasks) {
		this.startRevision = startRevision;
		this.endRevision = endRevision;
		this.tasks = tasks;
	}
	
	public Set<String> getTasksModifiedMethods() {
		Set<String> modifiedMethods = new HashSet<String>();
		for(Task task : tasks)
			modifiedMethods.addAll(task.getModifiedMethods());
		return modifiedMethods;
	}

	public Revision getStartRevision() {
		return startRevision;
	}

	public void setStartRevision(Revision startRevision) {
		this.startRevision = startRevision;
	}

	public Revision getEndRevision() {
		return endRevision;
	}

	public void setEndRevision(Revision endRevision) {
		this.endRevision = endRevision;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
