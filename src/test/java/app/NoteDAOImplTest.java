package app;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import app.dao.NoteDao;
import app.model.Note;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:test-cfg.xml")  
@TransactionConfiguration(defaultRollback=false,transactionManager="transactionManager")  
@WebAppConfiguration
public class NoteDAOImplTest {
	
	@Autowired
	private NoteDao dao;
	
	@Transactional
	@Test
    public void testGetNoteByName() {  
    Note note = dao.getNote("name");  
    assertNotNull(note);  
    assertEquals(note.getContent(), "content");
     
     note = dao.getNote("name2");
     assertNotNull(note);
     assertEquals(note.getContent(), "content2");
     
     note = dao.getNote("non_existing_note");
     assertNull(note);
     
	}  
	
	@Transactional
	@Test
    public void testIntegrationMultipleActions() {
		List<Note> notes = dao.findAll(1);
		assertEquals(2, notes.size());
		
		Note one = notes.get(0);
		assertEquals("content", one.getContent());
		assertEquals("name", one.getName());
		
		Note two = notes.get(1);
		assertEquals("content2", two.getContent());
		assertEquals("name2", two.getName());
		
		testSaveNote();
		testDeleteSelectedNotes();
		testDeleteAllNotes();
	}
	
	private void testSaveNote() {
		Note note = new Note("name4", "content4", 1);
		dao.save(note);
		assertEquals(3, dao.findAll(1).size());
		assertEquals(note.getName(), dao.getNote("name4").getName());
		assertEquals(note.getContent(), dao.getNote("name4").getContent());
	}
	
	private void testDeleteSelectedNotes() {
		dao.deleteNotes(new String[] {"2"});
		
		List<Note> notes = dao.findAll(1);
		assertEquals(2, notes.size());
		assertEquals("name", notes.get(0).getName());
		assertEquals("name4", notes.get(1).getName());
		
	}
	
	private void testDeleteAllNotes() {
		dao.deleteAllNotes();
		List<Note> notes = dao.findAll(1);
		assertEquals(1, notes.size());
	}

}
