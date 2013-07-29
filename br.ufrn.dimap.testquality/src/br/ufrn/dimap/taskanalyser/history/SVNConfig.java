package br.ufrn.dimap.taskanalyser.history;

public class SVNConfig {
	private String svnUrl;
	private String projectPath;
	private String userName;
	private String password;
	
	public SVNConfig(String svnUrl, String projectPath, String userName, String password) {
		this.svnUrl = svnUrl;
		this.projectPath = projectPath;
		this.userName = userName;
		this.password = password;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
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
