package app.login;


/*
 * Login bean used for passing details from  login form to the app
 */
public class LoginBean {
	
	
	private String name;
	
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
