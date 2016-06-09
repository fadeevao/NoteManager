package app.repos;


import app.entities.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {
    public Note findByName(String name);
    public List<Note> findByUserId(Long id);
}
