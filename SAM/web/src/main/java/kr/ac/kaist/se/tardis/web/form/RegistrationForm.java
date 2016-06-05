package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationForm {

	public static final String USERNAME_LENGTH_ERROR = "Username must be between 1 and 256 characters";
	public static final String PASSWORD_LENGTH_ERROR = "Password must be between 1 and 256 characters";
	public static final String USERNAME_EQUAL_ERROR = "Usernames must be equal";
	public static final String PASSWORD_EQUAL_ERROR = "Passwords must be equal";
	public static final String USERNAME_DUP = "Username is duplicated";

	@NotNull
	@Size(min = 1, max = 256, message = USERNAME_LENGTH_ERROR)
	private String username;

	@NotNull
	@Size(min = 1, max = 256, message = USERNAME_LENGTH_ERROR)
	private String usernameRepeated;

	@NotNull
	@Size(min = 1, max = 256, message = PASSWORD_LENGTH_ERROR)
	private String password;

	@NotNull
	@Size(min = 1, max = 256, message = PASSWORD_LENGTH_ERROR)
	private String passwordRepeated;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsernameRepeated() {
		return usernameRepeated;
	}

	public void setUsernameRepeated(String usernameRepeated) {
		this.usernameRepeated = usernameRepeated;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}

}
