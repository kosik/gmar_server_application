package by.gmar.emailservices.impl;

import by.gmar.emailservices.IEmailFactory;
import by.gmar.emailservices.IEmailService;
import by.gmar.emailservices.IEmailTransport;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private IEmailFactory emailFactory;

    @Autowired
    private IEmailTransport emailTransport;

    @Override
    public void sendUserRegistrationConfirmationEmail(final String userEmail, final String userFullName,
            final Locale locale) {
        final ODEmail email = emailFactory.createRegistrationConfirmationEmail(userFullName, locale);
        sendEmail(userEmail, email);
    }

    @Override
    public void sendForgotPasswordEmail(final String recipientFullName, final String newPassword, final Locale locale,
            String recipientEmail) {
        final ODEmail email = emailFactory.createForgotPasswordEmail(recipientFullName, newPassword, locale);
        sendEmail(recipientEmail, email);
    }

    private void sendEmail(final String recipientEmail, final ODEmail email) {
        email.setRecipient(recipientEmail);
        emailTransport.sendMimeEmail(email.getSender(), email.getRecipient(), 
                email.getSubject(), email.getContent());
    }
}
