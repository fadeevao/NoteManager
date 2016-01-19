package app.login;


import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.UserDAOImpl;
import app.model.User;

@Service("userService")
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private UserDAOImpl userDao;
	
	@Override
	public boolean isValidUser(String username, String password) throws SQLException
	{
		return userDao.isValidUser(username, password);
	}
	
	public UserDAOImpl getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDAOImpl userDao) {
		this.userDao = userDao;
	}

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
		
	}
	
	public long getIdForUserName(String username) {
		return userDao.getId(username);
	}

}
