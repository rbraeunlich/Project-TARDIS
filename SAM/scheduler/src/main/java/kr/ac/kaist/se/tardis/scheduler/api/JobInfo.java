package kr.ac.kaist.se.tardis.scheduler.api;

public class JobInfo {

	private String taskId;
	private String projectId;
	private JobType jobType;

	public String getId() {
		return taskId == null ? projectId : taskId;
	}

	public JobType getJobType() {
		return jobType;
	}

	public void setTaskId(String id) {
		this.taskId = id;
	}

	public void setProjectId(String id) {
		this.projectId = id;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

}
