package br.ufrn.dimap.rtquality.history;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class SVNConfig {
	private String svnUrl;
	private Map<Integer,Project> projects;
	private String userName;
	private String password;
	
	public SVNConfig(String svnUrl, Map<Integer,Project> projects, String userName, String password) throws Exception {
		this.svnUrl = svnUrl;
		this.projects = projects;
		this.userName = userName;
		this.password = password;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public Map<Integer,Project> getProjects() {
		return projects;
	}

	public void setProjects(Map<Integer,Project> projects) {
		this.projects = projects;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
