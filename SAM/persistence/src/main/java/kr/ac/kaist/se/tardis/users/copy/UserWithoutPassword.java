package kr.ac.kaist.se.tardis.users.copy;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

/**
 * Copy class of the {@link User} class to be used within the insecure database.
 * The class should <b>NOT</b> be placed within the same directory as the
 * UserImpl class or the {@link EntityManager} from UserDbConfig will start to
 * manage this class, which it should not do.
 */
@Entity
public class UserWithoutPassword {

	@Id
	private String username;

	public UserWithoutPassword() {
	}

	public UserWithoutPassword(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}
}
