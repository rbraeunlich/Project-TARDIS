package kr.ac.kaist.se.tardis.connector.github.api;

import java.net.URL;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;

public interface GitHubService {

	/**
	 * Checks if there are commits that mention a task from the given project.
	 * If such a commit is found, a contribution for that task will be created.
	 * @param url
	 * @param projectId
	 */
	void checkCommits(URL url, ProjectId projectId);
	
}
