package kr.ac.kaist.se.tardis.notification.impl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Set;

import javax.sql.DataSource;

import kr.ac.kaist.se.tardis.notification.api.Notification;
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
		NotificationImplPersistenceTest.TestDataSourceConfiguration.class,
		PrimaryDbConfig.class })
public class NotificationImplPersistenceTest {

	@Autowired
	private NotificationRepository repo;

	@Autowired
	private UserWithoutPasswordRepository userRepo;

	@Test
	public void findSeveralNotificationsByUsername() {
		String username = "user";
		UserWithoutPassword user = new UserWithoutPassword(username);
		userRepo.saveAndFlush(user);

		NotificationImpl notification = new NotificationImpl();
		notification.setNotificationDate(new Date());
		notification.setUsername(username);
		notification.setText("text");

		NotificationImpl notification2 = new NotificationImpl();
		notification2.setNotificationDate(new Date());
		notification2.setUsername(username);
		notification2.setText("text");

		NotificationImpl notification3 = new NotificationImpl();
		notification3.setNotificationDate(new Date());
		notification3.setUsername(username);
		notification3.setText("text");

		notification = repo.saveAndFlush(notification);
		notification2 = repo.saveAndFlush(notification2);
		notification3 = repo.saveAndFlush(notification3);

		Set<Notification> notificationsByUsername = repo
				.findNotificationsByUsername(username);
		assertThat(notificationsByUsername,
				hasItems(notification, notification2, notification3));
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
							"kr/ac/kaist/se/tardis/notification/sql/h2.notification.sql")
					.build();
			return embeddedDatabase;
		}

	}
}
