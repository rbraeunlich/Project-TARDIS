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

	public static final String KEY_PROJECT_ID = "id";
	public static final String KEY_GITHUB_URL = "url";

	@Autowired
	private GitHubService githubService;

	private String url;

	private String id;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		URL gitHubUrl;
		try {
			gitHubUrl = new URL(getUrl());
			addNotificationToGitHub(ProjectIdFactory.valueOf(getId()), gitHubUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private void addNotificationToGitHub(ProjectId projectId, URL gitHubUrl) {
		githubService.checkCommits(gitHubUrl, projectId);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
