package app;


import app.login.LoginDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {NoteManagerMainApp.class, SecurityConfig.class})
@WebAppConfiguration
public class UnauthorizedRequestsSecurityTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private LoginDelegate loginDelegate;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @WithMockUser
    @Test
    public void testSetup() {
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), new String("user"));
    }


    @Test
    public void testGetWelcomePageWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(get("/welcome"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }

    @Test
    public void testGetNotesPageWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(get("/notes"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }

    @Test
    public void testAddNotePageWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(get("/addNote"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }

    @Test
    public void testPostNotePageWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(post("/addNote"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }

    @Test
    public void testLookAtTheNoteWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(get("/notes/note1"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }

    @Test
    public void testDeleteNoteWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(get("/delete"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }

    @Test
    public void testDeleteSelectedNotesWhenUnauthorizedRedirectToLogin() throws Exception{
        MvcResult result = mvc.perform(post("/deleteSelectedNotes"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertTrue(result.getResponse().getRedirectedUrl().contains("/login"));
    }


    @Test
    public void testGetRegisterPageNoAuthorizationNeeded() throws Exception{
        mvc.perform(get("/register"))
                .andExpect(view().name("register"));
    }


}
