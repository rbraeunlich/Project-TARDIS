package kr.ac.kaist.se.tardis.task.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.TestConfig;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class TaskServiceImplTest {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProjectService projectService;
	
	@After
	public void tearDown() {
		Set<Task> allTasks = taskService.getAllTasks();
		for (Task task : allTasks) {
			taskService.deleteTask(task);
		}
	}

	@Test
	public void createTask() {
		Task task = taskService.createTask("me", mock(Project.class));
		assertThat(task, is(notNullValue()));
	}

	@Test
	public void findExistingTaskByName() {
		Task task = taskService.createTask("me", mock(Project.class));
		String name = "foo";
		task.setName(name);
		taskService.saveTask(task);
		Set<Task> taskByName = taskService.findTaskByName(name);
		assertThat(taskByName, contains(task));
	}

	@Test
	public void findExistingTasksByName() {
		String name = "bar";
		Task task = taskService.createTask("me", mock(Project.class));
		task.setName(name);
		taskService.saveTask(task);
		Task task2 = taskService.createTask("me", mock(Project.class));
		task2.setName(name);
		taskService.saveTask(task2);
		Set<Task> taskByName = taskService.findTaskByName(name);
		assertThat(taskByName, hasItems(task, task2));
	}

	@Test
	public void findNonExistingTaskByName() {
		Set<Task> taskByName = taskService.findTaskByName("foo1");
		assertThat(taskByName, is(empty()));
	}

	@Test
	public void findExistingTaskById() {
		Task task = taskService.createTask("me", mock(Project.class));
		taskService.saveTask(task);
		Optional<Task> taskById = taskService.findTaskById(task.getId());
		assertThat(taskById.get(), is(task));
	}

	@Test
	public void findNonExistingTaskById() {
		Optional<Task> projectById = taskService.findTaskById(TaskIdFactory.generateTaskId());
		assertThat(projectById.isPresent(), is(false));
	}

	@Test
	public void safeTask() {
		Task task = taskService.createTask("me", mock(Project.class));
		taskService.saveTask(task);
		Optional<Task> projectById = taskService.findTaskById(task.getId());
		assertThat(projectById.get(), is(task));
	}

	@Test
	public void findExistingTaskForUser() {
		String user = "me";
		Task task = taskService.createTask("me", mock(Project.class));
		taskService.saveTask(task);
		Set<Task> tasksForUser = taskService.findTasksForUser(user);
		assertThat(tasksForUser, contains(task));
	}

	@Test
	public void findExistingTaskForTwoUsers() {
		String user = "me";
		String user2 = "you";

		Task task = taskService.createTask(user, mock(Project.class));
		taskService.saveTask(task);
		Task task2 = taskService.createTask(user2, mock(Project.class));
		taskService.saveTask(task2);

		assertThat(taskService.findTasksForUser(user), contains(task));
		assertThat(taskService.findTasksForUser(user2), contains(task2));
	}

	@Test
	public void deleteSavedTask() {
		Task task = taskService.createTask("me", mock(Project.class));
		taskService.saveTask(task);
		taskService.deleteTask(task);
		Optional<Task> taskById = taskService.findTaskById(task.getId());
		assertThat(taskById.isPresent(), is(false));
	}

	@Test
	public void deleteNotSavedTask() {
		Task task = taskService.createTask("me", mock(Project.class));
		taskService.deleteTask(task);
		// just no error is fine
	}

	@Test
	public void findExistingTaskByProjectId() {
		ProjectId pid = ProjectIdFactory.generateProjectId();
		Project project = mock(ProjectImpl.class);
		when(project.getId()).thenReturn(pid);
		projectService.saveProject(project);
		Task task = taskService.createTask("me", project);
		taskService.saveTask(task);
		Set<Task> tasksByProjectId = taskService.findTaskByProjectId(pid);
		assertThat(tasksByProjectId, contains(task));
	}

	@Test
	public void findNonExistingTaskByProjectId() {
		Set<Task> taskByName = taskService.findTaskByProjectId(ProjectIdFactory.generateProjectId());
		assertThat(taskByName, is(empty()));
	}

}
