package by.gmar.config.spring;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Loads properties from property sources and places them in Spring's
 * {@link ConfigurableEnvironment} bean overriding existing ones if they exist. A typical use case
 * would be to load environment specific properties.
 *
 * @author s.kosik
 */
public interface ConfigurableEnvironmentPropertiesLoader {

    /**
     * Loads properties from a list of property sources. Non-existing property sources will be
     * silently ignored.
     *
     * @param environment Spring's {@link ConfigurableEnvironment} to hold the loaded properties.
     * @param propertySources The names of the property sources to process.
     */
    void loadProperties(ConfigurableEnvironment environment, String... propertySources);
}
