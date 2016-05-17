package kr.ac.kaist.se.tardis.users.impl;

import org.springframework.beans.factory.annotation.Autowired;

import kr.ac.kaist.se.tardis.users.api.User;
import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.users.api.UserWithoutPasswordRepository;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserWithoutPasswordRepository userWOPasswordRepo;

	@Override
	public User createUser(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleterUser(User u) {
		// TODO Auto-generated method stub

	}

}
