package kr.ac.kaist.se.tardis.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kr.ac.kaist.se.tardis.SamApplication;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.task.impl.state.TaskState;
import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

/**
 * This integration test is supposed to check that the security is active at the appropriate URLs.
 * It is not supposed to check every possible security combination.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {SamApplication.class, SecurityIntegrationTest.TestUserServiceConfig.class})
@WebAppConfiguration
@ActiveProfiles({"testSecurity", "default"})
public class SecurityIntegrationTest {

	private static final String USERNAME = "foo";

	private static final String PASSWORD = new BCryptPasswordEncoder().encode(USERNAME);
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void unsuccessfulLogin() throws Exception{
		mvc
			.perform(post("/index").with(user(USERNAME).password("foo")).with(csrf()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/index?error"));
	}
	
	@Test
	public void successfulLogin() throws Exception{
		mvc
			.perform(post("/index").param("username", USERNAME).param("password", PASSWORD).with(csrf()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/overview"));
	}
	
	@Test
	public void notAllowedToSeeProject() throws Exception{
		mvc
			.perform(get("/kanbanboard?projectId=" + ProjectIdFactory.generateProjectId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
			.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToSeeTask() throws Exception{
		mvc
		.perform(get("/tasksettingview?taskId=" + TaskIdFactory.generateTaskId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToChangeTask() throws Exception{
		mvc
		.perform(get("/taskchange?taskId=" + TaskIdFactory.generateTaskId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToChangeProject() throws Exception{
		mvc
		.perform(get("/projectchange?projectId=" + ProjectIdFactory.generateProjectId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}

	@Test
	public void notAllowedToViewProjectSettings() throws Exception{
		mvc
		.perform(get("/projectsettingview?projectId=" + ProjectIdFactory.generateProjectId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToChangeTaskStatus() throws Exception{
		mvc
		.perform(post("/updatestaskstatus")
				.param("taskId", TaskIdFactory.generateTaskId().getId())
				.param("projectId", ProjectIdFactory.generateProjectId().getId())
				.param("status", TaskState.INPROGRESS.toString())
				.with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToDeleteTask() throws Exception{
		mvc
		.perform(post("/taskdelete")
				.param("taskId", TaskIdFactory.generateTaskId().getId())
				.param("projectId", ProjectIdFactory.generateProjectId().getId())
				.with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void allowedToSeePersonalPage() throws Exception{
		mvc
		.perform(get("/mypage").with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isOk());
	}
	
	@Test
	public void notAllowedToSeeTaskDetails() throws Exception{
		mvc
		.perform(get("/taskdetail?taskId=" + TaskIdFactory.generateTaskId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToAddTaskDetails() throws Exception{
		mvc
		.perform(post("/addtask").param("taskId",TaskIdFactory.generateTaskId().getId()).with(user(USERNAME).password(PASSWORD)).with(csrf()))
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void notAllowedToDeleteNotification() throws Exception{
		mvc
		.perform(post("/deletenotification")
				.param("notificationId", "123")
				.param("currentUrl", "http://localhost"))
		.andExpect(status().isForbidden());
	}
	
	@Configuration
	@Profile("testSecurity")
	public static class TestUserServiceConfig{
		

		@Primary
		@Bean
		public UserRepository createMockUserRepository(){
			UserRepository mock = mock(UserRepository.class);
			UserImpl user = mock(UserImpl.class);
			when(user.getUsername()).thenReturn(USERNAME);
			when(user.getPassword()).thenReturn(PASSWORD);
			when(mock.findOne(USERNAME)).thenReturn(user);
			return mock;
		}
	}
}
