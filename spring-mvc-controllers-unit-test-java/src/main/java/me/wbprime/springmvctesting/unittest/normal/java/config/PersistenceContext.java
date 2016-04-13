package me.wbprime.springmvctesting.unittest.normal.java.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Class: PersistenceContext
 * Date: 2016/04/13 18:05
 *
 * @author Elvis Wang [bo.wang35@renren-inc.com]
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"me.wbprime.springmvctesting.common.services" })
public class PersistenceContext {
    protected static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    protected static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    protected static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private static final String PROPERTY_PACKAGES_TO_SCAN = "net.petrikainulainen.spring.testmvc.todo.model";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        final DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
            = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);

        Properties jpaProperties = new Properties();
        jpaProperties.put(
            PROPERTY_NAME_HIBERNATE_DIALECT,
            environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT)
        );
        jpaProperties.put(
            PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
            environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL)
        );
        jpaProperties.put(
            PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
            environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO)
        );
        jpaProperties.put(
            PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
            environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY)
        );
        jpaProperties.put(
            PROPERTY_NAME_HIBERNATE_SHOW_SQL,
            environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL)
        );

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }
}
