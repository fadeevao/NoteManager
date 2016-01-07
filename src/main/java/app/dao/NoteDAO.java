package app.dao;

import java.io.Serializable;
import java.util.List;

import app.model.Note;

public interface NoteDAO extends Serializable{
	
	    public List<Note> findAll();
	    public void save(Note note);
	    public Note getNote(String name);
	    public void deleteAllNotes();
		void deleteNotes(String[] id);

}