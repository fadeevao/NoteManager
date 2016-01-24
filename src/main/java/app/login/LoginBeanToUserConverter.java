package app.login;

import app.model.User;

public class LoginBeanToUserConverter {

	public User convert(LoginBean bean, long id) {
		User user = new User();
		user.setUsername(bean.getUsername());
		user.setId(id);
		return user;
	}
}
