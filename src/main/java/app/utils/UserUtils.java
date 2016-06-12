package app.utils;

import app.entities.User;
import app.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    /*
	 * Used when a user tries to log in to check whether the provided user details and generated hash match database records
	 * @param name
	 * @param hash
	 */
    public boolean areUserDCredentialsCorrect(String name, String hash) {
        List<User> users = userRepository.findByNameAndPasswordHash(name, hash);
        return !users.isEmpty();
    }

    public boolean isUserNameAlreadyInUse(String name) {
        User user = userRepository.findByName(name);
        return user != null;
    }

    public Long getIdFromName(String name) {
        return userRepository.findByName(name).getId();
    }


}

