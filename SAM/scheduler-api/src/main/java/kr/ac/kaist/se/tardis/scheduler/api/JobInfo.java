package kr.ac.kaist.se.tardis.scheduler.api;

public class JobInfo {

	private String taskId;
	private String projectId;
	private JobType jobType;
	private String githubUrl;
	/**
	 * The id Quartz gave the trigger of this job this info represents;
	 */
	private String triggerId;

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

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public void setTriggerId(String id){
		this.triggerId = id;
	}
	
	public String getTriggerId(){
		return triggerId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobType == null) ? 0 : jobType.hashCode());
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
		JobInfo other = (JobInfo) obj;
		if (jobType != other.jobType)
			return false;
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
