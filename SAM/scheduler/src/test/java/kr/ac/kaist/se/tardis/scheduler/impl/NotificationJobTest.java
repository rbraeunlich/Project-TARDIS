package kr.ac.kaist.se.tardis.scheduler.impl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NotificationJobTest.TestConfig.class)
public class NotificationJobTest {

	private static final String TASK_ID = TaskIdFactory.generateTaskId().getId();

	private static final String PROJECT_ID = ProjectIdFactory.generateProjectId().getId();

	private static final String TASK_OWNER = "Johnny";

	private static final String TASK_NAME = "Task";

	private static final String PROJECT_NAME = "Project";

	private static final Date DUE_DATE = new Date();

	private static final String PROJECT_OWNER = "Boss";

	private static final String PROJECT_MEMBER_1 = "Bob";

	private static final String PROJECT_MEMBER_2 = "Dave";

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private NotificationJob notificationJob;

	@Test
	public void jobShouldCreateNotificationForTaskOwner() throws JobExecutionException {
		JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
		when(jobExecutionContext.getScheduler()).thenReturn(scheduler);
		notificationJob.setId(TASK_ID);
		notificationJob.setType(NotificationJob.VALUE_TASK);
		notificationJob.setDueDate(DUE_DATE.getTime());
		notificationJob.execute(jobExecutionContext);

		verify(TestConfig.notificationService, atLeastOnce()).createNotification(TestConfig.usernameCaptor.capture(),
				TestConfig.textCaptor.capture(), TestConfig.dateCaptor.capture());

		assertThat(TestConfig.usernameCaptor.getValue(), is(TASK_OWNER));
		assertThat(TestConfig.textCaptor.getValue(),
				is("Task " + TASK_NAME + " from project " + PROJECT_NAME + " is due on " + DUE_DATE));
		assertThat(TestConfig.dateCaptor.getValue(), is(notNullValue()));
	}

	@Test
	public void jobShouldCreateNotificationForAllProjectMembers() throws JobExecutionException {
		JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
		notificationJob.setId(PROJECT_ID);
		notificationJob.setType(NotificationJob.VALUE_PROJECT);
		notificationJob.setDueDate(DUE_DATE.getTime());
		when(jobExecutionContext.getScheduler()).thenReturn(scheduler);
		notificationJob.execute(jobExecutionContext);

		verify(TestConfig.notificationService, atLeastOnce()).createNotification(TestConfig.usernameCaptor.capture(),
				TestConfig.textCaptor.capture(), TestConfig.dateCaptor.capture());

		assertThat(TestConfig.usernameCaptor.getAllValues(),
				hasItems(PROJECT_OWNER, PROJECT_MEMBER_1, PROJECT_MEMBER_2));
		assertThat(TestConfig.textCaptor.getValue(), is("Project " + PROJECT_NAME + " is due on " + DUE_DATE));
		assertThat(TestConfig.dateCaptor.getValue(), is(notNullValue()));
	}

	@Configuration
	@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { GitHubJob.class,
			StandardNotificationBuilderImplTest.TestConfig.class }))
	public static class TestConfig {

		private static NotificationService notificationService;

		private static ArgumentCaptor<String> usernameCaptor;

		private static ArgumentCaptor<String> textCaptor;

		private static ArgumentCaptor<Date> dateCaptor;

		@Bean
		public ProjectService createMockProjectService() {
			ProjectService projectService = mock(ProjectService.class);
			Project project = mock(Project.class);
			when(projectService.findProjectById(ProjectIdFactory.valueOf(PROJECT_ID))).thenReturn(Optional.of(project));
			when(project.getProjectOwner()).thenReturn(PROJECT_OWNER);
			when(project.getProjectMembers())
					.thenReturn(new HashSet<>(Arrays.asList(PROJECT_MEMBER_1, PROJECT_MEMBER_2)));
			when(project.getName()).thenReturn(PROJECT_NAME);
			return projectService;
		}

		@Bean
		public TaskService createMockTaskService() {
			TaskService taskService = mock(TaskService.class);
			Task taskMock = mock(Task.class);
			when(taskMock.getOwner()).thenReturn(TASK_OWNER);
			when(taskMock.getProjectId()).thenReturn(ProjectIdFactory.valueOf(PROJECT_ID));
			when(taskMock.getName()).thenReturn(TASK_NAME);
			when(taskService.findTaskById(TaskIdFactory.valueOf(TASK_ID))).thenReturn(Optional.of(taskMock));
			return taskService;
		}

		@Bean
		public NotificationService createMockNotificationService() {
			notificationService = mock(NotificationService.class);
			usernameCaptor = ArgumentCaptor.forClass(String.class);
			textCaptor = ArgumentCaptor.forClass(String.class);
			dateCaptor = ArgumentCaptor.forClass(Date.class);
			return notificationService;
		}

		@Bean
		public Scheduler createMockScheduler() {
			Scheduler schedulerMock = mock(Scheduler.class);
			try {
				when(schedulerMock.getContext()).thenReturn(new SchedulerContext());
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			return schedulerMock;
		}
	}

}
