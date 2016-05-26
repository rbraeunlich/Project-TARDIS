package kr.ac.kaist.se.tardis.web.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.web.form.CreateProjectForm;

public class OverviewControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Autowired
	private ProjectService projectService;
	
	@After
	public void tearDown(){
		Set<Project> allProjects = projectService.getAllProjects();
		for (Project project : allProjects) {
			projectService.deleteProject(project);
		}
	}

	@WithMockUser
	@Test
	public void createNewProject() throws Exception{
		mockMvc
			.perform(post("/overview")
					.param("projectName", "ABC")
					.param("description", "A neat little project"))
					.andExpect(redirectedUrl("overview"))
					.andReturn();
		assertThat(projectService.findProjectByName("ABC"), is(not(empty())));
	}
	
	@WithMockUser
	@Test
	public void tryToCreateProjectWithoutName() throws Exception{
		mockMvc
			.perform(post("/overview")
					.param("description", "A neat little project"))
			.andExpect(content().string(containsString(CreateProjectForm.NO_PROJECT_NAME_ERROR)));
		assertThat(projectService.findProjectByName("ABC"), is(empty()));
	}
	
	@WithMockUser
	@Test
	public void tryToCreateProjectWithShortName() throws Exception{
		mockMvc
			.perform(post("/overview")
					.param("projectName", "AB")
					.param("description", "A neat little project"))
			.andExpect(content().string(containsString(CreateProjectForm.SHORT_PROJECT_NAME_ERROR)));
		assertThat(projectService.findProjectByName("ABC"), is(empty()));
	}
	
	@WithMockUser
	@Test
	public void createProjectWithoutDescription() throws Exception{
		mockMvc
			.perform(post("/overview")
					.param("projectName", "ABC"))
			.andExpect(redirectedUrl("overview"));
		assertThat(projectService.findProjectByName("ABC"), is(not(empty())));
	}
}
