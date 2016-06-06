package kr.ac.kaist.se.tardis.scheduler.api;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class ProjectJobInfoId implements Serializable{
	
	private static final long serialVersionUID = 185640165277487453L;

	@Column(name = "projectid")
	private String projectId;
	
	@Enumerated(EnumType.STRING)
	private JobType jobType;

	public ProjectJobInfoId(String projectId, JobType jobType) {
		this.projectId = projectId;
		this.jobType = jobType;
	}

	public ProjectJobInfoId() {
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
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
		ProjectJobInfoId other = (ProjectJobInfoId) obj;
		if (jobType != other.jobType)
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		return true;
	}

}

