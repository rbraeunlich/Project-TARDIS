package kr.ac.kaist.se.tardis.taskNote.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

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

import kr.ac.kaist.se.tardis.persistence.PrimaryDbConfig;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteIdFactory;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value={TaskNotePersistenceTest.TestDataSourceConfiguration.class, PrimaryDbConfig.class})
public class TaskNotePersistenceTest {
	
	//FIXME replace with concrete type
	@Autowired
	private JpaRepository<TaskNote, TaskNoteId> repo;
	
	
	@Test
	public void persistProject() {
		TaskNoteId commentId = TaskNoteIdFactory.generateTaskNoteId();
		TaskNoteId contributionId = TaskNoteIdFactory.generateTaskNoteId();
		Comment comment = new Comment(commentId, TaskIdFactory.generateTaskId(), "foo", new Date(), "");
		Contribution contribution = new Contribution(contributionId, TaskIdFactory.generateTaskId(), "foo", new Date(), 100, 8);
		repo.saveAndFlush(comment);
		repo.saveAndFlush(contribution);
		TaskNote findOne = repo.findOne(commentId);
		assertThat(findOne, is(notNullValue()));
		TaskNote findOne2 = repo.findOne(contributionId);
		assertThat(findOne2, is(notNullValue()));
	}


	@Configuration
	@ComponentScan
	public static class TestDataSourceConfiguration {
		
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
