package kr.ac.kaist.se.tardis.scheduler.api;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class JobInfoId implements Serializable{
	
	private static final long serialVersionUID = 185640165277487453L;

	@Column(name = "taskid", nullable = true)
	private String taskId;

	@Column(name = "projectid", nullable = true)
	private String projectId;

	public JobInfoId(String taskId, String projectId) {
		this.taskId = taskId;
		this.projectId = projectId;
	}

	public JobInfoId() {
	}

	public String getTaskId() {
		return taskId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		JobInfoId other = (JobInfoId) obj;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

}

