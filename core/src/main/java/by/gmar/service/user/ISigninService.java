package by.gmar.service.user;

import by.gmar.exceptions.AddressNotAvailableException;
import by.gmar.exceptions.UserLoginException;
import by.gmar.model.user.User;

import java.text.ParseException;
import org.springframework.security.core.Authentication;

/**
 *
 * @author s.kosik
 */
public interface ISigninService {

    /**
     * 
     * @param phoneNumber
     * @param appToken contains md5 udid.
     * @return 
     */
    Authentication autologin(final String phoneNumber, final String appToken, 
            final String fireBaseToken)throws UserLoginException,
        ParseException, Exception;

    /**
     * 
     * @param userId
     * @param appToken contains md5 udid.
     * @return 
     */
    Authentication autologin(final Long userId, final String appToken, 
            final String fireBaseToken)throws UserLoginException, 
        ParseException, Exception;
    
    Authentication signinWithPassword(String username, String password, 
            final String fireBaseToken) throws UserLoginException;

    User register(User incomeUser)throws Exception, AddressNotAvailableException;
}