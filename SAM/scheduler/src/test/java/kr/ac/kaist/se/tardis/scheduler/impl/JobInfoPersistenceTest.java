package kr.ac.kaist.se.tardis.scheduler.impl;

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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.persistence.PrimaryDbConfig;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfo;
import kr.ac.kaist.se.tardis.scheduler.api.JobInfoRepository;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value={JobInfoPersistenceTest.TestDataSourceConfiguration.class, PrimaryDbConfig.class})
public class JobInfoPersistenceTest {
	
	@Autowired
	private JobInfoRepository repo;
	
	
	@Test
	public void persistProject() {
		JobInfo info = new JobInfo();
		repo.saveAndFlush(info);
		JobInfo findOne = repo.findOne(info.getId());
		assertThat(findOne, is(notNullValue()));
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
