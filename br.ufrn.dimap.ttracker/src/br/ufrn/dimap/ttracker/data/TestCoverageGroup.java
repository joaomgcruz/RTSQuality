package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TestCoverageGroup implements Comparable<TestCoverageGroup>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Set<TestCoverage> testCoverages;
	
	public TestCoverageGroup(Integer id) {
		this.id = id;
		this.testCoverages = new HashSet<TestCoverage>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<TestCoverage> getTestCoverages() {
		return testCoverages;
	}

	public void setTestCoverages(Set<TestCoverage> testCoverages) {
		this.testCoverages = testCoverages;
	}

	@Override
	public int compareTo(TestCoverageGroup o) {
		return id.compareTo(o.getId());
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
		TestCoverageGroup other = (TestCoverageGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
