package app.login;

import app.entities.User;
import app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class LoginDelegate
{
	@Autowired
	private UserUtils userUtils;

	public boolean checkUsernameExists(String username) {
		return  userUtils.isUserNameAlreadyInUse(username);
	}

	public boolean isValidUser(String username, String password) throws SQLException
	{return true;
	}

	public void saveUser(LoginBean loginbean) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordHash = passwordEncoder.encode(loginbean.getPassword());
		User user = new User(loginbean.getName(), passwordHash);
		user.setHash(passwordHash);
		userUtils.saveUser(user);
	}

	public long getIdForUserFromLoginBean(LoginBean loginBean) {
		return userUtils.getIdFromName(loginBean.getName());
	}


}
