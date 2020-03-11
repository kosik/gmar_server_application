package by.gmar.config.spring;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;


/**
 *
 * @author s.kosik
 */
public class AppInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);

    public static final String PARAM_APPLICATION_NAME = "applicationName";

    public static AnnotationConfigApplicationContext create(Class<?>... annotatedClasses) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(annotatedClasses);
        context.register(SpringConfig.class);
        new AppInitializer().initialize(context);
        context.refresh();
        return context;
    }

    @Override public void initialize(final ConfigurableApplicationContext context) {
        final ConfigurableEnvironment env = context.getEnvironment();

        env.setActiveProfiles(env.getActiveProfiles());
        env.addActiveProfile(Profiles.MAIN.toString());
        logActiveProfiles(env);

        final ConfigurableEnvironmentPropertiesLoader propertiesLoader =
            new FileSystemConfigurableEnvironmentPropertiesLoader();
        loadLocalProperties(propertiesLoader, env);
        loadApplicationSpecificLocalProperties(propertiesLoader, env);
        logPropertySources(env);
    }

    private void logActiveProfiles(final ConfigurableEnvironment env) {
        final String[] activeProfiles = env.getActiveProfiles();
        LOGGER.info("Active profiles: {}", Arrays.asList(activeProfiles));
    }

    private void logPropertySources(final ConfigurableEnvironment env) {
        final MutablePropertySources propertySources = env.getPropertySources();
        final List<String> names = new ArrayList<String>(propertySources.size());
        for (PropertySource<?> ps : propertySources) {
            names.add(ps.getName());
        }
        LOGGER.debug("Active property sources: {}", names);
    }

    /**
     * Loads environment specific properties.
     */
    private void loadLocalProperties(final ConfigurableEnvironmentPropertiesLoader propertiesLoader,
            final ConfigurableEnvironment env) {
        propertiesLoader.loadProperties(env, "buzzer-local.properties");
    }

    /**
     * Loads application specific environment specific properties.
     */
    private void loadApplicationSpecificLocalProperties(final ConfigurableEnvironmentPropertiesLoader propertiesLoader,
                                                        final ConfigurableEnvironment env) {
        final String applicationName = env.getProperty(PARAM_APPLICATION_NAME);
        if (applicationName != null) {
            propertiesLoader.loadProperties(env, format("%s-local.properties", applicationName));
        }
    }
}
