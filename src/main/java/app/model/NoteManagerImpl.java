package app.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import app.dao.NoteDAOImpl;

public class NoteManagerImpl implements NoteManager, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9107161229527344088L;

	ArrayList<Note> notes;
	
	public NoteManagerImpl() {
		notes = new ArrayList<>();
	}
	
	public void removeNote(int index) throws NoteException {
		if (index<0 || index>notes.size()) {
			throw new NoteException("Index is out of bounds");
		} else {
			notes.remove(index);
		}
	}

	public void removeNote(Note note) {
		notes.remove(note);
	}

	public void addNote(Note note) {
		notes.add(note);

	}

	public Note getNote(int index) {
		return notes.get(index);
	}

	public Note getNote(String name) {
		for (Note note : notes) {
			if (note.getName().equals(name)) {
				return note;
			}
		}
		return null;
	}

	@Override
	public List<Note> getAllNotes() {
		return notes;
	}

	@Override
	public void deleteAllNotes() {
		notes.removeAll(notes);
	}

	@Override
	public void setNotes(List<Note> notes) {
		this.notes=(ArrayList<Note>) notes;
	}
	
	

}
