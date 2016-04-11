package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private TaskType type;
	private String otherType;
	private List<Revision> revisions;
	private Revision oldRevision;
	private Revision currentRevision;
	private Set<TestCoverageGroup> oldSelectionGroup;
	private Set<TestCoverageGroup> oldExclusionGroup;
	private Set<TestCoverageGroup> currentSelectionGroup;
	private Set<TestCoverageGroup> currentExclusionGroup;
	private Set<String> modifiedMethods;
	private Float inclusion;
	private Float precision;
	private boolean doAndUndoDone;

	public Task(Integer id) {
		this.id = id;
		this.type = null;
		this.otherType = null;
		this.revisions = new ArrayList<Revision>();
		this.oldRevision = null;
		this.currentRevision = null;
		this.modifiedMethods = new HashSet<String>();
		this.inclusion = null;
		this.precision = null;
		this.doAndUndoDone = false;
	}
	
	public Task(Integer id, TaskType type, List<Revision> revisions) {
		this.id = id;
		this.type = type;
		this.revisions = revisions;
		organizeRevisions();
		this.doAndUndoDone = false;
	}
	
	public void organizeRevisions() {
		Collections.sort(this.revisions);
		
		Set<Task> oldTasks = new HashSet<Task>(1);
		oldTasks.add(this);
		this.oldRevision = new Revision(revisions.get(0).getId()-1,oldTasks,new HashSet<Task>(1));
		
		Set<Task> currentTasks = new HashSet<Task>(1);
		currentTasks.add(this);
		this.currentRevision = revisions.get(revisions.size()-1);
		if(currentRevision.getCurrentTasks() != null)
			currentRevision.setCurrentTasks(currentTasks);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Revision> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public Revision getOldRevision() {
		return oldRevision;
	}

	public void setOldRevision(Revision oldRevision) {
		this.oldRevision = oldRevision;
	}

	public Revision getCurrentRevision() {
		return currentRevision;
	}

	public void setCurrentRevision(Revision currentRevision) {
		this.currentRevision = currentRevision;
	}

	public Set<TestCoverageGroup> getOldSelectionGroup() {
		return oldSelectionGroup;
	}

	public void setOldSelectionGroup(Set<TestCoverageGroup> oldSelectionGroup) {
		this.oldSelectionGroup = oldSelectionGroup;
	}

	public Set<TestCoverageGroup> getOldExclusionGroup() {
		return oldExclusionGroup;
	}

	public void setOldExclusionGroup(Set<TestCoverageGroup> oldExclusionGroup) {
		this.oldExclusionGroup = oldExclusionGroup;
	}

	public Set<TestCoverageGroup> getCurrentSelectionGroup() {
		return currentSelectionGroup;
	}

	public void setCurrentSelectionGroup(Set<TestCoverageGroup> currentSelectionGroup) {
		this.currentSelectionGroup = currentSelectionGroup;
	}

	public Set<TestCoverageGroup> getCurrentExclusionGroup() {
		return currentExclusionGroup;
	}

	public void setCurrentExclusionGroup(Set<TestCoverageGroup> currentExclusionGroup) {
		this.currentExclusionGroup = currentExclusionGroup;
	}

	public Set<String> getModifiedMethods() {
		return modifiedMethods;
	}

	public void setModifiedMethods(Set<String> modifiedMethods) {
		this.modifiedMethods = modifiedMethods;
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

	public boolean isDoAndUndoDone() {
		return doAndUndoDone;
	}

	public void setDoAndUndoDone(boolean doAndUndoDone) {
		this.doAndUndoDone = doAndUndoDone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int compareTo(Task other) {
        return getId().compareTo(other.getId());
    }
	
	public String print() {
		String print = "Task Number: "+this.id+"\n";
		if(!this.type.equals(TaskType.OTHER))
			print += "Task Type: "+this.type.getName()+"\n";
		else
			print += "*Task Type: "+this.otherType+"\n";
		for(Revision revision : this.revisions) {
			print += "\tRevision Number: "+revision.getId()+"\n";
		}
		return print;
	}

}
