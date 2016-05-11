package kr.ac.kaist.se.tardis.project.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.project.TestConfig;
import kr.ac.kaist.se.tardis.project.api.Project;
import kr.ac.kaist.se.tardis.project.api.ProjectService;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class ProjectServiceImplTest {

	@Autowired
	private ProjectService projectService;

	@After
	public void tearDown(){
		Set<Project> allProjects = projectService.getAllProjects();
		for (Project project : allProjects) {
			projectService.deleteProject(project);
		}
	}
	
	@Test
	public void createProject() {
		Project project = projectService.createProject();
		assertThat(project, is(notNullValue()));
	}

	@Test
	public void findExistingProjectByName() {
		Project project = projectService.createProject();
		String name = "foo";
		project.setName(name);
		projectService.saveProject(project);
		Set<Project> projectByName = projectService.findProjectByName(name);
		assertThat(projectByName, contains(project));
	}

	@Test
	public void findExistingProjectsByName() {
		String name = "bar";
		Project project = projectService.createProject();
		project.setName(name);
		projectService.saveProject(project);
		Project project2 = projectService.createProject();
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
		Project project = projectService.createProject();
		projectService.saveProject(project);
		Optional<Project> projectById = projectService.findProjectById(project.getId());
		assertThat(projectById.get(), is(project));
	}

	@Test
	public void findNonExistingProjectById() {
		Optional<Project> projectById = projectService.findProjectById(ProjectIdFactory.generateProjectId());
		assertThat(projectById.isPresent(), is(false));
	}

	@Test
	public void safeProject() {
		Project project = projectService.createProject();
		projectService.saveProject(project);
		// FIXME we should look into the database here, so we should modify the
		// test after persistence is complete
		Optional<Project> projectById = projectService.findProjectById(project.getId());
		assertThat(projectById.get(), is(project));
	}

	@Ignore
	@Test
	public void findExistingProjectForUser() {
		// TODO
	}
	
	@Test
	public void deleteSavedProject(){
		Project project = projectService.createProject();
		projectService.saveProject(project);
		projectService.deleteProject(project);
		Optional<Project> projectById = projectService.findProjectById(project.getId());
		assertThat(projectById.isPresent(), is(false));
	}
	
	@Test
	public void deleteNotSavedProject(){
		Project project = projectService.createProject();
		projectService.deleteProject(project);
		//just no error is fine
	}
}
