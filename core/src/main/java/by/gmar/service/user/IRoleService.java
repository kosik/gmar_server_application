package by.gmar.service.user;

import by.gmar.model.commons.UserRole;
import by.gmar.model.commons.UserRole;
import java.util.List;

/**
 *
 * @author s.kosik
 */
public interface IRoleService {
    List<String> getAuthoritiesForRole(UserRole userRole);
}