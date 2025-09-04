package com.pulse.config.database;

import com.pulse.config.property.PulseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * H2 database configuration class for the {@code local} Spring profile.
 * <p>
 * This configuration integrates with {@link PulseConfig} to provide:
 * <ul>
 *   <li>H2 {@link DataSource}</li>
 *   <li>{@link LocalContainerEntityManagerFactoryBean} for JPA</li>
 *   <li>{@link PlatformTransactionManager} for transaction management</li>
 * </ul>
 * <p>
 * It extends {@link DBConfig} to reuse base configuration.
 */
@Slf4j
@Profile("local")
@Configuration
@EnableJpaRepositories(
        basePackages = "com.pulse.persistence",
        entityManagerFactoryRef = "pulseEntityManager",
        transactionManagerRef = "pulseTransactionManager"
)
@EnableTransactionManagement
public class H2Config extends DBConfig {

    private final PulseConfig pulseConfig;
    private final static String basePackage = "com.pulse.persistence";

    /**
     * Constructor for H2Config.
     *
     * @param pulseConfig The application-specific configuration that provides
     *                    database connection details.
     */
    public H2Config(PulseConfig pulseConfig) {

        super(basePackage);
        this.pulseConfig = pulseConfig;
        LOGGER.info("Initialized {} for base package: {}", H2Config.class.getSimpleName(), basePackage);

    }

    /**
     * Defines the {@link DataSource} bean for H2 database.
     *
     * @return Configured {@link DataSource}.
     */
    @Bean(name = "pulseDataSource")
    public DataSource dataSource() {

        LOGGER.info("Creating pulse dataSource for base package: {}", basePackage);

        PulseConfig.Database.H2 h2 = pulseConfig.getDatabase().getH2();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(h2.getUsername());
        dataSource.setPassword(h2.getPassword());
        dataSource.setUrl(h2.getUrl());
        dataSource.setDriverClassName(h2.getDriverClass());

        LOGGER.info("Created pulse dataSource successfully");
        return dataSource;

    }

    /**
     * Defines the {@link LocalContainerEntityManagerFactoryBean} for H2 JPA configuration.
     *
     * @return Configured {@link LocalContainerEntityManagerFactoryBean}.
     */
    @Override
    @Bean("pulseEntityManager")
    protected LocalContainerEntityManagerFactoryBean entityManager() {

        LOGGER.info("Creating pulse entity manager for base package: {}", basePackage);

        PulseConfig.Database.H2 h2 = pulseConfig.getDatabase().getH2();
        Map<String, String> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("dialect", h2.getDialect());
        jpaPropertyMap.put("showSql", h2.getShowSql());
        jpaPropertyMap.put("hbm2DdlAuto", h2.getHbm2DdlAuto());
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = createEntityManager(jpaPropertyMap);

        LOGGER.info("Created pulse entity manager successfully");
        return localContainerEntityManagerFactoryBean;

    }

    /**
     * Defines the {@link PlatformTransactionManager} for H2 transactions.
     *
     * @return Configured {@link PlatformTransactionManager}.
     */
    @Override
    @Bean("pulseTransactionManager")
    protected PlatformTransactionManager transactionManager() {

        LOGGER.info("Creating pulse transaction manager for base package: {}", basePackage);
        PlatformTransactionManager platformTransactionManager = createTransactionManager();

        LOGGER.info("Created pulse transaction manager successfully");
        return platformTransactionManager;

    }

}
