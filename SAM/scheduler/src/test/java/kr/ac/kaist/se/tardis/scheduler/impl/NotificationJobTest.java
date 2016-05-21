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

import kr.ac.kaist.se.tardis.notification.api.NotificationService;
import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NotificationJobTest.TestConfig.class)
public class NotificationJobTest {

	private static final String TASK_ID = TaskIdFactory.generateTaskId()
			.getId();

	private static final String PROJECT_ID = ProjectIdFactory
			.generateProjectId().getId();

	private static final String TASK_OWNER = "Johnny";

	private static final String TASK_NAME = "Task";

	private static final String PROJECT_NAME = "Project";

	private static final Date DUE_DATE = new Date();

	private static final String PROJECT_OWNER = "Boss";

	private static final String PROJECT_MEMBER_1 = "Bob";

	private static final String PROJECT_MEMBER_2 = "Dave";

	@Test
	public void jobShouldCreateNotificationForTaskOwner()
			throws JobExecutionException {
		NotificationJob notificationJob = new NotificationJob();
		JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
		when(jobExecutionContext.get(NotificationJob.KEY_ID)).thenReturn(
				TASK_ID);
		when(jobExecutionContext.get(NotificationJob.KEY_TYPE)).thenReturn(
				NotificationJob.VALUE_TASK);
		when(jobExecutionContext.get(NotificationJob.KEY_DUE_DATE)).thenReturn(
				DUE_DATE.getTime());
		notificationJob.execute(jobExecutionContext);
		
		verify(TestConfig.notificationService, atLeastOnce()).createNotification(TestConfig.usernameCaptor.capture(),
				TestConfig.textCaptor.capture(), TestConfig.dateCaptor.capture());

		assertThat(TestConfig.usernameCaptor.getValue(), is(TASK_OWNER));
		assertThat(TestConfig.textCaptor.getValue(), is("Task " + TASK_NAME
				+ " from project " + PROJECT_NAME + " is due on " + DUE_DATE));
		assertThat(TestConfig.dateCaptor.getValue(), is(notNullValue()));
	}

	@Test
	public void jobShouldCreateNotificationForAllProjectMembers()
			throws JobExecutionException {
		NotificationJob notificationJob = new NotificationJob();
		JobExecutionContext jobExecutionContext = mock(JobExecutionContext.class);
		when(jobExecutionContext.get(NotificationJob.KEY_ID)).thenReturn(
				PROJECT_ID);
		when(jobExecutionContext.get(NotificationJob.KEY_TYPE)).thenReturn(
				NotificationJob.VALUE_PROJECT);
		when(jobExecutionContext.get(NotificationJob.KEY_DUE_DATE)).thenReturn(
				DUE_DATE.getTime());
		notificationJob.execute(jobExecutionContext);

		verify(TestConfig.notificationService, atLeastOnce()).createNotification(TestConfig.usernameCaptor.capture(),
				TestConfig.textCaptor.capture(), TestConfig.dateCaptor.capture());
		
		assertThat(TestConfig.usernameCaptor.getAllValues(),
				hasItems(PROJECT_OWNER, PROJECT_MEMBER_1, PROJECT_MEMBER_2));
		assertThat(TestConfig.textCaptor.getValue(), is("Project "
				+ PROJECT_NAME + " is due on " + DUE_DATE));
		assertThat(TestConfig.dateCaptor.getValue(), is(notNullValue()));
	}

	@Configuration
	@ComponentScan
	public static class TestConfig {

		private static NotificationService notificationService;

		@Autowired
		private ApplicationContext appContext;
		
		private static ArgumentCaptor<String> usernameCaptor;

		private static ArgumentCaptor<String> textCaptor;

		private static ArgumentCaptor<Date> dateCaptor;

		@Bean
		public ProjectService createMockProjectService() {
			ProjectService projectService = mock(ProjectService.class);
			Project project = mock(Project.class);
			when(
					projectService.findProjectById(ProjectIdFactory
							.valueOf(PROJECT_ID))).thenReturn(
					Optional.of(project));
			when(project.getProjectOwner()).thenReturn(PROJECT_OWNER);
			when(project.getProjectMembers()).thenReturn(
					new HashSet<>(Arrays.asList(PROJECT_MEMBER_1,
							PROJECT_MEMBER_2)));
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
			when(taskService.findTaskById(TaskIdFactory.valueOf(TASK_ID)))
					.thenReturn(Optional.of(taskMock));
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
		public ApplicationContextProvider createAppContextProvider(){
			ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
			applicationContextProvider.setApplicationContext(appContext);
			return applicationContextProvider;
		}
	}

}
