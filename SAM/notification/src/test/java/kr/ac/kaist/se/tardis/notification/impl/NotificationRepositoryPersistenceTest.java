package kr.ac.kaist.se.tardis.notification.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.sql.DataSource;

import kr.ac.kaist.se.tardis.notification.impl.persistence.NotificationRepository;
import kr.ac.kaist.se.tardis.persistence.PrimaryDbConfig;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPassword;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPasswordRepository;

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

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(value = {
		NotificationRepositoryPersistenceTest.TestDataSourceConfiguration.class,
		PrimaryDbConfig.class })
public class NotificationRepositoryPersistenceTest {

	@Autowired
	private NotificationRepository repo;

	@Autowired
	private UserWithoutPasswordRepository userRepo;

	@Test
	public void persistNotification() {
		String username = "user";
		UserWithoutPassword user = new UserWithoutPassword(username);
		userRepo.saveAndFlush(user);

		NotificationImpl notification = new NotificationImpl();
		notification.setNotificationDate(new Date());
		notification.setUsername(username);
		notification.setText("text");

		notification = repo.saveAndFlush(notification);

		NotificationImpl findOne = repo.findOne(notification.getId());
		assertThat(findOne, is(notNullValue()));
	}
	
	@Configuration
	@ComponentScan
	public static class TestDataSourceConfiguration {

		@Bean
		public DataSource createUsersDataSource() {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			EmbeddedDatabase embeddedDatabase = builder
					.generateUniqueName(true)
					.setType(EmbeddedDatabaseType.H2)
					.setScriptEncoding("UTF-8")
					.addScript(
							"kr/ac/kaist/se/tardis/persistence/sql/h2.userwithoutpwd.sql")
					.addScript(
							"kr/ac/kaist/se/tardis/persistence/sql/h2.notification.sql")
					.build();
			return embeddedDatabase;
		}

	}
}
