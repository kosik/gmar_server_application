package by.gmar.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan("by.gmar.emailservices")
@PropertySource({
    "classpath:email-services-default.properties",
    "classpath:RegistrationConfirmationEmail.properties",
    "classpath:ForgotPasswordEmail.properties",
    "classpath:SystemSupportEmail.properties",
    "classpath:email-common-messages.properties"})
public class EmailServicesConfig {
    private final String EMAIL_HOST = "email.host";
    private final String EMAIL_PASS = "email.pass";
    private final String EMAIL_USERNAME = "email.username";
    private final String EMAIL_PORT = "email.port";
    private final String EMAIL_PROTOCOL = "email.protocol";
    private static final String FTL_VIEWS = "/WEB-INF/ftl_templates/";
    private static final String FTL_SUFF = ".ftl";
    
    @Autowired
    private Environment env;
    
    @Bean  
    public JavaMailSender getMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(env.getProperty(EMAIL_HOST));
        mailSender.setPassword(env.getProperty(EMAIL_PASS));
        mailSender.setUsername(env.getProperty(EMAIL_USERNAME));
        mailSender.setPort(env.getProperty(EMAIL_PORT, Integer.class));
        mailSender.setProtocol(env.getProperty(EMAIL_PROTOCOL));
        
        final Properties props = new Properties();
        props.put("mail.smtp.user", env.getProperty(EMAIL_USERNAME));
        props.put("mail.smtp.password", env.getProperty(EMAIL_PASS));
        props.put("mail.smtp.host", env.getProperty(EMAIL_HOST));
        props.put("mail.smtp.port", env.getProperty(EMAIL_PORT, Integer.class));
        props.put("mail.smtps.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", false);
        props.put("mail.mime.multipart.ignoreexistingboundaryparameter", true);
        
        mailSender.setJavaMailProperties(props);
        
        return mailSender;
    }
    
    @Bean
    public FreeMarkerConfigurer getFreeMarkerConfigurer(){
        FreeMarkerConfigurer cfg = new FreeMarkerConfigurer();
        cfg.setTemplateLoaderPath(FTL_VIEWS);
        cfg.setDefaultEncoding("utf8");
        
//        cfg.setFreemarkerSettings(null);
        return cfg;
    }
    
    @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver(){
        final FreeMarkerViewResolver fmResolver = new FreeMarkerViewResolver();
        fmResolver.setCache(true);
        fmResolver.setPrefix("");
        fmResolver.setSuffix(FTL_SUFF);
        
        return fmResolver;
    }
    
//        conf.setNumberFormat("0.####");
}
