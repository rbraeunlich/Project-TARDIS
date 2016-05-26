package kr.ac.kaist.se.tardis.scheduler.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import kr.ac.kaist.se.tardis.connector.github.api.GitHubService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

@Component
public class GitHubJob extends QuartzJobBean {

	public static final String KEY_PROJECT_ID = "ID";
	public static final String KEY_GITHUB_URL = "URL";

	@Autowired
	private GitHubService githubService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
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
		githubService.checkCommits(gitHubUrl, projectId);
	}

}
