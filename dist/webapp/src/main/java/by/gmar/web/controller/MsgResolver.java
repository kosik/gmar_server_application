package by.gmar.web.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 *
 * @author s.kosik
 */
@Component
public class MsgResolver {

    @Autowired
    private MessageSource messageSource;

    protected String getMsg(final String msgKey, final Locale locale) {
        String msg = null;
        try {
            msg = messageSource.getMessage(msgKey, null, locale);
        } catch (NoSuchMessageException e) {
            msg = messageSource.getMessage(msgKey, null, Locale.ENGLISH);
        }

        return msg;
    }
}
