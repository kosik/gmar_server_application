package by.gmar.service.impl.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author s.kosik
 */
public class RoleContainer {

    private Map<String, List<String>> roleMap = new HashMap<>();

    public void addRole(String roleName, List<String> authorities) {
        roleMap.put(roleName, authorities);
    }

    public List<String> toAuthorities(String roleName) {
        List<String> authorities = new LinkedList<>();
        List<String> roleAuthorities = roleMap.get(roleName);
        if (roleAuthorities != null) {
            authorities.addAll(roleAuthorities);
        }
        return authorities;
    }
    public Collection<String> getAvailableRoles() {
        return roleMap.keySet();
    }

}
