package kr.ac.kaist.se.tardis.scheduler.api;

import java.util.Set;

public interface JobOwner {
	
	Set<JobInfo> getAllJobInfos();
	
	void removeJobInfo(JobInfo jobInfo);
	
	void addJobInfo(JobInfo jobInfo);

}
