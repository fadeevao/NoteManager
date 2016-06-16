package app;


import app.controller.NoteBookController;
import app.entities.Note;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {NoteManagerMainApp.class})
public class NoteBookControllerNoSecurityConfiguredIntegrationTest extends IntegrationTest {

	@Mock
	private NoteUtils noteUtils;

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
		Mockito.when(noteUtils.getNote("note1")).thenReturn(modelAttributeNote);
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

}
