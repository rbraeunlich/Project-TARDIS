package kr.ac.kaist.se.tardis.users.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import kr.ac.kaist.se.tardis.users.api.User;
import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.api.UserService;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPassword;
import kr.ac.kaist.se.tardis.users.copy.UserWithoutPasswordRepository;

@Service
@Profile("dev")
public class DevUserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserWithoutPasswordRepository userWOPasswordRepo;

	@Override
	public void createUser(String name, String password) {
		UserImpl userImpl = new UserImpl(name, password);
		userRepo.save(userImpl);
		UserWithoutPassword userWithoutPassword = new UserWithoutPassword(name);
		userWOPasswordRepo.save(userWithoutPassword);
	}

	@Override
	public User findUserByName(String name) {
		if("null".equals(name)){
			return new UserImpl(name, "");
		}
		return  userRepo.findOne(name);
	}

	@Override
	public void deleterUser(User u) {
		userRepo.delete((UserImpl) u);
		userWOPasswordRepo.delete((UserWithoutPassword) u);
	}

}
