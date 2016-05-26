package kr.ac.kaist.se.tardis.web.validator;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.Errors;

import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.task.api.TaskService;
import kr.ac.kaist.se.tardis.users.api.User;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.web.form.SetProjectForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SetProjectFormValidatorTest.TestConfig.class)
@ActiveProfiles(profiles={"SetProjectFormValidatorTest", "default"})
public class SetProjectFormValidatorTest {
	
	@Autowired
	private SetProjectFormValidator validator;
	
	private static final String EXISTING_USER = "testuser";
	private static final String NON_EXISTING_USER = "foobar";
	
	@Test
	public void cannotSetEmptyProjectName(){
		SetProjectForm setProjectForm = new SetProjectForm();
		setProjectForm.setProjectName("");
		Errors mockErrors = mock(Errors.class);
		validator.validate(setProjectForm, mockErrors);
		
		verify(mockErrors).rejectValue(eq("projectName"), eq("error.newMember.short"), eq(SetProjectForm.SHORT_PROJECT_NAME_ERROR));
	}
	
	@Test
	public void cannotSetNullProjectName(){
		SetProjectForm setProjectForm = new SetProjectForm();
		setProjectForm.setProjectName(null);
		Errors mockErrors = mock(Errors.class);
		validator.validate(setProjectForm, mockErrors);
		
		verify(mockErrors).rejectValue(eq("projectName"), eq("error.newMember.short"), eq(SetProjectForm.SHORT_PROJECT_NAME_ERROR));
	}
	
	@Test
	public void cannotSetProjectNameWithTwoCharacters(){
		SetProjectForm setProjectForm = new SetProjectForm();
		setProjectForm.setProjectName("12");
		Errors mockErrors = mock(Errors.class);
		validator.validate(setProjectForm, mockErrors);
		
		verify(mockErrors).rejectValue(eq("projectName"), eq("error.newMember.short"), eq(SetProjectForm.SHORT_PROJECT_NAME_ERROR));
	}
	
	@Test
	public void canSetProjectNameWithThreeCharacters(){
		SetProjectForm setProjectForm = new SetProjectForm();
		setProjectForm.setProjectName("123");
		Errors mockErrors = mock(Errors.class);
		validator.validate(setProjectForm, mockErrors);
		
		verify(mockErrors, never()).rejectValue(eq("projectName"), eq("error.newMember.short"), eq(SetProjectForm.SHORT_PROJECT_NAME_ERROR));
	}
	
	@Test
	public void cannotSetNonExistingProjectMember(){
		SetProjectForm form = new SetProjectForm();
		form.setProjectName("123");
		form.setNewMember(NON_EXISTING_USER);
		Errors mockErrors = mock(Errors.class);
		validator.validate(form, mockErrors);
		
		verify(mockErrors).rejectValue(eq("newMember"), eq("error.newMember.notExisting"), eq(SetProjectForm.NO_EXISITING_USER));
	}
	
	@Test
	public void canSetExistingUserAsProjectMember(){
		SetProjectForm form = new SetProjectForm();
		form.setProjectName("123");
		form.setNewMember(EXISTING_USER);
		Errors mockErrors = mock(Errors.class);
		validator.validate(form, mockErrors);
		
		verify(mockErrors, never()).rejectValue(eq("newMember"), eq("error.newMember.notExisting"), eq(SetProjectForm.NO_EXISITING_USER));
	}
	
	@Configuration
	@ComponentScan
	@Profile("SetProjectFormValidatorTest")
	public static class TestConfig{
		
		//actually we only need the UserService, but because we use ComponentScan, Project and TaskServuce also need to be mocked
		@Bean
		public ProjectService createMockProjectService(){
			return mock(ProjectService.class);
		}
		
		@Bean
		public TaskService createMockTaskService(){
			return mock(TaskService.class);
		}
		
		@Bean
		public UserService createMockUserService(){
			UserService userService = mock(UserService.class);
			User mockUser = mock(User.class);
			when(mockUser.getUsername()).thenReturn(EXISTING_USER);
			when(userService.findUserByName(EXISTING_USER)).thenReturn(mockUser);
			when(userService.findUserByName(NON_EXISTING_USER)).thenReturn(null);
			return userService;
		}
	}

}
