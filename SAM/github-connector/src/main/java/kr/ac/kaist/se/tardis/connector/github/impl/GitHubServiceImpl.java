package kr.ac.kaist.se.tardis.connector.github.impl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.connector.github.api.GitHubService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;

@Service
public class GitHubServiceImpl implements GitHubService {
	
	@Autowired
	private TaskService taskService;

	@Override
	public void checkCommits(URL url, ProjectId projectId) {
		GitHubClient client = GitHubClient.createClient(url.toString());
		RepositoryService repositoryService = new RepositoryService(client);
		Repository repository = null;
		try {
			repository = repositoryService.getRepository(RepositoryId.createFromUrl(url));
			CommitService commitService = new CommitService(client);
			List<RepositoryCommit> commits = commitService.getCommits(repository);
			Set<Task> taskByProjectId = taskService.findTaskByProjectId(projectId);
			List<String> taskNames = taskByProjectId.stream().map(t -> t.getName()).collect(Collectors.toList());
			for (String name : taskNames) {
				for (RepositoryCommit repositoryCommit : commits) {
					if(repositoryCommit.getCommit().getMessage().contains(name)){
						//TODO check if that commit is new for the task, e.g. by the date of the last contirbution
						//TODO if new, create a new contribution
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
