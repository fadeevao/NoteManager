package app.model;
import java.util.List;
import app.model.Note;;

/*
 * Interface that controls further CRUD operations on Note objects
 */
public interface NoteManager {
	
	public void addNote(Note note, User user);
	
	public Note getNote(String name);
	
	public List<Note> getAllNotes(long id);
	
	public void deleteAllNotes();
	
	public void deleteSelectedNotes(String[] selectedNotes);
	
}
