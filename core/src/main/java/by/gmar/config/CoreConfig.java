 package by.gmar.config;

import by.gmar.security.Md5PasswordEncoder;
import by.gmar.security.Md5PasswordEncoder;
import by.gmar.security.Md5PasswordEncoder;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;


 /**
  * @author s.kosik
  */
 @Configuration
@ComponentScan({"by.gmar.service", "by.gmar.dataaccess"})
@PropertySource({"classpath:commons-default.properties", "classpath:sdk_config.properties"})
@Import({EmailServicesConfig.class})
public class CoreConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        return new Md5PasswordEncoder();
    }
}
