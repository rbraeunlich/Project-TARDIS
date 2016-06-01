package kr.ac.kaist.se.tardis.users.api;

public interface User {
	
	String getUsername();
	
	public boolean isPasswordCorrect(String password);

	String getPassword();

	void setPassword(String pwd);
	
}
