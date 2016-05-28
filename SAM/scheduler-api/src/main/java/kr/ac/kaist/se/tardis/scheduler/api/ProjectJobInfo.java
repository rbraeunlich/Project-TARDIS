package kr.ac.kaist.se.tardis.scheduler.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ProjectJobInfoId.class)
public class ProjectJobInfo extends JobInfo {
	
	@Id
	@Column(name = "projectid", nullable = true)
	private String projectId;

	@Id
	@Enumerated(EnumType.STRING)
	private JobType jobType;
	
	private String githubUrl;
	
	public ProjectJobInfo(){}

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	@Override
	public String getId() {
		return projectId;
	}

	@Override
	public void setId(String id) {
		this.projectId = id;
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
		ProjectJobInfo other = (ProjectJobInfo) obj;
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
