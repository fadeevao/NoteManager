package app;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import app.controller.LoginController;
import app.login.LoginBean;
import app.login.LoginDelegate;
import app.model.User;



@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations="classpath:test-cfg.xml")  
public class LoginControllerTest {
	
	
	@Mock
	 LoginDelegate loginDelegate;

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
		            .andExpect(view().name("login"))
		            .andExpect(model().attributeExists("loginBean") );
	}
	
	@Test
	public void testDisplayRegistration() throws Exception {
		User user = new User("Username");
		user.setId(12345);
		 this.mockMvc.perform(get("/register"))
		            .andExpect(status().isOk())
		            .andExpect(view().name("register"))
		            .andExpect(model().attributeExists("user") );
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
		user.setId(12345);
		 this.mockMvc.perform(get("/logout")
		 	.sessionAttr("user", user))
		 	.andExpect(status().is(302)) //redirect
		 	.andExpect(redirectedUrl("/home"));
	}
	
	@Test
	public void testExecuteLoginUserExistsAndPasswordIsCorrect() throws Exception{
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername("username");
		loginBean.setPassword("password");
		Mockito.when(loginDelegate.checkUsernameExists(Mockito.anyString())).thenReturn(true);
		Mockito.when(loginDelegate.isValidUser(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
		
		this.mockMvc.perform(post("/login")
				.param("username", loginBean.getUsername())
				.param("password", loginBean.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("welcome"))
			.andExpect(model().attributeExists("user") );
	}
	
	@Test
	public void testExecuteLoginUserExistsIncorrectPasswordSupplied() throws Exception{
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername("username");
		loginBean.setPassword("password");
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
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername("username");
		loginBean.setPassword("password");
		Mockito.when(loginDelegate.checkUsernameExists(Mockito.anyString())).thenReturn(false);
		
		this.mockMvc.perform(post("/login")
				.param("username", loginBean.getUsername())
				.param("password", loginBean.getPassword())
	               )
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
}
