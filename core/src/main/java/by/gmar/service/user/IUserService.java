package by.gmar.service.user;

import by.gmar.model.dto.PageParams;
import by.gmar.model.user.User;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 *
 * @author s.kosik
 */
public interface IUserService extends UserDetailsService, AuthenticationManager {
    User create(final User user);
    /**
     * 
     * @param id
     * @return User with additional data such as TotalTrip amount and distance.
     */
    User getById(final Long id);
}
