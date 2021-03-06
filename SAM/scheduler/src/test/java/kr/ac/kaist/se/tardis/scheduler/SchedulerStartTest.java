package kr.ac.kaist.se.tardis.scheduler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.connector.github.api.GitHubService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SchedulerConfig.class, SchedulerStartTest.TestDataSourceConfiguration.class})
public class SchedulerStartTest {
	
	@Autowired
	private Scheduler scheduler;
	
	@Test
	public void schedulerHasBeenStarted() throws SchedulerException{
		assertThat(scheduler.isStarted(), is(true));
	}
	
	@Configuration
	public static class TestDataSourceConfiguration {
		
		@Bean
		public GitHubService createMockGitHubService(){
			return mock(GitHubService.class);
		}
		
		@Bean
		public DataSource createUsersDataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase embeddedDatabase = builder.generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
					.setScriptEncoding("UTF-8")
					 .addScript("kr/ac/kaist/se/tardis/persistence/sql/h2.quartz.sql")
					.build();
			return embeddedDatabase;
		}
		
	}

}
