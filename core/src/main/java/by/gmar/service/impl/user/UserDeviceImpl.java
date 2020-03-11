package by.gmar.service.impl.user;

import by.gmar.dataaccess.UserDeviceRepo;
import by.gmar.dataaccess.UserRepository;
import by.gmar.exceptions.InvalidDataException;
import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.service.user.IUserDevice;
import by.gmar.dataaccess.UserDeviceRepo;
import by.gmar.dataaccess.UserRepository;
import by.gmar.exceptions.InvalidDataException;
import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.dataaccess.UserDeviceRepo;
import by.gmar.dataaccess.UserRepository;
import by.gmar.exceptions.InvalidDataException;
import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.service.user.IUserDevice;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author s.kosik
 */
@Service
public class UserDeviceImpl implements IUserDevice {
    @Autowired
    private UserDeviceRepo userDevRepo;
    @Autowired
    private UserRepository userRepo;
    
    private UserDevice save(UserDevice device) {
        return userDevRepo.save(device);
    }

    @Override
    public UserDevice get(final String udid) {
        if(null == udid)
            return null;
        return userDevRepo.findByUdid(udid);
    }

    @Override
    @Transactional
    public Collection<UserDevice> get(User user) {
        Collection<UserDevice> devices = userRepo.findOne(user.getId()).getUserDevices();
        
        devices.size();//to fetchs
        
        return devices;
    }

    @Override
    public UserDevice save(User user, String deviceId, String label) {
        if(null == user || null == deviceId)
            throw new InvalidDataException();
        deviceId = deviceId.toLowerCase();
        UserDevice device = userDevRepo.findByUdidAndOwner(deviceId, user);

        if(null == device){
            device = new UserDevice();
            device.setOwner(user);
            device.setUdid(deviceId);
            device.setDeviceName(label);
            return save(device);
        }

        return device;
    }
    
}
