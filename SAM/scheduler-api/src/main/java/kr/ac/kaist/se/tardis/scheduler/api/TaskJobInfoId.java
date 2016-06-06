package kr.ac.kaist.se.tardis.scheduler.api;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TaskJobInfoId implements Serializable {

	private static final long serialVersionUID = 185640165277487453L;

	@Column(name = "taskid", nullable = true)
	private String taskId;

	@Enumerated(EnumType.STRING)
	private JobType jobType;

	public TaskJobInfoId() {
		//default constructor for JPA
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
		TaskJobInfoId other = (TaskJobInfoId) obj;
		if (jobType != other.jobType)
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

}
