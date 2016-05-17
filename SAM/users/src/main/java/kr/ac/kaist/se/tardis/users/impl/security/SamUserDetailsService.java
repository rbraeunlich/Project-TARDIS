package kr.ac.kaist.se.tardis.users.impl.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import kr.ac.kaist.se.tardis.users.api.UserRepository;
import kr.ac.kaist.se.tardis.users.impl.UserImpl;

@Component
public class SamUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//FIXME
		if(username.equals("admin")){
			return new User("admin", new BCryptPasswordEncoder().encode("admin"), true, true, true, true, Collections.emptySet());
		}
		UserImpl foundUser = userRepo.findOne(username);
		if(foundUser == null){
			return new User("unknown", "", false, false, false, false, Collections.emptySet());
		}
		return new User(username, foundUser.getPassword(), true, true, true, true, Collections.emptySet());
	}
	
	public boolean isUserProjectOwner(Authentication authentication, HttpServletRequest request){
		return true;
	}
	
	public boolean isUserProjectMember(Authentication authentication, HttpServletRequest request){
		authentication.isAuthenticated();
		return true;
	}
	
	public boolean hasUserTask(Authentication authentication, HttpServletRequest request){
		return true;
	}

	public boolean isUserAllowedToChangeTask(Authentication authentication, HttpServletRequest request){
		return isUserProjectOwner(authentication, request) || hasUserTask(authentication, request);
	}
}
