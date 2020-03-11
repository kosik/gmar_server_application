package by.gmar.emailservices.impl;

import by.gmar.emailservices.IEmailTransport;
import by.gmar.emailservices.IEmailTransport;
import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author s.kosik
 */
@Service
public class DefEmailTransport implements IEmailTransport {
    private final String MAIL_MIME_CHARSET = "mail.mime.charset";
    private final String UTF_8 = "UTF-8";
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;
    
    @PostConstruct
    private void init(){
        final String utf = env.getProperty(MAIL_MIME_CHARSET);
        System.setProperty(MAIL_MIME_CHARSET, utf);
    }
    
    @Override
    public void sendMimeEmail(String sender, String recipient, String subjectStr, String contentAsStr) {
        MimeMessage msg =  mailSender.createMimeMessage();
        try {
            final MimeMessageHelper helper = new MimeMessageHelper(msg, false);
            helper.setTo(recipient);
            helper.setFrom(sender);
//            helper.setSubject(subjectStr);
//            helper.setText(contentAsStr, true);
            msg.setSubject(subjectStr, UTF_8);
            msg.setText(contentAsStr, UTF_8, "html");
            
        } catch (Exception e) {}
        mailSender.send(msg);
    }

    
    
//    private void sendMimeEmail(String sender, String recipient, String subjectStr, String contentAsStr) {
//        final SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom(sender);
//        msg.setTo(recipient);
//        msg.setSubject(subjectStr);
//        msg.setText(contentAsStr);
//        mailSender.send(msg);
//    }

}
