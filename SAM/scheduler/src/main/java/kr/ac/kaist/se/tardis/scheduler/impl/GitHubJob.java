package kr.ac.kaist.se.tardis.scheduler.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GitHubJob implements Job {

	public static final String KEY_PROJECT_ID = "ID";
	public static final String KEY_GITHUB_URL = "URL"; 
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub

	}

}
