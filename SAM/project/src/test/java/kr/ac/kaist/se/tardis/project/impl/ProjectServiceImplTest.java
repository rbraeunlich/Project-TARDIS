package kr.ac.kaist.se.tardis.project.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;

import kr.ac.kaist.se.tardis.project.TestConfig;
import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class ProjectServiceImplTest {

	@Autowired
	private ProjectService projectService;

	@After
	public void tearDown() {
		Set<Project> allProjects = projectService.getAllProjects();
		for (Project project : allProjects) {
			projectService.deleteProject(project);
		}
	}

	@Test
	public void createProject() {
		Project project = projectService.createProject("me");
		assertThat(project, is(notNullValue()));
	}

	@Test
	public void findExistingProjectByName() {
		Project project = projectService.createProject("me");
		String name = "foo";
		project.setName(name);
		projectService.saveProject(project);
		Set<Project> projectByName = projectService.findProjectByName(name);
		assertThat(projectByName, contains(project));
	}

	@Test
	public void findExistingProjectsByName() {
		String name = "bar";
		Project project = projectService.createProject("me");
		project.setName(name);
		projectService.saveProject(project);
		Project project2 = projectService.createProject("me");
		project2.setName(name);
		projectService.saveProject(project2);
		Set<Project> projectByName = projectService.findProjectByName(name);
		assertThat(projectByName, hasItems(project, project2));
	}

	@Test
	public void findNonExistingProjectByName() {
		Set<Project> projectByName = projectService.findProjectByName("foo");
		assertThat(projectByName, is(empty()));
	}

	@Test
	public void findExistingProjectById() {
		Project project = projectService.createProject("me");
		projectService.saveProject(project);
		Optional<Project> projectById = projectService.findProjectById(project
				.getId());
		assertThat(projectById.get(), is(project));
	}

	@Test
	public void findNonExistingProjectById() {
		Optional<Project> projectById = projectService
				.findProjectById(ProjectIdFactory.generateProjectId());
		assertThat(projectById.isPresent(), is(false));
	}

	@Test
	public void safeProject() {
		Project project = projectService.createProject("me");
		projectService.saveProject(project);
		Optional<Project> projectById = projectService.findProjectById(project
				.getId());
		assertThat(projectById.get(), is(project));
	}

	@Test
	public void findExistingProjectForUser() {
		String user = "me";
		Project project = projectService.createProject(user);
		projectService.saveProject(project);
		Set<Project> projectsForUser = projectService.findProjectsForUser(user);
		assertThat(projectsForUser, contains(project));
	}

	@Test
	public void findExistingProjectForTwoUsers() {
		String user = "me";
		String user2 = "you";
		Project project = projectService.createProject(user);
		projectService.saveProject(project);
		Project project2 = projectService.createProject(user2);
		projectService.saveProject(project2);
		assertThat(projectService.findProjectsForUser(user), contains(project));
		assertThat(projectService.findProjectsForUser(user2),
				contains(project2));
	}

	@Test
	public void deleteSavedProject() {
		Project project = projectService.createProject("me");
		projectService.saveProject(project);
		projectService.deleteProject(project);
		Optional<Project> projectById = projectService.findProjectById(project
				.getId());
		assertThat(projectById.isPresent(), is(false));
	}

	@Test
	public void deleteNotSavedProject() {
		Project project = projectService.createProject("me");
		projectService.deleteProject(project);
		// just no error is fine
	}
}
