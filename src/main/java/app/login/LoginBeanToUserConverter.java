package app.login;

import app.entities.User;

/*
 * Helper class that converts login bean to user object
 */
public class LoginBeanToUserConverter {

	public User convert(LoginBean bean, long id) {
		User user = new User();
		user.setName(bean.getUsername());
		user.setId(id);
		return user;
	}
}
