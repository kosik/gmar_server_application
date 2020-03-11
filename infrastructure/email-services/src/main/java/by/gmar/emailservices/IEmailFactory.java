package by.gmar.emailservices;

import by.gmar.emailservices.impl.ODEmail;

import java.util.Locale;

/**
 * Creates {@link ODEmail} objects with the "sender", "subject" and "content" fields initialized to
 * appropriate values.
 */
public interface IEmailFactory {

    ODEmail createRegistrationConfirmationEmail(String recipientFullName, Locale locale);

    ODEmail createForgotPasswordEmail(String recipientFullName, String newPassword, Locale locale);

//    serviceExpiration
    ODEmail createExpirationEmail(String recipientFullName, Locale locale);
    
}
