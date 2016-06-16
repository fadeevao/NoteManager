package app.utils;

import app.entities.User;
import app.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }


    public boolean isUserNameAlreadyInUse(String name) {
        User user = userRepository.findByName(name);
        return user != null;
    }

}

