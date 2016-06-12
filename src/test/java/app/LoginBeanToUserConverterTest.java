package app;

import app.entities.User;
import app.login.LoginBean;
import app.login.LoginBeanToUserConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginBeanToUserConverterTest {

	private final String PASSWORD = "12345";
	private final String USERNAME = "User";
	private final Long ID = 1L;

	private LoginBeanToUserConverter converter;

	@Before
	public void setUp() {
		converter = new LoginBeanToUserConverter();
	}

	@Test
	public void testConvertion() {
		LoginBean bean = new LoginBean();
		bean.setPassword(PASSWORD);
		bean.setName(USERNAME);

		User user = converter.convert(bean, ID);
		assertEquals(user.getName(), USERNAME);
		assertEquals(user.getId(), ID);
		assertEquals(user.getHash(), null);
	}

}
