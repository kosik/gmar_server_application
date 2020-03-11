package by.gmar.web.security;

import by.gmar.model.user.User;
import by.gmar.model.user.User;
import by.gmar.model.user.User;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 *
 * @author s.kosik
 */
@Component
public class UserHolder {

    public User getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        final Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            user.setPassword(null);
            return user;
        }
        return null;
    }

    public boolean isUserAllowed(String permission) {
        Collection<? extends GrantedAuthority> authorities = getUser().getAuthorities();
        if (authorities == null) {
            return false;
        }
        for (GrantedAuthority grantedAuthority : authorities) {
            if (permission.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
