package kr.ac.kaist.se.tardis.project.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;

@Entity(name="project")
@IdClass(ProjectId.class)
public class ProjectImpl implements Project {

	@Id
	private String id;
	private String name;
	private String description;
	private String owner;
	
	@ElementCollection
	@CollectionTable(name="projectmember", joinColumns=@JoinColumn(name="projectid"))
	private Set<String> members;
	
	@Temporal(TemporalType.DATE)
	private Date dueDate;
	
	@OneToMany(targetEntity=JobInfo.class)
	@JoinColumn(name="projectid", referencedColumnName="id")
	private Set<JobInfo> jobInfos = new HashSet<>();

	ProjectImpl(){}
	
	ProjectImpl(ProjectId id, String projectOwner) {
		this.id = id.toString();
		this.owner = projectOwner;
		this.members = new HashSet<>();
		addProjectMember(owner);
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
		return ProjectIdFactory.valueOf(id);
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
	public Set<JobInfo> getAllJobInfos() {
		return jobInfos;
	}

	@Override
	public void removeJobInfo(JobInfo jobInfo) {
		jobInfos.remove(jobInfo);
	}

	@Override
	public void addJobInfo(JobInfo jobInfo) {
		jobInfos.add(jobInfo);
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
