package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;

public class MethodState implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean covered;
	private boolean modified;

	public MethodState() {
		this.covered = false;
		this.modified = false;
	}
	
	public MethodState(boolean modified, boolean covered) {
		this.covered = covered;
		this.modified = modified;
	}

	public boolean isCovered() {
		return covered;
	}
	
	public void setCovered(boolean covered) {
		this.covered = covered;
	}
	
	public boolean isModified() {
		return modified;
	}
	
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (covered ? 1231 : 1237);
		result = prime * result + (modified ? 1231 : 1237);
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
		MethodState other = (MethodState) obj;
		if (covered != other.covered)
			return false;
		if (modified != other.modified)
			return false;
		return true;
	}
	
}
