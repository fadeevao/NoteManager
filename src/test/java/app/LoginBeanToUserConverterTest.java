package app;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import app.login.LoginBean;
import app.login.LoginBeanToUserConverter;
import app.model.User;

public class LoginBeanToUserConverterTest {
	
	private final String PASSWORD = "12345";
	private final String USERNAME = "User";
	private final long ID = 1;
	
	private LoginBeanToUserConverter converter;
	
	@Before
	public void setUp() {
		converter = new LoginBeanToUserConverter();
	}
	
	@Test
	public void testConvertion() {
		LoginBean bean = new LoginBean();
		bean.setPassword(PASSWORD);
		bean.setUsername(USERNAME);
		
		User user = converter.convert(bean, ID);
		assertEquals(user.getUsername(), USERNAME);
		assertEquals(user.getId(), ID);
		assertEquals(user.getSalt(), null);
		assertEquals(user.getHash(), null);
	}
	
}