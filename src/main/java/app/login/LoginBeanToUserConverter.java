package app.login;

import app.model.User;

public class LoginBeanToUserConverter {

	public User convert(LoginBean bean) {
		User user = new User();
		user.setPassword(bean.getPassword());
		user.setUsername(bean.getUsername());
		return user;
	}
}
