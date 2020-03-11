package by.gmar.service.impl.user;

import java.util.Collections;
import java.util.List;

import by.gmar.service.user.IRoleService;
import by.gmar.utilities.SysUtilities;
import by.gmar.model.commons.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 *
 * @author s.kosik
 */
@Component
public class DefaultRoleService implements IRoleService {
    private RoleContainer roles;

    @Autowired
    public void setEnvironment(Environment env) {
        roles = createRolesFromProperties(env);
    }

    private RoleContainer createRolesFromProperties(Environment env) {
        RoleContainer roles = new RoleContainer();
        for (String roleName : SysUtilities.commaSeparate(env.getProperty("security.availableRoles"))) {
            List<String> authorities = SysUtilities.commaSeparate(env.getProperty("security.role." + roleName));
            roles.addRole(roleName, authorities);
        }
        return roles;
    }


    @Override
    public List<String> getAuthoritiesForRole(UserRole userRole) {
        if (userRole == null) {
            return Collections.emptyList();
        }
        return roles.toAuthorities(userRole.name());
    }

}
