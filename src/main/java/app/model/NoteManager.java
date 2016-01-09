package app.model;
import java.util.List;
import app.model.Note;;


public interface NoteManager {
	
	
	public void addNote(Note note);
	
	public Note getNote(String name);
	
	public List<Note> getAllNotes();
	
	public void deleteAllNotes();
	
	public void deleteSelectedNotes(String[] selectedNotes);
	
}
