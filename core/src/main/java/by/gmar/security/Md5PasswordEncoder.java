package by.gmar.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.base.Strings;

/**
 *
 * @author s.kosik
 */
public class Md5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return encodeStringToMd5(rawPassword);
    }

    private String encodeStringToMd5(CharSequence rawPassword) {
        if (rawPassword == null || rawPassword.length() == 0) {
            return "";
        }
        return DigestUtils.md5Hex(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //NOTE we use ignore case due to YANDEX MD5 checkOrder request pass it in upper case
        return Strings.nullToEmpty(encodedPassword).equalsIgnoreCase(encodeStringToMd5(rawPassword));
    }    
}
