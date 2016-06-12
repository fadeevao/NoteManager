package app.login;

import app.entities.User;
import app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginDelegate
{
	@Autowired
	private UserUtils userUtils;

	public boolean checkUsernameExists(String username) {
		return  userUtils.isUserNameAlreadyInUse(username);
	}

	public void saveUser(LoginBean loginbean) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordHash = passwordEncoder.encode(loginbean.getPassword());
		User user = new User(loginbean.getUsername(), passwordHash);
		user.setHash(passwordHash);
		userUtils.saveUser(user);
	}
}
