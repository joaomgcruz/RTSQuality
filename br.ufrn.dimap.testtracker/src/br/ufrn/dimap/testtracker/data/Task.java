package br.ufrn.dimap.testtracker.data;

import java.util.List;

public class Task {
	private Integer id;
	private List<Revision> revisions;
	private String type;
	
	public static final String REFACTOR = "REFACTOR";
	public static final String CORRECTION = "CORRECTION";
	public static final String DEVELOPMENT = "DEVELOPMENT";
	
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
