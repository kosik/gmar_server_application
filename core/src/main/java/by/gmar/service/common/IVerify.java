package by.gmar.service.common;

import by.gmar.model.user.PrivateCode;
import by.gmar.model.user.User;

/**
 *
 * @author s.kosik
 */
public interface IVerify {
    PrivateCode issue(final User user)throws Exception;
    boolean check(final User user, final String code)throws Exception;
    boolean check(final String code, final String phone)throws Exception;
}
