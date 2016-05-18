package kr.ac.kaist.se.tardis.users.impl.security;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.Task;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SamUserDetailsServiceTest.TestConfiguration.class)
public class SamUserDetailsServiceTest {

	private static final String USERNAME_OWNER = "foo";

	private static final UserImpl PROJECT_OWNER = new UserImpl(USERNAME_OWNER, USERNAME_OWNER);

	private static final String USERNAME_MEMBER = "member";

	private static final UserImpl PROJECT_MEMBER = new UserImpl(USERNAME_MEMBER, USERNAME_MEMBER);

	private static final String USERNAME_NO_PROJECT = "bar";

	private static final ProjectId PROJECT_ID = ProjectIdFactory.generateProjectId();

	private static final TaskId TASK_ID = TaskIdFactory.generateTaskId();

	@Autowired
	private SamUserDetailsService detailsService;

	@Test
	public void loadExistingUserByUsername() {
		UserDetails userDetails = detailsService.loadUserByUsername(USERNAME_OWNER);
		assertThat(userDetails.getUsername(), is(USERNAME_OWNER));
		String password = userDetails.getPassword();
		assertThat(new BCryptPasswordEncoder().matches(USERNAME_OWNER, password), is(true));
		assertThat(userDetails.getAuthorities(), is(empty()));
		assertThat(userDetails.isAccountNonExpired(), is(true));
		assertThat(userDetails.isAccountNonLocked(), is(true));
		assertThat(userDetails.isCredentialsNonExpired(), is(true));
		assertThat(userDetails.isEnabled(), is(true));
	}

	@Test
	public void loadNonExistingUserByUsername() {
		UserDetails userDetails = detailsService.loadUserByUsername("bar");
		assertThat(userDetails.getUsername(), is("unknown"));
		assertThat(userDetails.getPassword(), is(""));
		assertThat(userDetails.getAuthorities(), is(empty()));
		assertThat(userDetails.isAccountNonExpired(), is(false));
		assertThat(userDetails.isAccountNonLocked(), is(false));
		assertThat(userDetails.isCredentialsNonExpired(), is(false));
		assertThat(userDetails.isEnabled(), is(false));
	}

	@Test
	public void unauthorizedUserIsAllowedNothing() {
		Authentication authentication = new TestingAuthenticationToken(null, null);
		authentication.setAuthenticated(false);
		HttpServletRequest request = mock(HttpServletRequest.class);
		boolean hasUserTask = detailsService.hasUserTask(authentication, request);
		assertThat(hasUserTask, is(false));
		boolean userAllowedToChangeTask = detailsService.isUserAllowedToChangeTask(authentication, request);
		assertThat(userAllowedToChangeTask, is(false));
		boolean userProjectMember = detailsService.isUserProjectMember(authentication, request);
		assertThat(userProjectMember, is(false));
		boolean userProjectOwner = detailsService.isUserProjectOwner(authentication, request);
		assertThat(userProjectOwner, is(false));
	}

	@Test
	public void userIsProjectOwner() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_OWNER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("projectId")).thenReturn(PROJECT_ID.getId());
		boolean userProjectOwner = detailsService.isUserProjectOwner(authentication, request);
		assertThat(userProjectOwner, is(true));
	}

	@Test
	public void userIsNotProjectOwner() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_MEMBER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("projectId")).thenReturn(PROJECT_ID.getId());
		boolean userProjectOwner = detailsService.isUserProjectOwner(authentication, request);
		assertThat(userProjectOwner, is(false));
	}

	@Test
	public void userIsProjectMember() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_MEMBER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("projectId")).thenReturn(PROJECT_ID.getId());
		boolean userProjectMember = detailsService.isUserProjectMember(authentication, request);
		assertThat(userProjectMember, is(true));
	}

	@Test
	public void userIsNotProjectMember() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_NO_PROJECT, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("projectId")).thenReturn(PROJECT_ID.getId());
		boolean userProjectMember = detailsService.isUserProjectMember(authentication, request);
		assertThat(userProjectMember, is(false));
	}

	@Test
	public void projectOwnerIsProjectMember() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_OWNER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("projectId")).thenReturn(PROJECT_ID.getId());
		boolean userProjectMember = detailsService.isUserProjectMember(authentication, request);
		assertThat(userProjectMember, is(true));
	}

	@Test
	public void userHasTask() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_MEMBER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("taskId")).thenReturn(TASK_ID.getId());
		boolean hasUserTask = detailsService.hasUserTask(authentication, request);
		assertThat(hasUserTask, is(true));
	}

	@Test
	public void userDoesNotHaveTask() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_NO_PROJECT, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("taskId")).thenReturn(TASK_ID.getId());
		boolean hasUserTask = detailsService.hasUserTask(authentication, request);
		assertThat(hasUserTask, is(false));
	}

	@Test
	public void userIsNotAllowedToChangeTask() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_NO_PROJECT, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("taskId")).thenReturn(TASK_ID.getId());
		boolean userAllowedToChangeTask = detailsService.isUserAllowedToChangeTask(authentication, request);
		assertThat(userAllowedToChangeTask, is(false));
	}

	@Test
	public void userIsAllowedToChangeTaskBecauseHeOwnsIt() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_MEMBER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("taskId")).thenReturn(TASK_ID.getId());
		boolean userAllowedToChangeTask = detailsService.isUserAllowedToChangeTask(authentication, request);
		assertThat(userAllowedToChangeTask, is(true));
	}

	@Test
	public void userIsAllowedToChangeTaskBecauseHeOwnsTheProject() {
		Authentication authentication = new TestingAuthenticationToken(USERNAME_OWNER, null);
		authentication.setAuthenticated(true);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("taskId")).thenReturn(TASK_ID.getId());
		boolean userAllowedToChangeTask = detailsService.isUserAllowedToChangeTask(authentication, request);
		assertThat(userAllowedToChangeTask, is(true));
	}

	@ComponentScan(basePackages = "kr.ac.kaist.se.tardis.users.impl.security")
	public static class TestConfiguration {

		@Bean
		public TaskService createMockTaskService() {
			TaskService taskService = mock(TaskService.class);
			Task task = mock(Task.class);
			when(task.getTaskOwner()).thenReturn(USERNAME_MEMBER);
			when(task.getProjectOwner()).thenReturn(USERNAME_OWNER);
			when(task.getProjectId()).thenReturn(PROJECT_ID);
			when(taskService.findTaskById(TASK_ID)).thenReturn(Optional.of(task));
			return taskService;
		}

		@Bean
		public ProjectService createMockProjectService() {
			ProjectService service = mock(ProjectService.class);
			Project project = mock(Project.class);
			when(project.getProjectMembers()).thenReturn(new HashSet<>(Arrays.asList(USERNAME_MEMBER, USERNAME_OWNER)));
			when(project.getProjectOwner()).thenReturn(USERNAME_OWNER);
			when(service.findProjectById(PROJECT_ID)).thenReturn(Optional.of(project));
			return service;
		}

		@Bean
		public UserRepository createMockRepository() {
			UserRepository mock = mock(UserRepository.class);
			when(mock.findOne(USERNAME_OWNER)).thenReturn(PROJECT_OWNER);
			when(mock.findOne(USERNAME_NO_PROJECT)).thenReturn(null);
			when(mock.findOne(USERNAME_MEMBER)).thenReturn(PROJECT_MEMBER);
			return mock;
		}

	}
}
