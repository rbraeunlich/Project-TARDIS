package kr.ac.kaist.se.tardis.task.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskRepository;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository repo;

	@Autowired
	private ProjectService projectService;
	
	@Override
	public Task createTask(String owner, Project project) {
		TaskId taskId = TaskIdFactory.generateTaskId();
		Task t = new TaskImpl(taskId, project, owner);
		return t;
	}

	@Override
	public Set<Task> findTasksForUser(String username) {
		return repo.findTasksByOwner(username);
	}

	@Override
	public Set<Task> findTaskByName(String name) {
		return repo.findTasksByName(name);
	}

	@Override
	public Optional<Task> findTaskById(TaskId id) {
		return Optional.ofNullable(repo.findOne(id));
	}

	@Override
	public Set<Task> findTaskByProjectId(ProjectId id) {
		Project project = projectService.findProjectById(id).get();
		return repo.findTaskByProject((ProjectImpl) project);
	}

	@Override
	public void saveTask(Task t) {
		repo.save((TaskImpl) t);
	}

	@Override
	public void deleteTask(Task t) {
		repo.delete((TaskImpl) t);
	}

	@Override
	public Set<Task> getAllTasks() {
		return new HashSet<>(repo.findAll());
	}

}
