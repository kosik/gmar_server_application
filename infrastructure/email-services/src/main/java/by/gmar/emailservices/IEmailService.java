package by.gmar.emailservices;

import java.util.Locale;

/**
 * Service for sending template based emails.
 */
public interface IEmailService {

    void sendUserRegistrationConfirmationEmail(String userEmail, String userFullName, Locale locale);

    void sendForgotPasswordEmail(String userEmail, String newPassword, Locale locale, String recipientEmail);

}