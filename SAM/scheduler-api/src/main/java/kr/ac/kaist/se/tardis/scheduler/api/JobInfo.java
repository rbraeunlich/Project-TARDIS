package kr.ac.kaist.se.tardis.scheduler.api;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class JobInfo {

	/**
	 * The id Quartz gave the trigger of this job this info represents;
	 */
	private String triggerId;

	public void setTriggerId(String id) {
		this.triggerId = id;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public abstract JobType getJobType();

	public abstract void setJobType(JobType jobType);
	
	public abstract String getId();
	
	public abstract void setId(String id);
}
