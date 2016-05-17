package kr.ac.kaist.se.tardis.persistence;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.ac.kaist.se.tardis.PrimaryDbConfig;
import kr.ac.kaist.se.tardis.users.UserWithoutPassword;
import kr.ac.kaist.se.tardis.users.api.User;
import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.api.UserWithoutPasswordRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;
import kr.ac.kaist.se.tardis.users.impl.persistence.UserDbConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@SpringApplicationConfiguration(classes={TestDataSourceConfig.class, UserDbConfig.class, PrimaryDbConfig.class})
public class EntityManagerCombinationTest {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserWithoutPasswordRepository userWithoutPwdRepo;
	
	@Test
	public void bothTypesCanBeSaved() {
		String name = "admin";
		UserImpl user = new UserImpl(name, name);
		userRepo.saveAndFlush(user);
		User findOne = userRepo.findOne(name);
		assertThat(findOne, is(notNullValue()));
		assertThat(findOne.getUsername(), is(name));
		UserWithoutPassword userWOPw = new UserWithoutPassword(name);
		userWithoutPwdRepo.saveAndFlush(userWOPw);
		UserWithoutPassword findOne2 = userWithoutPwdRepo.findOne(name);
		assertThat(findOne2, is(notNullValue()));
		assertThat(findOne2.getUsername(), is(name));
	}

}
