package by.gmar.config;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@ImportResource(value = "classpath:spring-security-context.xml")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//public class SecurityConfig {
// if Spring MVC is on classpath and no CorsConfigurationSource is provided,
// Spring Security will use CORS configuration provided to Spring MVC
//    @Override protected void configure(HttpSecurity http) throws Exception {
//        http.cors().disable();
//    }
    
    @Bean
    SecurityContextPersistenceFilter securityContextPersistenceFilter() {
        final HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        repo.setAllowSessionCreation(true);
        return new SecurityContextPersistenceFilter(repo);
    }

    @Bean
    MethodInvokingFactoryBean setSecurityContextHolderStrategy() {
        final MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setTargetClass(SecurityContextHolder.class);
        bean.setTargetMethod("setStrategyName");
        // Use default MODE_THREADLOCAL strategy.
        // MODE_GLOBAL is suitable for application where all instances in the JVM share the SecurityContext,
        // but in servlet base web application each thread must has it's own SecurityContext.
        bean.setArguments(new String[]{SecurityContextHolder.MODE_THREADLOCAL});
        return bean;
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//	 http.authorizeRequests()
//	    .antMatchers("/secure/**").access("hasRole('USER')")
//	    .and().formLogin().loginPage("/").failureUrl("/status")
//                .usernameParameter("username")
//		.passwordParameter("password")
//	    .and().logout().logoutSuccessUrl("/login?logout")
//        http.exceptionHandling().accessDeniedPage("/status");
//    }

}