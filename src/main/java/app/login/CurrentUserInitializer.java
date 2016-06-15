package app.login;

import app.entities.User;
import app.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserInitializer {

    @Autowired
    private UserRepository userRepository;

    public  CurrentUser initializeCurrentUser() {
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User entityUser = userRepository.findByName(springUser.getUsername());
        return new CurrentUser(springUser, entityUser);
    }
}
