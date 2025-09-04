package com.pulse.config.database;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Abstract base configuration class for setting up database-related beans
 * in a Spring Boot JPA/Hibernate application.
 */
public abstract class DBConfig {

    private final String basePackage;

    /**
     * Constructor for DBConfig.
     *
     * @param basePackage The base package to scan for JPA entities.
     */
    protected DBConfig(String basePackage) {

        this.basePackage = basePackage;

    }

    /**
     * Creates a {@link LocalContainerEntityManagerFactoryBean} with the given properties.
     *
     * @param jpaPropertyMap Map of JPA/Hibernate-specific properties.
     * @return Configured {@link LocalContainerEntityManagerFactoryBean}.
     */
    protected LocalContainerEntityManagerFactoryBean createEntityManager(Map<String, String> jpaPropertyMap) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(basePackage);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(properties(jpaPropertyMap));

        return entityManagerFactoryBean;

    }

    /**
     * Creates a {@link JpaTransactionManager} linked to the current EntityManager.
     *
     * @return Configured {@link PlatformTransactionManager}.
     */
    protected PlatformTransactionManager createTransactionManager() {

        if (entityManager() != null) {
            return new JpaTransactionManager(Objects.requireNonNull(entityManager().getObject()));
        }

        return new JpaTransactionManager();

    }

    /**
     * Builds Hibernate JPA properties from the provided property map.
     *
     * @param propertyMap Map containing Hibernate properties such as dialect and showSql.
     * @return Hibernate properties.
     */
    private Properties properties(Map<String, String> propertyMap) {

        var properties = new Properties();
        properties.setProperty("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        properties.setProperty("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        properties.setProperty("hibernate.dialect", propertyMap.get("dialect"));
        properties.setProperty("hibernate.show_sql", propertyMap.get("showSql"));
        properties.setProperty("hibernate.hbm2ddl.auto", propertyMap.get("hbm2DdlAuto"));


        return properties;

    }

    /**
     * Provides a {@link DataSource} bean.
     * Subclasses must return a configured DataSource instance.
     *
     * @return Configured {@link DataSource}.
     */
    protected abstract DataSource dataSource();

    /**
     * Provides an {@link LocalContainerEntityManagerFactoryBean} bean.
     * Subclasses must return a configured EntityManagerFactory instance.
     *
     * @return Configured {@link LocalContainerEntityManagerFactoryBean}.
     */
    protected abstract LocalContainerEntityManagerFactoryBean entityManager();

    /**
     * Provides a {@link PlatformTransactionManager} bean.
     * Subclasses must return a configured TransactionManager instance.
     *
     * @return Configured {@link PlatformTransactionManager}.
     */
    protected abstract PlatformTransactionManager transactionManager();

}
