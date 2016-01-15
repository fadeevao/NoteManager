package app.login;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.User;

@Service("loginDelegate")
public class LoginDelegate
{
	@Autowired
	private UserServiceImpl userService;
	
	public UserService getUserService()
	{
		return this.userService;
	}

	public void setUserService(UserServiceImpl userService)
	{
		this.userService = userService;
	}

	public boolean isValidUser(String username, String password) throws SQLException
	{
	    return userService.isValidUser(username, password);
	}
	
	public void saveUser(User user) {
		userService.saveUser(user);
	}
}
