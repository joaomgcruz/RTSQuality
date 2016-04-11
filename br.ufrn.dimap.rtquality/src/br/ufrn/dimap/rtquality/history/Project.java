package br.ufrn.dimap.rtquality.history;

import java.io.Serializable;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import br.ufrn.dimap.ttracker.data.Task;

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String name;
	private IProject iProject;
	private boolean aspectJNature;
	private boolean executeTests;
	private boolean forCheckout;
	private Set<String> packagesToTest;
	private ProjectRevisionInformations projectRevisionInformations;

	public Project(String projectPath, String projectName, IProject iProject, boolean aspectJNature, boolean forCheckout) {
		this.path = projectPath;
		this.name = projectName;
		this.iProject = iProject;
		this.aspectJNature = aspectJNature;
		this.executeTests = false;
		this.forCheckout = forCheckout;
		this.packagesToTest = null;
		this.projectRevisionInformations = new ProjectRevisionInformations();
	}

	public Project(String projectPath, String projectName, IProject iProject, boolean aspectJNature, boolean forCheckout, Set<String> packagesToTest) throws Exception {
		this.path = projectPath;
		this.name = projectName;
		this.iProject = iProject;
		this.aspectJNature = aspectJNature;
		if(packagesToTest == null || packagesToTest.isEmpty())
			throw new Exception("The packagesToTest can not be null neither empty.");
		this.executeTests = true;
		this.forCheckout = forCheckout;
		this.packagesToTest = packagesToTest;
		this.projectRevisionInformations = new ProjectRevisionInformations();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String projectPath) {
		this.path = projectPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String projectName) {
		this.name = projectName;
	}

	public IProject getIProject() {
		return iProject;
	}

	public void setIProject(IProject iProject) {
		this.iProject = iProject;
	}

	public boolean isAspectJNature() {
		return aspectJNature;
	}

	public void setAspectJNature(boolean aspectJNature) {
		this.aspectJNature = aspectJNature;
	}

	public boolean isExecuteTests() {
		return executeTests;
	}

	public void setExecuteTests(boolean executeTests) {
		this.executeTests = executeTests;
	}

	public boolean isForCheckout() {
		return forCheckout;
	}

	public void setForCheckout(boolean forCheckout) {
		this.forCheckout = forCheckout;
	}

	public Set<String> getPackagesToTest() {
		return packagesToTest;
	}

	public ProjectRevisionInformations getProjectRevisionInformations() {
		return projectRevisionInformations;
	}

	public void setProjectRevisionInformations(
			ProjectRevisionInformations projectRevisionInformations) {
		this.projectRevisionInformations = projectRevisionInformations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((path == null) ? 0 : path.hashCode());
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
		Project other = (Project) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	public int compareTo(Project other) {
        return path.compareTo(other.path);
    }
	
}
