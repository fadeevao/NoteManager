package app.login;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/*
 * Login bean used for passing details from  login form to the app
 */
public class LoginBean {
	
	
	private String username;
	
	private String password;
	
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		LoginBean loginBean = (LoginBean) o;

		return new EqualsBuilder()
				.append(username, loginBean.username)
				.append(password, loginBean.password)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(username)
				.append(password)
				.toHashCode();
	}
}
