package kr.ac.kaist.se.tardis.taskNote.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

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
import kr.ac.kaist.se.tardis.project.impl.ProjectImpl;
import kr.ac.kaist.se.tardis.project.impl.id.ProjectIdFactory;
import kr.ac.kaist.se.tardis.task.api.TaskRepository;
import kr.ac.kaist.se.tardis.task.impl.TaskImpl;
import kr.ac.kaist.se.tardis.task.impl.id.TaskId;
import kr.ac.kaist.se.tardis.task.impl.id.TaskIdFactory;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNote;
import kr.ac.kaist.se.tardis.taskNote.api.TaskNoteRepository;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteId;
import kr.ac.kaist.se.tardis.taskNote.impl.id.TaskNoteIdFactory;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPassword;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPasswordRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value={TaskNotePersistenceTest.TestDataSourceConfiguration.class, PrimaryDbConfig.class})
public class TaskNotePersistenceTest {
	
	@Autowired
	private TaskRepository taskRepo;
	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private UserWithoutPasswordRepository userRepo;
	@Autowired
	private TaskNoteRepository repo;
	
	
	@Test
	public void persistCommentAndContribution() {
		String username = "user";
		UserWithoutPassword user = new UserWithoutPassword(username );
		userRepo.saveAndFlush(user);

		ProjectImpl project = new ProjectImpl(ProjectIdFactory.generateProjectId(), username);
		projectRepo.saveAndFlush(project);

		TaskId id = TaskIdFactory.generateTaskId();
		TaskImpl task = new TaskImpl(id, project, username);
		task = taskRepo.saveAndFlush(task);
		
		
		TaskNoteId commentId = TaskNoteIdFactory.generateTaskNoteId();
		TaskNoteId contributionId = TaskNoteIdFactory.generateTaskNoteId();
		Comment comment = new Comment(commentId, task, "foo", new Date(), "");
		Contribution contribution = new Contribution(contributionId, task, "foo", new Date(), 100, 8);
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
					.setScriptEncoding("UTF-8").addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.project.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.task.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.jobinfo.sql")
					.addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.tasknote.sql")
					.build();
			return embeddedDatabase;
		}
		
	}
}
