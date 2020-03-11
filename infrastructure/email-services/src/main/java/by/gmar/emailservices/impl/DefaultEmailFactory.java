package by.gmar.emailservices.impl;

import by.gmar.emailservices.IEmailFactory;
import by.gmar.emailservices.ODEmailKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Service
public class DefaultEmailFactory implements IEmailFactory {
    private Logger logger = LoggerFactory.getLogger(DefaultEmailFactory.class);
    private final String REGISTRATION_CONFIRMATION_EMAIL_FTL = "registrationConfirmationEmail.ftl";
    private final String SERVICE_EXPIRATION_EMAIL_FTL = "serviceExpiration.ftl";
    
    private String defaultLocale;

    @Autowired
    private Environment env;
    @Autowired
    private FreeMarkerConfigurer fConfigurer;
    
    @PostConstruct
    public void setUp() {
        defaultLocale = env.getProperty(ODEmailKeys.EMAIL_DEF_LOCALE);
    }

    @Override
    public ODEmail createExpirationEmail(String recipientFullName, Locale locale) {
        final ODEmail email = new ODEmail();
        setSenderAndSubject(email, ODEmailKeys.USER_REGISTRATION_EMAIL_SENDER, 
            ODEmailKeys.SERVICE_EXPIRATION_SUBJ, locale);
        
        final Map<String, String> params = new HashMap<>();
        params.put(ODEmailKeys.USER_FULLNAME_MVAR, recipientFullName);
        params.put(ODEmailKeys.FOOTER_SLOGAN1, getProp(ODEmailKeys.FOOTER_SLOGAN1, locale));
        params.put(ODEmailKeys.FOOTER_SLOGAN2, getProp(ODEmailKeys.FOOTER_SLOGAN2, locale));
        params.put(ODEmailKeys.SUBSCRITION_NOTE, 
            getProp(ODEmailKeys.SUBSCRITION_NOTE, locale));
        params.put(ODEmailKeys.SERVICE_EXPIRATION_SUBJ, 
            getProp(ODEmailKeys.SERVICE_EXPIRATION_SUBJ, locale));
        
        return process(email, SERVICE_EXPIRATION_EMAIL_FTL, params);
    }
    
    @Override
    public ODEmail createRegistrationConfirmationEmail(final String recipientFullName, final Locale locale) {
        final ODEmail email = new ODEmail();
        setSenderAndSubject(email, ODEmailKeys.USER_REGISTRATION_EMAIL_SENDER, 
            ODEmailKeys.USER_REGISTRATION_EMAIL_SUBJECT, locale);
        
        final Map<String, String> params = new HashMap<>();
        params.put(ODEmailKeys.USER_FULLNAME_MVAR, recipientFullName);
        params.put(ODEmailKeys.FOOTER_SLOGAN1, getProp(ODEmailKeys.FOOTER_SLOGAN1, locale));
        params.put(ODEmailKeys.FOOTER_SLOGAN2, getProp(ODEmailKeys.FOOTER_SLOGAN2, locale));
        params.put(ODEmailKeys.USER_REGISTRATION_GREETHING, 
                getProp(ODEmailKeys.USER_REGISTRATION_GREETHING, locale));
        params.put(ODEmailKeys.CONFIRMATION_EMAIL, 
                getProp(ODEmailKeys.CONFIRMATION_EMAIL, locale));
        
        return process(email, REGISTRATION_CONFIRMATION_EMAIL_FTL, params);
    }

    
    private ODEmail process(final ODEmail email, final String templateName, final Map<String, String> params){
        String output = null;
        
        try {
            Template template = fConfigurer.getConfiguration().
                    getTemplate(templateName);
            output = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
            
        } catch (Exception e) {}
        
        email.setContent(output);
        return email;
    }
    
    
    @Override
    public ODEmail createForgotPasswordEmail(String recipientFullName, String newPassword, Locale locale) {
        final ODEmail email = new ODEmail();
        setSenderAndSubject(email, ODEmailKeys.USER_FORGOT_PASSWORD_EMAIL_SENDER, 
                ODEmailKeys.USER_FORGOT_PASSWORD_EMAIL_SUBJECT, locale);

//        final Context context = new Context();
//        context.setVariable("recipientFullName", recipientFullName);
//        context.setVariable("newPassword", newPassword);
//        context.setLocale(locale);
//        final String output = templateEngine.process("ForgotPasswordEmail", context);
//        email.setContent(output);

        return email;
    }

    private String getProp(String prop, Locale locale){
        String value = env.getProperty(prop + "_" + locale.getLanguage());
        if(null == value){
            value = env.getProperty(prop + "_" + defaultLocale);
        }
        return value;
    }
    
    private void setSenderAndSubject(ODEmail email, String senderProperty, String subjectProperty, Locale locale) {
        final String sender = env.getProperty(senderProperty);
        final String subject = getProp(subjectProperty, locale);

        logger.debug("sender: {}", sender);
        logger.debug("subject: {}", subject);

        email.setSender(sender);
        email.setSubject(subject);
    }


}
