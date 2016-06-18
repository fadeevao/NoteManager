package app.repos;


import app.entities.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {
     Note findByName(String name);
     List<Note> findByUserId(Long id);
}
