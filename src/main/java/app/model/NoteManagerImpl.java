package app.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.NoteDao;


@Service("noteManager")
public class NoteManagerImpl implements NoteManager, Serializable {
	
	private static final long serialVersionUID = -9107161229527344088L;

	@Autowired
	private NoteDao dao;
	
	public NoteManagerImpl() {}
	
	@Override
	public void addNote(Note note, User user) {
		dao.save(new Note(note.getName(), note.getContent(), user.getId()));
	}
	
	@Override
	public Note getNote(String name) {
		return  dao.getNote(name);
	}

	@Override
	public List<Note> getAllNotes(long id) {
		return  (ArrayList<Note>) dao.findAll(id);
	}

	@Override
	public void deleteAllNotes() {
		dao.deleteAllNotes();
	}

	@Override
	public void deleteSelectedNotes(String[] selectedNotes) {
		dao.deleteNotes(selectedNotes);
	}
	
	

}
