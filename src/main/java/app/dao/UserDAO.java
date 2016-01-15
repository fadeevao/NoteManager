package app.dao;

import java.sql.SQLException;

public interface UserDAO
{
	public boolean isValidUser(String username, String password) throws SQLException;
}

