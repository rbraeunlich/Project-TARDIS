package kr.ac.kaist.se.tardis.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SetUserForm {
	
	public static final String PASSWORD_LENGTH_ERROR = "Password must be between 1 and 256 characters";
	public static final String PASSWORD_EQUAL_ERROR = "Passwords must be equal";

	@NotNull
	@Size(min = 1, max = 256, message = PASSWORD_LENGTH_ERROR)
	private String password;

	@NotNull
	@Size(min = 1, max = 256, message = PASSWORD_LENGTH_ERROR)
	private String passwordRepeated;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
	}
	
}
