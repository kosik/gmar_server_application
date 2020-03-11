package by.gmar.config;

import by.gmar.config.spring.SpringConfig;
import by.gmar.config.CoreConfig;
import by.gmar.config.SpringJpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import by.gmar.config.spring.SpringConfig;

/**
 * Stuff configured here will be available on root application context level.
 *
 * Configuration directives, bean definitions etc specific to the Spring MVC dispatcher servlet
 * should go into WebMvcConfig.
 */
@Configuration
@Import({SpringConfig.class, SpringJpaConfig.class, SecurityConfig.class, CoreConfig.class})
public class AppConfig {

}
