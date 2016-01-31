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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import app.controller.LoginController;
import app.login.LoginDelegate;
import app.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations="classpath:test-cfg.xml")  
public class LoginControllerTest {
	
	@Autowired
	WebApplicationContext wac;
	
	@Mock
	private LoginDelegate loginDelegate;

	private MockMvc mockMvc;
	
	@InjectMocks
	LoginController loginController;
	

	@Before
	public void setup() {
		 MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
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
}
