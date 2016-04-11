package br.ufrn.dimap.rtquality.history;

import java.io.Serializable;

public class ProjectRevisionInformations implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer revision;
	private Integer revisionBuilded;
	
	public ProjectRevisionInformations() {
		this.revision = 0;
		this.revisionBuilded = 0;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public Integer getRevisionBuilded() {
		return revisionBuilded;
	}

	public void setRevisionBuilded(Integer revisionBuilded) {
		this.revisionBuilded = revisionBuilded;
	}
	
}
