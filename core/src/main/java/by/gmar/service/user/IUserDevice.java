package by.gmar.service.user;

import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import java.util.Collection;

/**
 *
 * @author s.kosik
 */
public interface IUserDevice {
    UserDevice save(User user, String deviceId, String label);
    UserDevice get(final String udid);
    Collection<UserDevice> get(User user);
}
