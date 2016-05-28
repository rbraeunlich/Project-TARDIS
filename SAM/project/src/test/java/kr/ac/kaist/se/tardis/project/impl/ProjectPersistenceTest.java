package kr.ac.kaist.se.tardis.project.impl;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

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
import kr.ac.kaist.se.tardis.project.impl.id.ProjectId;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobType;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPassword;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPasswordRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value={ProjectPersistenceTest.TestDataSourceConfiguration.class, PrimaryDbConfig.class})
public class ProjectPersistenceTest {
	
	@Autowired
	private ProjectRepository repo;
	@Autowired
	private UserWithoutPasswordRepository userRepo;
	
	@Test
	public void persistProject() {
		UserWithoutPassword user = new UserWithoutPassword("owner");
		userRepo.saveAndFlush(user);
		
		ProjectId id = ProjectIdFactory.generateProjectId();
		ProjectImpl project = new ProjectImpl(id, user.getUsername());
		repo.saveAndFlush(project);
		ProjectImpl findOne = repo.findOne(id);
		assertThat(findOne, is(notNullValue()));
	}
	
	@Test
	public void persistProjectWithJobInfo() {
		UserWithoutPassword user = new UserWithoutPassword("owner");
		userRepo.saveAndFlush(user);

		ProjectId id = ProjectIdFactory.generateProjectId();
		
		JobInfo info = new JobInfo();
		info.setGithubUrl("https://github.com/rbraeunlich/Project-TARDIS");
		info.setJobType(JobType.GITHUB);
		info.setTriggerId("123");
		info.setProjectId(id.getId());
		info.setTaskId("");
		
		ProjectImpl project = new ProjectImpl(id, user.getUsername());
		
		project = repo.saveAndFlush(project);
		
		project.addJobInfo(info);
		repo.saveAndFlush(project);
		
		ProjectImpl findOne = repo.findOne(id);
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
					.setScriptEncoding("UTF-8")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.project.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.jobinfo.sql")
					.build();
			return embeddedDatabase;
		}
		
	}

}
