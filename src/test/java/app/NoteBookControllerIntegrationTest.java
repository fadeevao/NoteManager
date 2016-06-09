package app;


import app.controller.NoteBookController;
import app.entities.Note;
import app.entities.User;
import app.utils.NoteUtils;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {NoteManagerMainApp.class})
public class NoteBookControllerIntegrationTest {

	@Mock
	private NoteUtils noteUtils;

	private MockMvc mockMvc;

	@InjectMocks
	NoteBookController noteBookController;


	@Before
	public void setup() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
		 MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(noteBookController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testDeleteAllNotes() throws Exception {
		 this.mockMvc.perform(get("/delete"))
		            .andExpect(status().is(302)) //redirect
		            .andExpect(redirectedUrl("/notes"));
	}

	@Test
	public void testDeleteSelectedNotes() throws Exception {
		 this.mockMvc.perform(post("/deleteSelectedNotes"))
		            .andExpect(status().is(302)) //redirect
		            .andExpect(redirectedUrl("/notes"));
	}

	@Test
	public void testGetNote() throws Exception {
		Mockito.when(noteUtils.getNote("note1")).thenReturn(new Note());
		 this.mockMvc.perform(get("/notes/note1")
				 	.sessionAttr("user", new User("username")))
		  			.andExpect(status().isOk())
		            .andExpect(view().name("note"));
	}

	@Test
	public void testAddNote() throws Exception {

		 this.mockMvc.perform(get("/addNote"))
		  			.andExpect(status().isOk());
	}

	@Test
	public void testDisplayAllNotes() throws Exception {
		User user = new User("Username");
		user.setId(12345L);
		Mockito.when(noteUtils.getAllNotes(12345)).thenReturn(new ArrayList<Note>());
		 this.mockMvc.perform(get("/notes")
			.sessionAttr("user", user))
			.andExpect(status().isOk())
			.andExpect(view().name("notes"));
	}

}
