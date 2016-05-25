package kr.ac.kaist.se.tardis.scheduler.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

public class GitHubJob implements Job {

	public static final String KEY_PROJECT_ID = "ID";
	public static final String KEY_GITHUB_URL = "URL"; 
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String id = (String) context.get(KEY_PROJECT_ID);
		URL gitHubUrl;
		try {
			gitHubUrl = new URL((String) context.get(KEY_GITHUB_URL));
			addNotificationToGitHub(ProjectIdFactory.valueOf(id), gitHubUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void addNotificationToGitHub(ProjectId projectId, URL gitHubUrl) {
		// TODO with GitHub Service
	}
	
}
