package app;


import app.entities.Note;
import app.login.LoginBean;
import app.login.LoginDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {NoteManagerMainApp.class, SecurityConfig.class})
@WebAppConfiguration
public class AuthorizedRequestsSecurityTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private  LoginDelegate loginDelegate;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }

    @Test
    public void testLoginAuthenticationOk() throws Exception {
        setUpUserAccount();
        mvc
                .perform(formLogin())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notebook/welcome"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testGetWelcomePageWhenAuthorized() throws Exception{
        setUpUserAccount();
        mvc.perform(get("/notebook/welcome"))
                .andExpect(view().name("welcome"));
    }

    @Test
    @WithMockUser(username = "user" )
    public void accessNotes() throws Exception{
        setUpUserAccount();
        mvc.perform(get("/notebook/notes"))
                .andExpect(view().name("notes"));
    }

    @Test
    @WithMockUser(username = "user" )
    public void getAddNoteScreen() throws Exception{
        mvc.perform(get("/notebook/addNote"))
                .andExpect(view().name("addNote"))
                .andExpect(model().attribute("note", new Note()));
    }


    @Test
    @WithMockUser(username = "user" )
    public void getNoteByName() throws Exception{
        mvc.perform(get("/notebook/notes/note1"))
                .andExpect(view().name("note"));
    }

    private  void setUpUserAccount() {
        if (!loginDelegate.usernameExists("user")) {
            LoginBean loginBean = new LoginBean();
            loginBean.setUsername("user");
            loginBean.setPassword("password");
            loginDelegate.saveUser(loginBean);
        }
    }
}
