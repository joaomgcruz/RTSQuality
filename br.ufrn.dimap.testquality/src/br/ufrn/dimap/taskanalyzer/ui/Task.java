package br.ufrn.dimap.taskanalyzer.ui;

import java.util.List;

public class Task {
	private Integer id;
	private List<Revision> revisions;
	private String type;
	
	public Task(Integer id, List<Revision> revisions, String type) {
		this.id = id;
		this.revisions = revisions;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
