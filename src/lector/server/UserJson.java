package lector.server;

import java.io.Serializable;

public class UserJson implements Serializable
{
	
	public String User;
	public String Password;
	
	public UserJson() {
		User="";
		Password="";
	}
	
	public UserJson(String user,String password) {
		super();
		User=user;
		Password=password;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	
	
}
