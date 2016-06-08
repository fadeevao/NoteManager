package app;

import app.entities.Note;
import app.repos.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class NoteManager {

    @Autowired
    NoteRepository noteRepository;

	public void addNote(Note note) {
        noteRepository.save(note);
    }

	public Note getNote(String name) {
        return noteRepository.findByName(name);
    }

	public Iterable<Note> getAllNotes(long id){
        return noteRepository.findAll(Collections.singletonList(id));
    }

	public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

	public void deleteSelectedNotes(String[] selectedNotes) {
        for (int i=0; i<selectedNotes.length; i++) {
            noteRepository.delete(Long.valueOf(selectedNotes[i]));
        }
    }

}
