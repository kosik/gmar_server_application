package by.gmar.config.spring;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

/**
 * A {@link ConfigurableEnvironmentPropertiesLoader} implementation that loads properties from files
 * located on the file system.
 * <p>
 * The default directory to load the property sources from is "/etc/frt". This location can be
 * overridden by specifying the "FRT_ETC" environment variable.
 *
 * @author s.kosik
 */
public class FileSystemConfigurableEnvironmentPropertiesLoader implements ConfigurableEnvironmentPropertiesLoader {

    //TODO: implement directory path reading from properties
    /** Default etc directory to look for property files. */    
    public static final String DEFAULT_ETC_DIR = "/etc/buzzer";

    /** The default etc directory can be overridden by setting the FRT_ETC environment variable. */
    public static final String CUSTOM_ETC_DIR_VAR = "FRT_ETC";

    private static final Logger LOGGER = LoggerFactory.getLogger(
            FileSystemConfigurableEnvironmentPropertiesLoader.class);

    private SystemAdapter system = new DefaultSystemAdapter();

    @Override
    public void loadProperties(final ConfigurableEnvironment environment, final String... propertySources) {
        final String envEtcDir = system.getenv(CUSTOM_ETC_DIR_VAR);
        final String configDir = (envEtcDir == null) ? DEFAULT_ETC_DIR : envEtcDir;

        for (String propertySource : propertySources) {
            try {
                loadPropertiesFromFile(environment, new File(configDir + File.separatorChar + propertySource));

            } catch (Exception e) {
                throw new RuntimeException("Failed to load properties file '" + propertySource + "'", e);
            }
        }
    }

    private void loadPropertiesFromFile(final ConfigurableEnvironment env, final File file) {
        if (!file.exists()) {
            LOGGER.warn("Skipping non-existing properties file '" + file.getAbsolutePath() + "'");
            return;
        }

        LOGGER.info("Loading properties file '" + file.getAbsolutePath() + "'");
        final Properties properties = new Properties();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            properties.load(fileReader);

        } catch (Exception e) {
            throw new RuntimeException("Failed loading properties from file '" + file.getAbsolutePath() + "'", e);

        } finally {
            IOUtils.closeQuietly(fileReader);
        }

        env.getPropertySources().addFirst(new PropertiesPropertySource(file.getAbsolutePath(), properties));
    }
}
