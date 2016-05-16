package kr.ac.kaist.se.tardis.users.persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.users.api.User;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(classes={TestUserDataSourceConfig.class, UserDbConfig.class})
public class UserPersistenceTest {

	@Autowired
	private UserRepository repo;

	@Test
	public void persistUser() {
		String name = "admin";
		User user = new UserImpl(name, name);
		repo.save(user);
		User findOne = repo.findOne(name);
		assertThat(findOne, is(notNullValue()));
		assertThat(findOne.getUsername(), is(name));
	}

}
