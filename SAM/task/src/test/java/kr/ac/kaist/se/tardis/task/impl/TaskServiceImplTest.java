package kr.ac.kaist.se.tardis.task.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;

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

	@After
	public void tearDown() {
		Set<Task> allTasks = taskService.getAllTasks();
		for (Task task : allTasks) {
			taskService.deleteTask(task);
		}
	}

	@Test
	public void createTask() {
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		assertThat(task, is(notNullValue()));
	}

	@Test
	public void findExistingTaskByName() {
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		String name = "foo";
		task.setName(name);
		taskService.saveTask(task);
		Set<Task> taskByName = taskService.findTaskByName(name);
		assertThat(taskByName, contains(task));
	}

	@Test
	public void findExistingTasksByName() {
		String name = "bar";
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		task.setName(name);
		taskService.saveTask(task);
		Task task2 = taskService.createTask("me",ProjectIdFactory.generateProjectId());
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
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		taskService.saveTask(task);
		Optional<Task> taskById = taskService.findTaskById(task
				.getId());
		assertThat(taskById.get(), is(task));
	}

	@Test
	public void findNonExistingTaskById() {
		Optional<Task> projectById = taskService
				.findTaskById(TaskIdFactory.generateTaskId());
		assertThat(projectById.isPresent(), is(false));
	}

	@Test
	public void safeTask() {
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		taskService.saveTask(task);
		// FIXME we should look into the database here, so we should modify the
		// test after persistence is complete
		Optional<Task> projectById = taskService.findTaskById(task
				.getId());
		assertThat(projectById.get(), is(task));
	}

	@Test
	public void findExistingTaskForUser() {
		String user = "me";
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		taskService.saveTask(task);
		Set<Task> tasksForUser = taskService.findTasksForUser(user);
		assertThat(tasksForUser, contains(task));
	}

	@Test
	public void findExistingTaskForTwoUsers() {
		String user = "me";
		String user2 = "you";
		
		Task task = taskService.createTask(user,ProjectIdFactory.generateProjectId());
		taskService.saveTask(task);
		Task task2 = taskService.createTask(user2,ProjectIdFactory.generateProjectId());
		taskService.saveTask(task2);
		
		assertThat(taskService.findTasksForUser(user), contains(task));
		assertThat(taskService.findTasksForUser(user2),
				contains(task2));
	}

	@Test
	public void deleteSavedTask() {
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		taskService.saveTask(task);
		taskService.deleteTask(task);
		Optional<Task> taskById = taskService.findTaskById(task
				.getId());
		assertThat(taskById.isPresent(), is(false));
	}

	@Test
	public void deleteNotSavedTask() {
		Task task = taskService.createTask("me",ProjectIdFactory.generateProjectId());
		taskService.deleteTask(task);
		// just no error is fine
	}
	@Test
	public void findExistingTaskByProjectId() {
		ProjectId pid = ProjectIdFactory.generateProjectId();
		Task task = taskService.createTask("me",pid);
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
