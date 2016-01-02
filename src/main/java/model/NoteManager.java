package model;
import java.util.List;


public interface NoteManager {
	
	public void removeNote(int index) throws NoteException;
	public void removeNote(Note note);
	
	public void addNote(Note note);
	
	public Note getNote(int index);
	public Note getNote(String name);
	
	public List<Note> getAllNotes();
	
	public void deleteAllNotes();
	
	public void setNotes(List<Note> notes);
}
