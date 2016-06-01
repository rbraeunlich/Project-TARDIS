package kr.ac.kaist.se.tardis.users.impl;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.ac.kaist.se.tardis.users.api.User;

@Entity(name="user")
public class UserImpl implements User {

	@Id
	private String username;

	@Basic
	private String password;

	/**
	 * For JPA
	 */
	public UserImpl(){}
	
	public UserImpl(String username, String password) {
		this.username = username;
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the hashed password.
	 * @return
	 */
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public void setPassword(String pwd) {
		password = pwd;
	}

	@Override
	public boolean isPasswordCorrect(String password){
		return new BCryptPasswordEncoder().matches(password, getPassword());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserImpl other = (UserImpl) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
