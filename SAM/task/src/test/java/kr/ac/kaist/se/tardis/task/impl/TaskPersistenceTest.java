package kr.ac.kaist.se.tardis.task.impl;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.persistence.PrimaryDbConfig;
import kr.ac.kaist.se.tardis.project.api.ProjectRepository;
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.scheduler.api.TaskJobInfo;
import kr.ac.kaist.se.tardis.task.api.TaskRepository;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPassword;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPasswordRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value = { TaskPersistenceTest.TestDataSourceConfiguration.class,
		PrimaryDbConfig.class })
public class TaskPersistenceTest {

	@Autowired
	private TaskRepository repo;
	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private UserWithoutPasswordRepository userRepo;

	private static final ProjectId PROJECT_ID = ProjectIdFactory.generateProjectId();

	private static final String USERNAME = "the user";

	@Before
	public void setUp() {

	}

	@Test
	public void persistTask() {
		UserWithoutPassword user = new UserWithoutPassword(USERNAME);
		userRepo.saveAndFlush(user);

		ProjectImpl project = new ProjectImpl(PROJECT_ID, USERNAME);
		projectRepo.saveAndFlush(project);

		TaskId id = TaskIdFactory.generateTaskId();
		TaskImpl task = new TaskImpl(id, project, USERNAME);
		repo.saveAndFlush(task);
		TaskImpl findOne = repo.findOne(id);
		assertThat(findOne, is(notNullValue()));
	}

	@Test
	public void persistTaskWithJobInfo() {
		UserWithoutPassword user = new UserWithoutPassword(USERNAME);
		userRepo.saveAndFlush(user);

		ProjectImpl project = new ProjectImpl(PROJECT_ID, USERNAME);
		projectRepo.saveAndFlush(project);

		TaskId id = TaskIdFactory.generateTaskId();
		
		TaskJobInfo info = new TaskJobInfo();
		info.setJobType(JobType.ONE_DAY);
		info.setTriggerId("123");
		info.setTaskId(id.getId());

		TaskImpl task = new TaskImpl(id, project, USERNAME);
		task.addJobInfo(info);
		repo.saveAndFlush(task);
		TaskImpl findOne = repo.findOne(id);
		assertThat(findOne, is(notNullValue()));
		assertThat(findOne.getAllJobInfos(), hasItem(info));
	}

	@Configuration
	@ComponentScan
	public static class TestDataSourceConfiguration {

		@Bean
		public DataSource createUsersDataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
					.setScriptEncoding("UTF-8").addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.project.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.task.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.jobinfo.sql").build();
			return embeddedDatabase;
		}

	}

}
