package app;


import app.controller.NoteBookController;
import app.entities.Note;
import app.entities.User;
import app.login.CurrentUser;
import app.login.CurrentUserInitializer;
import app.utils.NoteUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {NoteManagerMainApp.class})
public class NoteBookControllerNoSecurityConfiguredIntegrationTest extends IntegrationTest {

	@Mock
	private NoteUtils noteUtils;

	@Mock
	private CurrentUserInitializer currentUserInitializer;

	@InjectMocks
	NoteBookController noteBookController;


	@Before
	public void setup() {
		super.setup();
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(noteBookController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testDeleteAllNotes() throws Exception {
		 this.mockMvc.perform(get("/notebook/delete"))
		            .andExpect(status().is(302)) //redirect
		            .andExpect(redirectedUrl("/notebook/notes"));
	}

	@Test
	public void testDeleteSelectedNotes() throws Exception {
		 this.mockMvc.perform(post("/notebook/deleteSelectedNotes"))
		            .andExpect(status().is(302)) //redirect
		            .andExpect(redirectedUrl("/notebook/notes"));
	}

	@Test
	public void testGetNote() throws Exception {
		Note modelAttributeNote = new Note("name", "content");
		when(noteUtils.getNote("note1")).thenReturn(modelAttributeNote);
		this.mockMvc.perform(get("/notebook/notes/note1"))
		  			.andExpect(status().isOk())
		            .andExpect(view().name("note"))
		 			.andExpect(model().attribute("note", modelAttributeNote));
	}

	@Test
	public void testAddNote() throws Exception {
		 this.mockMvc.perform(get("/notebook/addNote"))
		  			.andExpect(status().isOk())
		 			.andExpect(model().attribute("note", new Note()))
		 			.andExpect(view().name("addNote"));
	}

	@Test
	public void testPostValidNote() throws Exception {
		doNothing().when(noteUtils).addNote(any());
		when(currentUserInitializer.initializeCurrentUser()).thenReturn(new CurrentUser(createUser()));
		mockMvc.perform(post("/notebook/addNote")
				.param("name", "name")
				.param("content", "content"))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl("/notebook/notes"));

	}

	@Test
	public void testPostInvalidNote() throws Exception {
		mockMvc.perform(post("/notebook/addNote")
				.param("content", "content"))
				.andExpect(view().name("addNote"));

	}

	private User createUser() {
		User user = new User();
		user.setHash("hash");
		user.setName("name");
		user.setId(12345L);
		return  user;
	}

}
