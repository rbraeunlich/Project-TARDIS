package kr.ac.kaist.se.tardis.task.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value={TaskPersistenceTest.TestDataSourceConfiguration.class})
public class TaskPersistenceTest {
	
	//FIXME replace with concrete type
	@Autowired
	private JpaRepository<TaskImpl, TaskId> repo;
	
	
	@Test
	public void persistProject() {
		TaskId id = TaskIdFactory.generateTaskId();
		TaskImpl project = new TaskImpl(id, ProjectIdFactory.generateProjectId(), "owner");
		repo.saveAndFlush(project);
		TaskImpl findOne = repo.findOne(id);
		assertThat(findOne, is(notNullValue()));
	}


	@Configuration
	@ComponentScan
	public class TestDataSourceConfiguration {
		
		@Bean
		public DataSource createUsersDataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
					.setScriptEncoding("UTF-8")
					//.addScript("kr/ac/kaist/se/tardis/...")
					.build();
			return embeddedDatabase;
		}
		
	}

}
