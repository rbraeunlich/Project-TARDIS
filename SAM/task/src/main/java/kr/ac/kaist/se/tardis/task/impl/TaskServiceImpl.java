package kr.ac.kaist.se.tardis.task.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

@Service
public class TaskServiceImpl implements TaskService {

	// FIXME replace this set with a DTO later
	private Set<Task> tasks = new HashSet<>();

	@Override
	public Task createTask(String owner,ProjectId projectId) {
		TaskId taskId = TaskIdFactory.generateTaskId();
		Task t = new TaskImpl(taskId, projectId, owner);
		return t;
	}


	
	@Override
	public Set<Task> findTasksForUser(String userId) {
		return tasks
				.stream()
				.filter(p -> ( p.getOwner().equals(userId) )  )
				.collect(Collectors.toSet());
	}

	@Override
	public Set<Task> findTaskByName(String name) {
		return tasks.stream().filter(p -> name.equals(p.getName()))
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<Task> findTaskById(TaskId id) {
		return tasks.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	@Override
	public Set<Task> findTaskByProjectId(ProjectId id) {
		return tasks.stream().filter(p -> id.equals(p.getProjectId()))
				.collect(Collectors.toSet());
	}

	@Override
	public void saveTask(Task p) {
		tasks.add(p);
	}

	@Override
	public void deleteTask(Task p) {
		tasks.remove(p);
		
	}

	@Override
	public Set<Task> getAllTasks() {
		return new HashSet<>(tasks);
	}





}
