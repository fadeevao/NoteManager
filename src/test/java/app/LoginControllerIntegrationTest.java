package app;


import app.controller.LoginController;
import app.entities.User;
import app.login.LoginBean;
import app.login.LoginDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {NoteManagerMainApp.class})
public class LoginControllerIntegrationTest {


	@Mock
	LoginDelegate loginDelegate;

	private final Long USER_ID = 12345L;

	private MockMvc mockMvc;

	@InjectMocks
	LoginController loginController;


	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");


		 MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testDisplayLogin() throws Exception {
		 this.mockMvc.perform(get("/login"))
		            .andExpect(status().isOk())
		            .andExpect(view().name("login"));
	}

	@Test
	public void testDisplayRegistration() throws Exception {
		User user = new User("Username");
		user.setId(USER_ID);
		 this.mockMvc.perform(get("/register"))
		            .andExpect(status().isOk())
		            .andExpect(view().name("register"));
	}

	//@Test
	public void testRegisterWithValidPassword() throws Exception{
		User user = new User("username");
		user.setPassword("password78964");
		Mockito.when(loginDelegate.checkUsernameExists("username")).thenReturn(false);


		this.mockMvc.perform(post("/register")
				.param("username", "username")
				.param("password", user.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	//@Test
	public void testRegisterWithInvalidPassword() throws Exception{
		User user = new User();
		user.setName("username");
		user.setPassword("p%^&*%&*&7894654");
		Mockito.when(loginDelegate.checkUsernameExists(Mockito.anyString())).thenReturn(false);


		this.mockMvc.perform(post("/register")
				.param("username", user.getName())
				.param("password", user.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("register"));
	}

	@Test
	public void testGetHome() throws Exception {
		 this.mockMvc.perform(get("/home"))
		 	.andExpect(status().isOk())
		 	.andExpect(view().name("home"));
	}

	@Test
	public void testLogout() throws Exception {
		User user = new User("Username");
		user.setId(USER_ID);
		 this.mockMvc.perform(get("/logout")
		 	.sessionAttr("user", user))
		 	.andExpect(status().is(302)) //redirect
		 	.andExpect(redirectedUrl("/home"));
	}

	@Test
	public void testExecuteLoginUserExistsAndPasswordIsCorrect() throws Exception{
		LoginBean loginBean = getLoginBean();
		Mockito.when(loginDelegate.checkUsernameExists(Mockito.anyString())).thenReturn(true);
		Mockito.when(loginDelegate.isValidUser(Mockito.anyString(),Mockito.anyString())).thenReturn(true);

		this.mockMvc.perform(post("/login")
				.param("username", loginBean.getUsername())
				.param("password", loginBean.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("welcome"));
	}

	@Test
	public void testExecuteLoginUserExistsIncorrectPasswordSupplied() throws Exception{
		LoginBean loginBean = getLoginBean();
		Mockito.when(loginDelegate.checkUsernameExists(Mockito.anyString())).thenReturn(true);
		Mockito.when(loginDelegate.isValidUser(Mockito.anyString(),Mockito.anyString())).thenReturn(false);

		this.mockMvc.perform(post("/login")
				.param("username", loginBean.getUsername())
				.param("password", loginBean.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}

	@Test
	public void testExecuteLoginUserDoesNotExist() throws Exception{
		LoginBean loginBean = getLoginBean();
		Mockito.when(loginDelegate.checkUsernameExists(Mockito.anyString())).thenReturn(false);

		this.mockMvc.perform(post("/login")
				.param("username", loginBean.getUsername())
				.param("password", loginBean.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}

	private LoginBean getLoginBean() {
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername("username");
		loginBean.setPassword("password");
		return loginBean;
	}
}
