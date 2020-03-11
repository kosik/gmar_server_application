package by.gmar.config.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author s.kosik
 */
@Component
public class BeanWiringSupport {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Autowires a bean and runs bean post processors. The autowired objects'
     * class must have a public or protected no-arg constructor.
     *
     * @param object instance to wire
     * @return the wired instance
     */
    @SuppressWarnings("unchecked")
    public <T> T autowire(T object) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(object);
        return (T) applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(
                object, object.getClass().getSimpleName());
    }
}