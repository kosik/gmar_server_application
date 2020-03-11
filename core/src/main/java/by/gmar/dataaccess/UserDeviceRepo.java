package by.gmar.dataaccess;

import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author s.kosik
 */
public interface UserDeviceRepo extends PagingAndSortingRepository<UserDevice, Long>{
    UserDevice findByUdid(String udid);
    UserDevice findByUdidAndOwner(String udid, User owner);
}
