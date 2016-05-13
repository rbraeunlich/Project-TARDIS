package kr.ac.kaist.se.tardis.users;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UserTest {

	@Test
	public void passwordIsBeingEncrypted() {
		String password = "admin";
		User user = new User("admin", password);
		assertThat(user.getPassword(), is(not(equalTo(password))));
	}

	@Test
	public void passwordCorrectnessCheckValidPassword() {
		String password = "admin";
		User user = new User("admin", password);
		assertThat(user.isPasswordCorrect(password), is(true));
	}

	@Test
	public void passwordCorrectnessCheckInvalidPassword() {
		String password = "admin";
		User user = new User("admin", password);
		assertThat(user.isPasswordCorrect("foo"), is(false));
	}
}
