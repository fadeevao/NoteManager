package app.login;

import java.sql.SQLException;

import app.model.User;


public interface UserService
{
	public boolean isValidUser(String username, String password) throws SQLException;
	public void saveUser(User user);
}
