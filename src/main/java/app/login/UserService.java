package app.login;


import app.entities.User;
import app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service("userService")
public class UserService
{

	@Autowired
	private UserUtils userUtils;

	public boolean areUserDetailsCorrect(String username, String hash) throws SQLException {
        return userUtils.areUserDCredentialsCorrect(username, hash);
    }

	public void saveUser(User user) {
		userUtils.saveUser(user);
	}

	public long getIdForUserName(String username) {
		return userUtils.getIdFromName(username);
	}

	public boolean doesUserExist(String username) {
		return userUtils.isUserNameAlreadyInUse(username);
	}

	public String getSaltForUsername(String username) {
		return userUtils.getSalt(username);
	}

}
