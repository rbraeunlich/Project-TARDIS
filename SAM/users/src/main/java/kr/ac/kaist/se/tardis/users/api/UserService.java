package kr.ac.kaist.se.tardis.users.api;

import kr.ac.kaist.se.tardis.users.impl.UserImpl;

public interface UserService {
	
	/**
	 * Creates and persists a user.
	 * @param name
	 * @param password
	 */
	void createUser(String name, String password);
	
	User findUserByName(String name);
	
	void deleterUser(UserImpl u);
	
}