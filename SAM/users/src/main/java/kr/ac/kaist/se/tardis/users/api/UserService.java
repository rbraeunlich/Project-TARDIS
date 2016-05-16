package kr.ac.kaist.se.tardis.users.api;

public interface UserService {
	
	User createUser(String name, String password);
	
	User findUserByName(String name);
	
	void deleterUser(User u);
	
}