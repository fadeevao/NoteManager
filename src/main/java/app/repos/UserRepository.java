package app.repos;


import app.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findByName(String name);

    List<User> findByNameAndPasswordHash(String name, String passwordHash);
}
