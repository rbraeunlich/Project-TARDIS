package kr.ac.kaist.se.tardis.connector.github.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.CommitUser;
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
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteService;

@Service
public class GitHubServiceImpl implements GitHubService {

	private static final String PREFIX = "SAM";

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskNoteService taskNoteService;

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
			Map<Task, String> taskKeywords = taskByProjectId.stream()
					.collect(Collectors.toMap(t -> t, t -> PREFIX + t.getKey()));
			for (Entry<Task, String> entry : taskKeywords.entrySet()) {
				for (RepositoryCommit repositoryCommit : commits) {
					Commit commit = repositoryCommit.getCommit();
					if (commit.getMessage().contains(entry.getValue())) {
						Date dateOfLatestContribution = getLatestAutomaticContribution(entry.getKey());
						CommitUser author = commit.getAuthor();
						if (dateOfLatestContribution != null && author.getDate().after(dateOfLatestContribution)) {
							taskNoteService.createContribution(entry.getKey(),
									repositoryCommit.getCommitter().getName(), author.getDate(), null, null);
						}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Date getLatestAutomaticContribution(Task task) {
		Set<TaskNote> taskNotesByTaskId = taskNoteService.findTaskNotesByTaskId(task.getId());
		Optional<Date> first = taskNotesByTaskId.stream().map(tn -> tn.getWriteDate())
				.sorted((d1, d2) -> -d1.compareTo(d2)).findFirst();
		if (first.isPresent()) {
			return first.get();
		}
		return null;
	}
}
