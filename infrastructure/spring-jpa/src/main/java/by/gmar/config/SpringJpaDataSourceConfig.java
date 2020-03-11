package by.gmar.config;

import java.beans.PropertyVetoException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * In order to reach more flexibility with our docker env we add possibility to read
 * database parameters from operation system environment.
 *
 * This works following order: 1) the application try reads OS variable 2) if the variable is not availible,
 * application reads the value from spring-jpa-default.properties
 * To see variable names see spring-jpa-default.properties file.
 *
 * NB: For the OS variables we follow UPPER_CASE & SNAKE_STYLE name convention;
 *
 * @author s.kosik
 */
@Configuration
public class SpringJpaDataSourceConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(SpringJpaDataSourceConfig.class);

    @Inject @Value("${jdbc.url}") private String jdbcUrl;
    @Inject @Value("${db.connection.params}") private String dbConnectionParams;
    @Inject @Value("${db.pool.initial_size}") private String dbPoolInitSize;
    @Inject @Value("${db.pool.max_size}") private String dbPoolMaxSize;

    @Inject @Value("${APP_MYSQL_MAIN_DATA_HOST}") private String APP_DB_HOST;
    @Inject @Value("${APP_MYSQL_MAIN_DATA_DATABASE}") private String APP_DB_NAME;

    @Inject @Value("${APP_MYSQL_MAIN_DATA_USER}") private String APP_DB_USER;
    @Inject @Value("${APP_MYSQL_MAIN_DATA_PASSWORD}") private String APP_DB_PASSWORD;

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";


    private static final String DB_POOL_TEST_CONNECTION_QUERY = "db.pool.test_connection_query";
//    private static final String DB_POOL_TEST_CONNECTION_ON_CHECKOUT = "db.pool.test_connection_on_checkout";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        final ComboPooledDataSource dataSource = new ComboPooledDataSource();

        //TODO move it into separate class by AOP pointcut.
        LOGGER.debug("KEYS from configuration file: ");
        LOGGER.debug("APP_DB_HOST: " + APP_DB_HOST);
        LOGGER.debug("APP_DB_NAME: " + APP_DB_NAME);
        LOGGER.debug("APP_DB_USER: " + APP_DB_USER);
        LOGGER.debug("APP_DB_PASSWORD: " + APP_DB_PASSWORD);
        LOGGER.debug("###");
        LOGGER.debug("KEYS from system environment: ");
        LOGGER.debug("APP_MYSQL_MAIN_DATA_HOST: " + (System.getenv ("APP_MYSQL_MAIN_DATA_HOST")));
        LOGGER.debug("APP_MYSQL_MAIN_DATA_DATABASE: " + (System.getenv ("APP_MYSQL_MAIN_DATA_DATABASE")));
        LOGGER.debug("APP_MYSQL_MAIN_DATA_USER: " + (System.getenv ("APP_MYSQL_MAIN_DATA_USER")));
        LOGGER.debug("APP_MYSQL_MAIN_DATA_PASSWORD: " + (System.getenv ("APP_MYSQL_MAIN_DATA_PASSWORD")));

        final String dbConnectionUrl = jdbcUrl
                + ((System.getenv ("APP_MYSQL_MAIN_DATA_HOST") == null ? APP_DB_HOST : System.getenv("APP_MYSQL_MAIN_DATA_HOST"))
                + "/" + (System.getenv ("APP_MYSQL_MAIN_DATA_DATABASE") == null ? APP_DB_NAME : System.getenv ("APP_MYSQL_MAIN_DATA_DATABASE"))
                + dbConnectionParams);

        LOGGER.debug("db_connection uri is: " + dbConnectionUrl);

        dataSource.setJdbcUrl(dbConnectionUrl);

        dataSource.setDriverClass(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));

        dataSource.setUser(System.getenv ("APP_MYSQL_MAIN_DATA_USER") == null ? APP_DB_USER : System.getenv("APP_MYSQL_MAIN_DATA_USER"));
        dataSource.setPassword(System.getenv ("APP_MYSQL_MAIN_DATA_PASSWORD") == null ? APP_DB_PASSWORD : System.getenv("APP_MYSQL_MAIN_DATA_PASSWORD"));

        dataSource.setPreferredTestQuery(environment.getRequiredProperty(DB_POOL_TEST_CONNECTION_QUERY));

        dataSource.setAutoCommitOnClose(true);
        dataSource.setInitialPoolSize(Integer.valueOf(dbPoolInitSize));
        dataSource.setMaxPoolSize(Integer.valueOf(dbPoolMaxSize));

        return dataSource;
    }

}
