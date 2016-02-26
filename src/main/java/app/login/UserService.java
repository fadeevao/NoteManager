package app.login;


import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.UserDao;
import app.model.User;

@Service("userService")
public class UserService
{
	
	@Autowired
	private UserDao userDao;
	
	public boolean isValidUser(String username, String hash) throws SQLException
	{
		return userDao.isValidUser(username, hash);
	}
	
	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void saveUser(User user) {
		userDao.saveUser(user);
		
	}
	
	public long getIdForUserName(String username) {
		return userDao.getId(username);
	}
	
	public boolean doesUserExist(String username) {
		return  userDao.doesUsernamerExist(username);
	}
	
	public String getSaltForUsername(String username) {
		return userDao.getSalt(username);
	}

}
