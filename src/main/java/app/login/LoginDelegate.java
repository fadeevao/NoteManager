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
	
	HashGenerationHelper hashGenerationHelper;
	
	public LoginDelegate() {
		hashGenerationHelper = new HashGenerationHelper();
	}
	
	public LoginDelegate(HashGenerationHelper hashGenerationHelper) {
		this.hashGenerationHelper = hashGenerationHelper;
	}
	
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
		generatePasswordHash(user);
		userService.saveUser(user);
	}
	
	public long getIdForUserFromLoginBean(LoginBean loginBean) {
		return userService.getIdForUserName(loginBean.getUsername());
	}
	
	private String generateSalt(int length) {
		org.apache.commons.lang3.RandomStringUtils utils = new org.apache.commons.lang3.RandomStringUtils();
		return utils.randomAscii(length);
	}
	
	private void generatePasswordHash(User user) {
		String salt = generateSalt(20);
		String hash = hashGenerationHelper.generateSHA256(salt+user.getPassword());
		hash = hash.substring(0, 30);
		user.setSalt(salt);
		user.setHash(hash);
		
	}
}
