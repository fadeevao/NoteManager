package app;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        if (authentication != null && authentication.getDetails() != null) {
            httpServletRequest.getSession().invalidate();
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        try {
            httpServletResponse.sendRedirect("/home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
