package kr.ac.kaist.se.tardis.project.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

class ProjectImpl implements Project {

	private final ProjectId id;
	private String name;
	private String description;
	private final String owner;
	private final Set<String> members;
	private Date dueDate;

	public ProjectImpl(ProjectId id, String projectOwner) {
		this.id = id;
		this.owner = projectOwner;
		this.members = new HashSet<>();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDescription(String des) {
		this.description = des;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public ProjectId getId() {
		return id;
	}

	@Override
	public Date getDueDate() {
		return dueDate;
	}

	@Override
	public void setDueDate(Date d) {
		this.dueDate = d;
	}

	@Override
	public void addProjectMember(String member) {
		members.add(member);
	}

	@Override
	public void removeProjectMember(String member) {
		members.remove(member);
	}

	@Override
	public Set<String> getProjectMembers() {
		return members;
	}

	@Override
	public String getProjectOwner() {
		return owner;
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
		ProjectImpl other = (ProjectImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectImpl [id=" + id + ", name=" + name + ", owner=" + owner + ", members=" + members + ", dueDate="
				+ dueDate + "]";
	}
	
}
