package by.gmar.config;

import java.util.List;

import by.gmar.model.dto.UserPreferences;
import by.gmar.model.dto.UserPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import by.gmar.model.dto.UserPreferences;
import org.springframework.context.MessageSource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"by.gmar.web"})
@PropertySource({"classpath:web-default.properties"})
@Import({JacksonConfig.class, VersionInfoConfig.class, I18nConfig.class})
//@EnableWebMvc
class WebMvcConfig extends WebMvcConfigurationSupport {
    private static final String MESSAGE_SYSTEM = "/WEB-INF/i18n/system";
    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    private static final String MESSAGE_AGREMENT = "/WEB-INF/i18n/rules";
    private static final String MESSAGE_LANDING = "/WEB-INF/i18n/landing";
    private static final String MESSAGE_IMAGE_TITLES = "/WEB-INF/i18n/images";
    private static final String MESSAGE_IMAGE_TOOLTIPS = "/WEB-INF/i18n/tooltips";
    private static final String VIEWS = "/WEB-INF/views/";

    @Autowired
    private ObjectMapper objectMapper; // reference to object mapper from JacksonConfig

    @Override public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("https://kosik.github.io", "https://kosik.github.io/fileTest",
            "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop", "*", "https://buzzer" )
            .allowedMethods("GET", "POST")
            .exposedHeaders("Cookie", "Set-Cookie")
            .allowCredentials(true).maxAge(3600);
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGE_SYSTEM, MESSAGE_AGREMENT, MESSAGE_SOURCE,
                MESSAGE_LANDING, MESSAGE_IMAGE_TITLES, MESSAGE_IMAGE_TOOLTIPS);
        messageSource.setDefaultEncoding("UTF-8");
        
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        return localeChangeInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(localeChangeInterceptor());
    }
    
    @Override
    public Validator getValidator() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/repository/**").addResourceLocations("/repository/");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(VIEWS);
        resolver.setSuffix(".jsp");
        return resolver;
    }

//    @Bean
//    public FreeMarkerViewResolver getFreeMarkerViewResolver(){
//        final FreeMarkerViewResolver fmResolver = new FreeMarkerViewResolver();
//        fmResolver.setCache(true);
//        fmResolver.setPrefix("");
//        fmResolver.setSuffix(".jsp");
//        
//        return fmResolver;
//    }
//    
//    @Bean
//    public FreeMarkerConfigurer getFreeMarkerConfigurer(){
//        FreeMarkerConfigurer cfg = new FreeMarkerConfigurer();
//        cfg.setTemplateLoaderPath(VIEWS);
//        cfg.setDefaultEncoding("utf8");
//        
////        cfg.setFreemarkerSettings(null);
//        return cfg;
//    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public CommonsMultipartResolver getMultipartResolver() {
//        final CommonsMultipartResolver mr = new CommonsMultipartResolver();
//        mr.setMaxUploadSize(2097152); // 2Mb
//        return mr;
//    }
    
    @Bean(name = "multipartResolver")
    public StandardServletMultipartResolver resolver() {
        return new StandardServletMultipartResolver();
    }
    

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        objectMapper.registerModule(new Hibernate4Module());
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserPreferences getUserPreferences(){
        final UserPreferences preferences = new UserPreferences();
        return preferences;
    }
    
    @Controller
    static class FaviconController {
        @RequestMapping("/favicon.ico")
        String favicon() {
            return "forward:/resources/img-res/favicon.ico";
        }
    }
}
