package by.gmar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 *
 * @author s.kosik
 */
@Configuration
public class I18nConfig {

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");
        return localeChangeInterceptor;
    }

//    @Bean
//    public HandlerMapping handlerMapping() {
//        final LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("locale");
//
//        final DefaultAnnotationHandlerMapping ret = new DefaultAnnotationHandlerMapping();
//        ret.setInterceptors(new Object[]{interceptor});
//        return ret;
//    }

}
