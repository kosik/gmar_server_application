package by.gmar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:build.properties")
public class VersionInfoConfig {

    private final Logger logger = LoggerFactory.getLogger(VersionInfoConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public VersionInfo versionInfo() {
        String buildVersion = environment.getProperty("build.version");
        String buildTimestamp = environment.getProperty("build.timestamp");
        String releaseVersion = environment.getProperty("release.version");
        logger.info("Build version: " + buildVersion);
        logger.info("Build timestamp: " + buildTimestamp);
        logger.info("Release version: " + releaseVersion);
        return new VersionInfo(buildVersion, buildTimestamp, releaseVersion);
    }

}
