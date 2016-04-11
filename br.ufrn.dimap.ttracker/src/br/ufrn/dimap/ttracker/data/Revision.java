package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Revision implements Serializable, Comparable<Revision> {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Revision oldRevision;
	private Set<Task> oldTasks;
	private Set<Task> currentTasks;
	private Set<String> modifiedMethods;
	private Set<String> UndoModifiedMethods;
	private boolean doAndUndoDone;
	private Float inclusion;
	private Float precision;
	private Float MC;
	private Float MnC;
	private Float nMC;
	private Float nMnC;
	
	public Revision() {
		this.oldRevision = null;
		this.oldTasks = new HashSet<Task>(0);
		this.currentTasks = new HashSet<Task>(0);
		this.modifiedMethods = new HashSet<String>(0);
		this.UndoModifiedMethods = new HashSet<String>(0);
		this.doAndUndoDone = false;
	}
	
	public Revision(Integer id) {
		this.id = id;
		this.oldRevision = null;
		this.oldTasks = new HashSet<Task>(0);
		this.currentTasks = new HashSet<Task>(0);
		this.doAndUndoDone = false;
	}
	
	public Revision(Integer id,Set<Task> oldTasks, Set<Task> currentTasks) {
		this.id = id;
		this.oldRevision = null;
		this.oldTasks = oldTasks;
		this.currentTasks = currentTasks;
		this.doAndUndoDone = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Float getMC() {
		return MC;
	}

	public void setMC(Float mC) {
		MC = mC;
	}

	public Float getMnC() {
		return MnC;
	}

	public void setMnC(Float mnC) {
		MnC = mnC;
	}

	public Float getnMC() {
		return nMC;
	}

	public void setnMC(Float nMC) {
		this.nMC = nMC;
	}

	public Float getnMnC() {
		return nMnC;
	}

	public void setnMnC(Float nMnC) {
		this.nMnC = nMnC;
	}

	public Revision getOldRevision() {
		return oldRevision;
	}

	public void setOldRevision(Revision oldRevision) {
		this.oldRevision = oldRevision;
	}

	public Set<Task> getOldTasks() {
		return oldTasks;
	}

	public void setOldTasks(Set<Task> oldTasks) {
		this.oldTasks = oldTasks;
	}

	public Set<Task> getCurrentTasks() {
		return currentTasks;
	}

	public void setCurrentTasks(Set<Task> currentTasks) {
		this.currentTasks = currentTasks;
	}

	public Set<String> getModifiedMethods() {
		return modifiedMethods;
	}

	public void setModifiedMethods(Set<String> modifiedMethods) {
		this.modifiedMethods = modifiedMethods;
	}

	public Set<String> getUndoModifiedMethods() {
		return UndoModifiedMethods;
	}

	public void setUndoModifiedMethods(Set<String> undoModifiedMethods) {
		UndoModifiedMethods = undoModifiedMethods;
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
		Revision other = (Revision) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

    public int compareTo(Revision other) {
        return getId().compareTo(other.getId());
    }

}
